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


    @AfterViews
    void showMenus() {
        initPager();
    }

    private void initPager() {
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
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

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        Fragment[] fragments = new Fragment[3];

        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
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
            return getNextWeekDayAsString(position);
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
