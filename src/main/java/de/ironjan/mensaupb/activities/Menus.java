package de.ironjan.mensaupb.activities;


import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SyncInfo;
import android.content.SyncStatusObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.widget.ArrayAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.fragments.MenuListingFragment;
import de.ironjan.mensaupb.fragments.MenuListingFragment_;
import de.ironjan.mensaupb.fragments.RestaurantDetailFragment;
import de.ironjan.mensaupb.stw.*;
import de.ironjan.mensaupb.sync.AccountCreator;

@EActivity(R.layout.activity_menu_listing)
@OptionsMenu(R.menu.main)
public class Menus extends ActionBarActivity implements ActionBar.OnNavigationListener {
    public static final int WEEKEND_OFFSET = 2;
    private static final SimpleDateFormat SDF = Menu.DATABASE_DATE_FORMAT;
    private static final int DISPLAYED_DAYS_COUNT = 3;
    private String[] weekDaysAsString = new String[DISPLAYED_DAYS_COUNT];
    private final Logger LOGGER = LoggerFactory.getLogger(Menus.class.getSimpleName());

    @ViewById(R.id.pager)
    ViewPager mViewPager;
    @ViewById(R.id.pager_title_strip)
    PagerTabStrip mPagerTabStrip;

    @StringArrayRes(R.array.restaurants)
    String[] mRestaurants;

    @StringArrayRes(R.array.displayedRestaurants)
    String[] mDisplayedRestaurants;

    @Bean
    AccountCreator mAccountCreator;
    private DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    private int mLocation = 0;
    private DemoCollectionPagerAdapter[] adapters = new DemoCollectionPagerAdapter[4];
    @Bean
    RestaurantProvider mRestaurantProvider;

    @Trace
    @AfterViews
    @Background
    void init() {
        mLocation = mRestaurantProvider.getLocation();
        initDays();
        initPager();
        initActionBar();
    }

    @Trace
    void initDays() {
        for (int i = 0; i < DISPLAYED_DAYS_COUNT; i++) {
            getNextWeekDayAsString(i);
        }
    }

    @Trace
    @UiThread
    void initActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Specify a SpinnerAdapter to populate the dropdown list.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                mDisplayedRestaurants);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actionBar.setListNavigationCallbacks(adapter, this);
        actionBar.setSelectedNavigationItem(mLocation);
    }

    @Trace
    void initPager() {
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mPagerTabStrip.setTabIndicatorColorResource(R.color.iconBg);
        mPagerTabStrip.setDrawFullUnderline(true);

        loadPagerAdapter(mLocation);
    }

    @Background
    @Trace
    void loadPagerAdapter(int i) {
        if (BuildConfig.DEBUG) LOGGER.debug("loadPagerAdapter({})", i);
        mDemoCollectionPagerAdapter =
                getPagerAdapter(i);
        if (BuildConfig.DEBUG) LOGGER.info("Got adapter: {}", mDemoCollectionPagerAdapter);
        if (mDemoCollectionPagerAdapter != null) {
            switchAdapter();
        }
    }

    @UiThread
    @Trace
    void switchAdapter() {
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }

    @Trace
    DemoCollectionPagerAdapter getPagerAdapter(int i) {
        if (BuildConfig.DEBUG) LOGGER.debug("getPagerAdapter({})", i);
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
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager(), mRestaurants[i]);
        loadPagerAdapter(i);
    }

    @Trace
    synchronized String getNextWeekDayAsString(int i) {

        if (weekDaysAsString[i] == null) {
            weekDaysAsString[i] = SDF.format(getNextWeekDay(i));
        }

        if (BuildConfig.DEBUG)
            LOGGER.debug("getNextWeekDayAsString({}) -> {}", i, weekDaysAsString[i]);
        return weekDaysAsString[i];
    }

    @Trace
    boolean dayIsWeekend(Calendar cal) {
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
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

    @Trace
    Date getNextWeekDay(int offset) {
        if (BuildConfig.DEBUG) LOGGER.debug("getNextWeekDay({})", offset);
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DAY_OF_WEEK, offset);

        if (dayIsWeekend(cal)) {
            cal.add(Calendar.DAY_OF_WEEK, WEEKEND_OFFSET);
        }
        return cal.getTime();
    }

    @OptionsItem(R.id.ab_refresh)
    void refreshClicked() {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        ContentResolver.requestSync(mAccountCreator.build(this), mAccountCreator.getAuthority(), settingsBundle);
    }

    @OptionsItem(R.id.ab_STW)
    void stwClicked() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.studentenwerk-pb.de/startseite/"));
        startActivity(intent);
    }

    @OptionsItem(R.id.ab_about)
    void aboutClicked() {
        About_.intent(this).start();
    }

    @OptionsItem(R.id.ab_times)
    void timesClicked() {
        FragmentManager fm = getSupportFragmentManager();
        RestaurantDetailFragment fragment = RestaurantDetailFragment.newInstance(mLocation);
        fragment.show(fm, "fragment_edit_name");
    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        Fragment[] fragments = new Fragment[3];
        String mRestaurant;

        public DemoCollectionPagerAdapter(FragmentManager fm, String restaurant) {
            super(fm);
            mRestaurant = restaurant;
        }


        @Override
        public Fragment getItem(int i) {
            return getMenuListingFragment(i);
        }

        Fragment getMenuListingFragment(int i) {
            if (fragments[i] == null) {
                Fragment fragment = initMenuListingFragment(i);
                fragments[i] = fragment;
            }

            return fragments[i];
        }

        Fragment initMenuListingFragment(int i) {
            Fragment fragment = new MenuListingFragment_();
            Bundle arguments = new Bundle();

            arguments.putString(MenuListingFragment.ARG_DATE, getNextWeekDayAsString(i));
            arguments.putString(MenuListingFragment.ARG_LOCATION, mRestaurant);

            fragment.setArguments(arguments);
            return fragment;
        }

        @Override
        public int getCount() {
            return DISPLAYED_DAYS_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getNextWeekDayAsString(position);
        }

    }

}
