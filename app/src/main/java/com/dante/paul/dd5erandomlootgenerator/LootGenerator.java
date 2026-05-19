package com.dante.paul.dd5erandomlootgenerator;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

import java.util.concurrent.atomic.AtomicBoolean;

public class LootGenerator extends AppCompatActivity {

    private FrameLayout adContainer;
    private AdView adView;
    private ConsentInformation consentInformation;
    private final AtomicBoolean adsSdkInitialized = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loot_generator);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        adContainer = findViewById(R.id.ad_container);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Treasure"));
        tabLayout.addTab(tabLayout.newTab().setText("Items"));
        tabLayout.addTab(tabLayout.newTab().setText("Spells"));
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

        requestConsentAndLoadAd();
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
        if (adView != null) return;
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
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loot_generator, menu);
        return true;
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
