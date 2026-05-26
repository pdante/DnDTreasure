package com.dante.paul.dd5erandomlootgenerator.billing;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.PendingPurchasesParams;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;

import java.util.Collections;
import java.util.List;

public class BillingManager {

    public static final String PRODUCT_REMOVE_ADS = "remove_ads";

    private static final String TAG = "BillingManager";
    private static final String PREFS = "LootGenPref";
    private static final String KEY_PREMIUM = "premium";

    public interface Listener {
        void onAdsRemovedChanged(boolean adsRemoved);
        void onPurchaseError(@NonNull String message);
        /** Called once per user-initiated {@link #restorePurchases()} call. */
        void onRestoreCompleted(boolean entitlementFound);
    }

    private final Context appContext;
    private final Listener listener;
    private final BillingClient billingClient;
    private ProductDetails removeAdsProduct;
    private boolean adsRemovedCached;
    /**
     * True while we owe the user a Toast for an explicit "Restore Purchase"
     * tap. Consumed by the next {@link #queryPurchases} callback (or by a
     * billing-setup failure if we never get that far).
     */
    private boolean pendingRestoreFeedback;

    public BillingManager(@NonNull Context context, @NonNull Listener listener) {
        this.appContext = context.getApplicationContext();
        this.listener = listener;
        this.adsRemovedCached = readCachedPremium();
        this.billingClient = BillingClient.newBuilder(appContext)
                .setListener(this::onPurchasesUpdated)
                .enablePendingPurchases(
                        PendingPurchasesParams.newBuilder()
                                .enableOneTimeProducts()
                                .build())
                .build();
    }

    public boolean isAdsRemovedCached() {
        return adsRemovedCached;
    }

    public void start() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult result) {
                if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    queryProductDetails();
                    queryPurchases();
                } else {
                    Log.w(TAG, "Billing setup failed: " + result.getDebugMessage());
                    if (pendingRestoreFeedback) {
                        pendingRestoreFeedback = false;
                        listener.onPurchaseError(
                                "Could not reach the Play Store. Please try again.");
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.w(TAG, "Billing service disconnected");
            }
        });
    }

    public void destroy() {
        if (billingClient.isReady()) {
            billingClient.endConnection();
        }
    }

    public void launchPurchaseFlow(@NonNull Activity activity) {
        if (removeAdsProduct == null) {
            listener.onPurchaseError("Store not ready yet, please try again in a moment.");
            return;
        }
        BillingFlowParams.ProductDetailsParams productParams =
                BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(removeAdsProduct)
                        .build();
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(Collections.singletonList(productParams))
                .build();
        BillingResult result = billingClient.launchBillingFlow(activity, flowParams);
        if (result.getResponseCode() != BillingClient.BillingResponseCode.OK) {
            listener.onPurchaseError("Could not start purchase: " + result.getDebugMessage());
        }
    }

    public void restorePurchases() {
        pendingRestoreFeedback = true;
        if (!billingClient.isReady()) {
            // start() will trigger queryPurchases() once the connection is up,
            // and the pendingRestoreFeedback flag survives that round-trip.
            start();
            return;
        }
        queryPurchases();
    }

    private void queryProductDetails() {
        QueryProductDetailsParams.Product product =
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(PRODUCT_REMOVE_ADS)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build();
        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(Collections.singletonList(product))
                .build();
        billingClient.queryProductDetailsAsync(params, (result, products) -> {
            if (result.getResponseCode() == BillingClient.BillingResponseCode.OK
                    && !products.isEmpty()) {
                removeAdsProduct = products.get(0);
            } else {
                Log.w(TAG, "Product details query failed: " + result.getDebugMessage());
            }
        });
    }

    private void queryPurchases() {
        QueryPurchasesParams params = QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP)
                .build();
        billingClient.queryPurchasesAsync(params, (result, purchases) -> {
            if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                handlePurchases(purchases);
                boolean owned = containsRemoveAds(purchases);
                if (!owned && adsRemovedCached) {
                    updateAdsRemoved(false);
                }
                if (pendingRestoreFeedback) {
                    pendingRestoreFeedback = false;
                    listener.onRestoreCompleted(owned);
                }
            } else if (pendingRestoreFeedback) {
                pendingRestoreFeedback = false;
                listener.onPurchaseError(
                        "Could not reach the Play Store. Please try again.");
            }
        });
    }

    private void onPurchasesUpdated(@NonNull BillingResult result, List<Purchase> purchases) {
        int code = result.getResponseCode();
        if (code == BillingClient.BillingResponseCode.OK && purchases != null) {
            handlePurchases(purchases);
        } else if (code == BillingClient.BillingResponseCode.USER_CANCELED) {
            // User backed out of the purchase dialog — not an error to surface.
        } else if (code == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            queryPurchases();
        } else {
            listener.onPurchaseError("Purchase failed: " + result.getDebugMessage());
        }
    }

    private void handlePurchases(List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            if (!purchase.getProducts().contains(PRODUCT_REMOVE_ADS)) continue;
            if (purchase.getPurchaseState() != Purchase.PurchaseState.PURCHASED) continue;

            updateAdsRemoved(true);

            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams ackParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();
                billingClient.acknowledgePurchase(ackParams, ackResult -> {
                    if (ackResult.getResponseCode() != BillingClient.BillingResponseCode.OK) {
                        Log.w(TAG, "Ack failed: " + ackResult.getDebugMessage());
                    }
                });
            }
        }
    }

    private boolean containsRemoveAds(List<Purchase> purchases) {
        for (Purchase p : purchases) {
            if (p.getProducts().contains(PRODUCT_REMOVE_ADS)
                    && p.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                return true;
            }
        }
        return false;
    }

    private void updateAdsRemoved(boolean removed) {
        if (removed == adsRemovedCached) return;
        adsRemovedCached = removed;
        writeCachedPremium(removed);
        listener.onAdsRemovedChanged(removed);
    }

    private boolean readCachedPremium() {
        SharedPreferences prefs = appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_PREMIUM, false);
    }

    private void writeCachedPremium(boolean premium) {
        SharedPreferences prefs = appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_PREMIUM, premium).apply();
    }
}
