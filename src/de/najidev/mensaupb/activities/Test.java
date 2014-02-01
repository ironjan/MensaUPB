package de.najidev.mensaupb.activities;

import android.os.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.app.*;

import org.androidannotations.annotations.*;

import java.text.*;
import java.util.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.fragments.*;
import de.najidev.mensaupb.stw.*;

@EActivity(R.layout.activity_menu_listing)
public class Test extends ActionBarActivity {

    public static final int WEEKEND_OFFSET = 2;
    @ViewById(R.id.pager)
    ViewPager mViewPager;
    private DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    private static final SimpleDateFormat SDF = Menu.DATABASE_DATE_FORMAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };


        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 3; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText("Tab " + (i + 1))
                            .setTabListener(tabListener));
        }
    }

    @AfterViews
    void showMenus() {
        initPager();
        Bundle arguments = new Bundle();
        arguments.putString(MenuListingFragment.ARG_DATE, SDF.format(getNextWeekDay()));
        arguments.putString(MenuListingFragment.ARG_LOCATION, "Mensa");

        final MenuListingFragment_ fragment = new MenuListingFragment_();
        fragment.setArguments(arguments);

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, fragment);
        ft.commit();
    }

    private void initPager() {
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
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

    // Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new MenuListingFragment_();
            Bundle arguments = new Bundle();

            arguments.putString(MenuListingFragment.ARG_DATE, SDF.format(getNextWeekDay(i)));
            arguments.putString(MenuListingFragment.ARG_LOCATION, "Mensa");

            fragment.setArguments(arguments);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
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
