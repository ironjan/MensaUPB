package de.najidev.mensaupb.activities;

import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.text.*;
import android.widget.*;

import org.androidannotations.annotations.*;

import java.text.*;
import java.util.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.fragments.*;
import de.najidev.mensaupb.stw.*;

@EActivity(R.layout.activity_menu_listing)
public class Test extends ActionBarActivity implements ActionBar.OnNavigationListener {

    public static final int WEEKEND_OFFSET = 2;
    @ViewById(R.id.pager)
    ViewPager mViewPager;
    private DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    private static final SimpleDateFormat SDF = Menu.DATABASE_DATE_FORMAT;
    public static final String[] RESTAURANTS = new String[]{"Mensa", "Abendmensa", "Gownsmen's Pub", "HNF (Hotspot)"};
    private String mLocation = "Mensa";
    private DemoCollectionPagerAdapter[] adapters = new DemoCollectionPagerAdapter[4];


    @AfterViews
    void showMenus() {
        initActionBar();
        initPager();
    }

    private void initActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Specify a SpinnerAdapter to populate the dropdown list.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                RESTAURANTS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actionBar.setListNavigationCallbacks(adapter, this);
    }

    private void initPager() {
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        switchPagerAdapter(0);
    }

    private void switchPagerAdapter(int i) {
        mDemoCollectionPagerAdapter =
                getPagerAdapter(i);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }

    private DemoCollectionPagerAdapter getPagerAdapter(int i) {
        if (adapters[i] == null) {
            adapters[i] =
                    new DemoCollectionPagerAdapter(
                            getSupportFragmentManager(), RESTAURANTS[i]);
        }
        return adapters[i];
    }

    private String getNextWeekDayAsString(int i) {
        return SDF.format(getNextWeekDay(i));
    }

    private Date getNextWeekDay() {
        Calendar cal = Calendar.getInstance();
        while (dayIsWeekend(cal)) {
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }

        return cal.getTime();
    }

    private boolean dayIsWeekend(Calendar cal) {
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        if (TextUtils.equals(mLocation, RESTAURANTS[i]))
            return true;

        mLocation = RESTAURANTS[i];

        switchPagerAdapter(i);

        return true;
    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        Fragment[] fragments = new Fragment[3];
        String mLocation;

        public DemoCollectionPagerAdapter(FragmentManager fm, String location) {
            super(fm);
            mLocation = location;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = getMenuListingFragment(i);
            return fragment;
        }

        private Fragment getMenuListingFragment(int i) {
            if (fragments[i] == null) {
                Fragment fragment = initMenuListingFragment(i);
                fragments[i] = fragment;
            }

            return fragments[i];
        }

        private Fragment initMenuListingFragment(int i) {
            Fragment fragment = new MenuListingFragment_();
            Bundle arguments = new Bundle();

            arguments.putString(MenuListingFragment.ARG_DATE, getNextWeekDayAsString(i));
            arguments.putString(MenuListingFragment.ARG_LOCATION, mLocation);

            fragment.setArguments(arguments);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getNextWeekDayAsString(position);
        }
    }


    private String getLocation() {
        return mLocation;
    }

    private Date getNextWeekDay(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getNextWeekDay());

        cal.add(Calendar.DAY_OF_WEEK, offset);

        if (dayIsWeekend(cal)) {
            cal.add(Calendar.DAY_OF_WEEK, WEEKEND_OFFSET);
        }
        return cal.getTime();
    }


}
