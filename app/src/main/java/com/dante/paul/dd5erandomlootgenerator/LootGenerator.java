package com.dante.paul.dd5erandomlootgenerator;

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
import com.dante.paul.dd5erandomlootgenerator.Tracker.Campaign;
import com.dante.paul.dd5erandomlootgenerator.Tracker.CampaignStore;
import com.dante.paul.dd5erandomlootgenerator.billing.BillingManager;

import java.util.List;
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
    private SharedPreferences.OnSharedPreferenceChangeListener campaignPrefsListener;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        // The pager's tab composition changes between editions (Tracker is
        // 2024-only), so any restored fragment / ViewPager state from a
        // previous activity instance can land at the wrong positions.
        // Always start fresh — no saved state restoration. The cost is
        // losing in-fragment scroll/selection state on rotation, which is
        // acceptable for this app.
        super.onCreate(null);
        setContentView(R.layout.activity_loot_generator);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        updateToolbarTitle();
        updateToolbarSubtitle();
        toolbar.setOnClickListener(v -> {
            if (SettingsManager.getRulesEdition(this) == RulesEdition.RULES_2024) {
                showCampaignPickerDialog();
            }
        });

        SharedPreferences prefs = getApplicationContext()
                .getSharedPreferences("LootGenPref", Context.MODE_PRIVATE);
        titlePrefsListener = (sharedPreferences, key) -> {
            if ("rules_edition".equals(key)) {
                updateToolbarTitle();
                // Tabs change between editions (Tracker is 2024-only).
                // recreate() reuses saved state, which means stale fragments
                // end up at the wrong positions when the tab list changes.
                // finish + new Intent guarantees a fresh activity with no
                // saved fragment / view-pager state to restore.
                android.content.Intent restart =
                        new android.content.Intent(this, LootGenerator.class);
                restart.addFlags(android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(restart);
                overridePendingTransition(0, 0);
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(titlePrefsListener);

        SharedPreferences campaignPrefs = getApplicationContext()
                .getSharedPreferences("LootGenCampaigns", Context.MODE_PRIVATE);
        campaignPrefsListener = (sharedPreferences, key) -> updateToolbarSubtitle();
        campaignPrefs.registerOnSharedPreferenceChangeListener(campaignPrefsListener);

        billingManager = new BillingManager(this, this);
        adsEnabled = !billingManager.isAdsRemovedCached();

        adContainer = findViewById(R.id.ad_container);

        boolean is2024 = SettingsManager.getRulesEdition(this) == RulesEdition.RULES_2024;
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Treasure"));
        if (is2024) tabLayout.addTab(tabLayout.newTab().setText("Tracker"));
        tabLayout.addTab(tabLayout.newTab().setText("Items"));
        tabLayout.addTab(tabLayout.newTab().setText("Spells"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(
                getSupportFragmentManager(), is2024);
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
        if (campaignPrefsListener != null) {
            getApplicationContext()
                    .getSharedPreferences("LootGenCampaigns", Context.MODE_PRIVATE)
                    .unregisterOnSharedPreferenceChangeListener(campaignPrefsListener);
            campaignPrefsListener = null;
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
        int titleRes = edition == RulesEdition.RULES_2014 ? R.string.app_name_2014 : R.string.app_name;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleRes);
        } else {
            setTitle(titleRes);
        }
    }

    private void updateToolbarSubtitle() {
        if (getSupportActionBar() == null) return;
        if (SettingsManager.getRulesEdition(this) != RulesEdition.RULES_2024) {
            getSupportActionBar().setSubtitle(null);
            return;
        }
        Campaign active = new CampaignStore(this).getActive();
        getSupportActionBar().setSubtitle(getString(R.string.toolbar_campaign_subtitle, active.getName()));
    }

    private void showCampaignPickerDialog() {
        CampaignStore store = new CampaignStore(this);
        List<Campaign> campaigns = store.listCampaigns();
        if (campaigns.isEmpty()) return;
        String[] names = new String[campaigns.size()];
        int checkedIndex = 0;
        String activeId = store.getActive().getId();
        for (int i = 0; i < campaigns.size(); i++) {
            names[i] = campaigns.get(i).getName();
            if (campaigns.get(i).getId().equals(activeId)) checkedIndex = i;
        }
        new AlertDialog.Builder(this)
                .setTitle(R.string.toolbar_pick_campaign_title)
                .setSingleChoiceItems(names, checkedIndex, (dialog, which) -> {
                    store.setActive(campaigns.get(which).getId());
                    dialog.dismiss();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
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

    private void openPlayListing() {
        String pkg = getPackageName();
        android.net.Uri market = android.net.Uri.parse("market://details?id=" + pkg);
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW, market);
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            android.net.Uri web = android.net.Uri.parse("https://play.google.com/store/apps/details?id=" + pkg);
            startActivityOrToast(new android.content.Intent(android.content.Intent.ACTION_VIEW, web),
                    R.string.no_browser_app);
        }
    }

    private void sendFeedbackEmail() {
        String email = getString(R.string.feedback_email);
        String versionName;
        try {
            versionName = getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {
            versionName = "";
        }
        String subject = getString(R.string.feedback_subject, versionName);
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SENDTO);
        intent.setData(android.net.Uri.parse("mailto:" + android.net.Uri.encode(email)
                + "?subject=" + android.net.Uri.encode(subject)));
        startActivityOrToast(intent, R.string.no_email_app);
    }

    private void shareApp() {
        String url = "https://play.google.com/store/apps/details?id=" + getPackageName();
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.share_app_text, url));
        startActivity(android.content.Intent.createChooser(intent, getString(R.string.share_app_chooser_title)));
    }

    private void openPrivacyPolicy() {
        android.net.Uri uri = android.net.Uri.parse(getString(R.string.privacy_policy_url));
        startActivityOrToast(new android.content.Intent(android.content.Intent.ACTION_VIEW, uri),
                R.string.no_browser_app);
    }

    private void startActivityOrToast(android.content.Intent intent, int errorStringRes) {
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this, errorStringRes, Toast.LENGTH_SHORT).show();
        }
    }

    private void promptDeleteAllCampaigns() {
        CampaignStore store = new CampaignStore(this);
        List<Campaign> all = store.listCampaigns();
        int count = all.size();
        new AlertDialog.Builder(this)
                .setTitle(R.string.tracker_delete_all_title)
                .setMessage(getString(R.string.tracker_delete_all_message, count))
                .setPositiveButton(R.string.tracker_delete_all_title, (dialog, which) -> {
                    for (Campaign c : all) {
                        store.deleteCampaign(c.getId());
                    }
                    Toast.makeText(this, "All campaigns deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    public boolean about(MenuItem item) {
        showAboutDialog();
        return true;
    }

    private void showAboutDialog() {
        android.widget.LinearLayout container = new android.widget.LinearLayout(this);
        container.setOrientation(android.widget.LinearLayout.VERTICAL);
        float density = getResources().getDisplayMetrics().density;
        int hPad = (int) (24 * density + 0.5f);
        int vPad = (int) (16 * density + 0.5f);
        int gap = (int) (8 * density + 0.5f);
        container.setPadding(hPad, vPad, hPad, 0);

        android.widget.TextView body = new android.widget.TextView(this);
        String text = "Developed by Paul Dante to help save DMs some time.\n\n" +
                "If you like the app and would like to make a donation: https://paypal.me/PDante\n\n" +
                "Background image of scroll provided by https://www.myfreetextures.com";
        body.setText(text);
        body.setTextSize(16);
        body.setAutoLinkMask(android.text.util.Linkify.WEB_URLS);
        body.setLinksClickable(true);
        body.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        container.addView(body);

        android.widget.LinearLayout.LayoutParams btnLp = new android.widget.LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        btnLp.topMargin = gap;

        container.addView(makeAboutButton(R.string.action_rate_app, btnLp, v -> openPlayListing()));
        container.addView(makeAboutButton(R.string.action_send_feedback, btnLp, v -> sendFeedbackEmail()));
        container.addView(makeAboutButton(R.string.action_share_app, btnLp, v -> shareApp()));
        container.addView(makeAboutButton(R.string.action_privacy_policy, btnLp, v -> openPrivacyPolicy()));

        android.widget.ScrollView scroll = new android.widget.ScrollView(this);
        scroll.addView(container);

        new AlertDialog.Builder(this)
                .setTitle("LootForge v1.0")
                .setView(scroll)
                .setPositiveButton(android.R.string.ok, (d, w) -> d.dismiss())
                .show();
    }

    private android.widget.Button makeAboutButton(int textRes,
                                                  android.widget.LinearLayout.LayoutParams lp,
                                                  View.OnClickListener listener) {
        android.widget.Button btn = new android.widget.Button(this);
        btn.setText(textRes);
        btn.setAllCaps(false);
        btn.setLayoutParams(lp);
        btn.setOnClickListener(listener);
        return btn;
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
        if (id == R.id.action_delete_all_campaigns) {
            promptDeleteAllCampaigns();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
