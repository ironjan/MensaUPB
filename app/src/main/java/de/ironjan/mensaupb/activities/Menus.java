package de.ironjan.mensaupb.activities;


import android.annotation.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.widget.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.adapters.*;
import de.ironjan.mensaupb.sync.*;

@SuppressLint("Registered")
@EActivity(R.layout.activity_menu_listing)
@OptionsMenu(R.menu.main)
public class Menus extends ActionBarActivity implements ActionBar.OnNavigationListener {


    private final Logger LOGGER = LoggerFactory.getLogger(Menus.class.getSimpleName());
    @ViewById(R.id.pager)
    ViewPager mViewPager;
    @ViewById(R.id.pager_title_strip)
    PagerTabStrip mPagerTabStrip;
    @StringArrayRes(R.array.restaurants)
    String[] mRestaurants;
    @StringArrayRes(R.array.displayedRestaurants)
    String[] mDisplayedRestaurants;
    @StringArrayRes(R.array.restaurantUrls)
    String[] mRestaurantUrls;
    @Bean
    WeekdayHelper mwWeekdayHelper;
    @Bean
    AccountCreator mAccountCreator;
    private FragmentStatePagerAdapter[] adapters;
    private FragmentStatePagerAdapter mWeekdayPagerAdapter;
    private int mLocation = 0;

    @Trace
    @AfterViews
    @Background
    void init() {
        initPager();
        initActionBar();
    }


    @Trace
    @UiThread
    void initActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                mDisplayedRestaurants);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actionBar.setListNavigationCallbacks(adapter, this);
        actionBar.setSelectedNavigationItem(mLocation);
    }

    @Trace
    void initPager() {
        mPagerTabStrip.setTabIndicatorColorResource(R.color.iconBg);
        mPagerTabStrip.setDrawFullUnderline(true);

        loadPagerAdapter(mLocation);
    }

    @Background
    @Trace
    void loadPagerAdapter(int i) {
        if (BuildConfig.DEBUG) LOGGER.debug("loadPagerAdapter({})", i);
        mWeekdayPagerAdapter =
                getPagerAdapter(i);
        if (BuildConfig.DEBUG) LOGGER.info("Got adapter: {}", mWeekdayPagerAdapter);
        if (mWeekdayPagerAdapter != null) {
            switchAdapter();
        }
    }

    @UiThread
    @Trace
    void switchAdapter() {
        mViewPager.setAdapter(mWeekdayPagerAdapter);
    }

    @Trace
    FragmentStatePagerAdapter getPagerAdapter(int i) {
        if (BuildConfig.DEBUG) LOGGER.debug("getPagerAdapter({})", i);
        if (adapters == null) {
            adapters = new RestaurantPagerAdapter[1]; // fixme replace with displayed date count
        }
        if (adapters[i] == null) {
            createNewAdapter(i);
        }
        return adapters[i];
    }

    @Trace
    @Background
    void createNewAdapter(int i) {
        if (BuildConfig.DEBUG) LOGGER.debug("createNewAdapter({})", i);
        adapters[i] =
                new RestaurantPagerAdapter(this, getSupportFragmentManager());
        loadPagerAdapter(i);
    }


    @Override
    @Trace
    public boolean onNavigationItemSelected(int i, long l) {
        mLocation = i;
        if (BuildConfig.DEBUG)
            LOGGER.debug("onNavigationItemSelected({},{}), location := {}", new Object[]{i, l, mLocation});

        loadPagerAdapter(mLocation);

        return true;
    }


    @OptionsItem(R.id.ab_refresh)
    void refreshClicked() {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        ContentResolver.requestSync(mAccountCreator.getAccount(), mAccountCreator.getAuthority(), settingsBundle);
    }

    @OptionsItem(R.id.ab_STW)
    void stwClicked() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(mRestaurantUrls[mLocation]));
        startActivity(intent);
    }

    @OptionsItem(R.id.ab_about)
    void aboutClicked() {
        About_.intent(this).start();
    }


}
