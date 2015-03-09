package de.ironjan.mensaupb.activities;


import android.annotation.*;
import android.content.*;
import android.os.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.widget.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

import java.util.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.adapters.*;
import de.ironjan.mensaupb.helpers.*;
import de.ironjan.mensaupb.sync.*;

@SuppressWarnings("WeakerAccess")
@SuppressLint("Registered")
@EActivity(R.layout.activity_menu_listing)
@OptionsMenu(R.menu.main)
public class Menus extends ActionBarActivity implements ActionBar.OnNavigationListener {

    public static final String KEY_DAY_OFFSET = "KEY_DAY_OFFSET";
    public static final String KEY_DATE = "KEY_DATE";
    public static final String KEY_RESTAURANT = "KEY_RESTAURANT";
    private final Logger LOGGER = LoggerFactory.getLogger(Menus.class.getSimpleName());
    @Extra(value = KEY_DATE)
    String dateAsString = null;

    @ViewById(R.id.pager)
    ViewPager mViewPager;
    @ViewById(R.id.pager_title_strip)
    PagerTabStrip mPagerTabStrip;
    @StringArrayRes(R.array.restaurants)
    String[] mRestaurants;
    @StringArrayRes(R.array.displayedRestaurants)
    String[] mDisplayedRestaurants;
    @Bean
    WeekdayHelper mwWeekdayHelper;
    @Bean
    AccountCreator mAccountCreator;
    @Extra(value = KEY_RESTAURANT)
    String restaurant = null;
    private WeekdayPagerAdapter[] adapters;
    private WeekdayPagerAdapter mWeekdayPagerAdapter;
    private int mLocation = 0;
    private int mDayOffset = 0;

    @Trace
    @AfterViews
    @Background
    void init() {
        if (dateAsString != null) {
            Date date = DateHelper.toDate(dateAsString);
            mDayOffset = DateHelper.computeDayOffset(date);
        }
        if (restaurant != null) {
            mLocation = findLocationId(restaurant);
        }
        LOGGER.warn("Extras: location={}, dayOffset={}", mLocation, mDayOffset);
        initPager();
        initActionBar();
    }

    private int findLocationId(String restaurant) {
        for (int i = 0; i < mRestaurants.length; i++) {
            if (restaurant.equals(mRestaurants[i])) {
                return i;
            }
        }
        return 0;
    }


    @Trace
    @UiThread
    void initActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                mDisplayedRestaurants);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actionBar.setListNavigationCallbacks(adapter, this);
        actionBar.setSelectedNavigationItem(mLocation);
    }

    @Trace
    @UiThread
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
            switchAdapterTo(mDayOffset);
        }
    }

    @UiThread
    void switchAdapterTo(int currentItem) {
        LOGGER.warn("switch to {}", currentItem);
        mViewPager.setAdapter(mWeekdayPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
    }

    @Trace
    WeekdayPagerAdapter getPagerAdapter(int i) {
        if (BuildConfig.DEBUG) LOGGER.debug("getPagerAdapter({})", i);
        if (adapters == null) {
            adapters = new WeekdayPagerAdapter[mRestaurants.length];
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
                new WeekdayPagerAdapter(this, getSupportFragmentManager(), mRestaurants[i]);
        loadPagerAdapter(i);
    }


    @Override
    @Trace
    public boolean onNavigationItemSelected(int i, long l) {
        mLocation = i;
        if (BuildConfig.DEBUG)
            LOGGER.debug("onNavigationItemSelected({},{}), location := {}", new Object[]{i, l, mLocation});
        mDayOffset = mViewPager.getCurrentItem();
        loadPagerAdapter(i);

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

    @OptionsItem(R.id.ab_about)
    void aboutClicked() {
        About_.intent(this).start();
    }

}
