package com.dante.paul.dd5erandomlootgenerator;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.dante.paul.dd5erandomlootgenerator.EnumeratedClasses.RulesEdition;
import com.dante.paul.dd5erandomlootgenerator.Settings.SettingsManager;
import com.dante.paul.dd5erandomlootgenerator.billing.BillingManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

import java.util.concurrent.atomic.AtomicBoolean;

public class LootGenerator extends AppCompatActivity
        implements BillingManager.Listener {

    private FrameLayout adContainer;
    private AdView adView;
    private BillingManager billingManager;
    private boolean adsEnabled;
    private ConsentInformation consentInformation;
    private final AtomicBoolean adsSdkInitialized = new AtomicBoolean(false);
    private SharedPreferences.OnSharedPreferenceChangeListener titlePrefsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loot_generator);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        updateToolbarTitle();

        SharedPreferences prefs = getApplicationContext()
                .getSharedPreferences("LootGenPref", Context.MODE_PRIVATE);
        titlePrefsListener = (sharedPreferences, key) -> {
            if ("rules_edition".equals(key)) {
                updateToolbarTitle();
                // Tabs change between editions (Tracker tab is 2024-only);
                // simplest robust refresh is to recreate the activity.
                recreate();
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(titlePrefsListener);

        billingManager = new BillingManager(this, this);
        adsEnabled = !billingManager.isAdsRemovedCached();

        adContainer = findViewById(R.id.ad_container);

        boolean is2024 = SettingsManager.getRulesEdition(this) == RulesEdition.RULES_2024;
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Treasure"));
        tabLayout.addTab(tabLayout.newTab().setText("Items"));
        tabLayout.addTab(tabLayout.newTab().setText("Spells"));
        if (is2024) tabLayout.addTab(tabLayout.newTab().setText("Tracker"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) { viewPager.setCurrentItem(tab.getPosition()); }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        billingManager.start();
        if (adsEnabled) {
            requestConsentAndLoadAd();
        } else {
            adContainer.setVisibility(View.GONE);
        }
    }

    private void requestConsentAndLoadAd() {
        ConsentRequestParameters params = new ConsentRequestParameters.Builder().build();
        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                () -> UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                        this,
                        formError -> {
                            if (consentInformation.canRequestAds()) {
                                initializeAdsAndLoadBanner();
                            }
                        }),
                requestError -> {
                    if (consentInformation.canRequestAds()) {
                        initializeAdsAndLoadBanner();
                    }
                });
    }

    private void initializeAdsAndLoadBanner() {
        if (!adsSdkInitialized.getAndSet(true)) {
            MobileAds.initialize(this, status -> {});
        }
        if (adView != null || !adsEnabled) return;
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.test_banner_ad));
        adView.setAdSize(getAdaptiveBannerSize());
        adContainer.removeAllViews();
        adContainer.addView(adView);
        adContainer.setVisibility(View.VISIBLE);
        adView.loadAd(new AdRequest.Builder().build());
    }

    private AdSize getAdaptiveBannerSize() {
        float density = getResources().getDisplayMetrics().density;
        int widthPixels = adContainer.getWidth();
        if (widthPixels <= 0) {
            widthPixels = getResources().getDisplayMetrics().widthPixels;
        }
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private void removeBannerAd() {
        adsEnabled = false;
        if (adView != null) {
            adView.destroy();
            adView = null;
        }
        adContainer.removeAllViews();
        adContainer.setVisibility(View.GONE);
    }

    @Override
    public void onAdsRemovedChanged(boolean adsRemoved) {
        runOnUiThread(() -> {
            if (adsRemoved) {
                removeBannerAd();
                Toast.makeText(this, R.string.remove_ads_thanks, Toast.LENGTH_LONG).show();
            } else if (adView == null) {
                adsEnabled = true;
                requestConsentAndLoadAd();
            }
            invalidateOptionsMenu();
        });
    }

    @Override
    public void onPurchaseError(@androidx.annotation.NonNull String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) adView.resume();
    }

    @Override
    protected void onPause() {
        if (adView != null) adView.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
            adView = null;
        }
        if (billingManager != null) {
            billingManager.destroy();
        }
        if (titlePrefsListener != null) {
            getApplicationContext()
                    .getSharedPreferences("LootGenPref", Context.MODE_PRIVATE)
                    .unregisterOnSharedPreferenceChangeListener(titlePrefsListener);
            titlePrefsListener = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loot_generator, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem remove = menu.findItem(R.id.action_remove_ads);
        if (remove != null) {
            remove.setVisible(billingManager == null || !billingManager.isAdsRemovedCached());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void updateToolbarTitle() {
        RulesEdition edition = SettingsManager.getRulesEdition(this);
        int titleRes = edition == RulesEdition.RULES_2014 ? R.string.app_name_2014 : R.string.app_name_2024;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleRes);
        } else {
            setTitle(titleRes);
        }
    }

    private void showRulesEditionDialog() {
        RulesEdition current = SettingsManager.getRulesEdition(this);
        String[] labels = new String[] {
                getString(R.string.rules_edition_2024),
                getString(R.string.rules_edition_2014)
        };
        RulesEdition[] values = new RulesEdition[] {
                RulesEdition.RULES_2024,
                RulesEdition.RULES_2014
        };
        int checked = current == RulesEdition.RULES_2014 ? 1 : 0;
        new AlertDialog.Builder(this)
                .setTitle(R.string.rules_edition_dialog_title)
                .setSingleChoiceItems(labels, checked, (dialog, which) -> {
                    SettingsManager.setRulesEdition(this, values[which]);
                    dialog.dismiss();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    public boolean about(MenuItem item){
        String aboutSummary = "D&D 5e Loot Generator v1.9";
        String about = "Developed by Paul Dante to help save DMs some time. \r\n \r\nPlease rate and provide feedback of how I can improve this app.\r\n \r\nIf you like the app and would like to make a donation: PayPal.Me/PDante \n" +
                " \nBackground image of scroll provided by www.myfreetextures.com";
        DialogFragment how = new GenerateAboutMessage();
        Bundle args = new Bundle();
        args.putString("about_summary", aboutSummary);
        args.putString("about", about);
        how.setArguments(args);
        how.show(getFragmentManager(), "tag");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_remove_ads) {
            if (billingManager != null) billingManager.launchPurchaseFlow(this);
            return true;
        }
        if (id == R.id.action_restore_purchases) {
            if (billingManager != null) billingManager.restorePurchases();
            return true;
        }
        if (id == R.id.action_rules_edition) {
            showRulesEditionDialog();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
