package de.ironjan.mensaupb.menus_ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class WeekdayPagerAdapter extends FragmentStatePagerAdapter {

    private final Fragment[] fragments = new Fragment[WeekdayHelper.DISPLAYED_DAYS_COUNT];
    private final String mRestaurant;

    private final WeekdayHelper mWeekdayHelper;

    public WeekdayPagerAdapter(Context context, FragmentManager fm, String restaurant) {
        super(fm);
        mWeekdayHelper = WeekdayHelper_.getInstance_(context);
        mRestaurant = restaurant;
    }


    @Override
    public Fragment getItem(int i) {
        return getMenuListingFragment(i);
    }

    private Fragment getMenuListingFragment(int i) {
        if (fragments[i] == null) {
            String nextWeekDayAsKey = mWeekdayHelper.getNextWeekDayAsKey(i);
            Fragment fragment = MenuListingFragment.getInstance(nextWeekDayAsKey, mRestaurant);
            fragments[i] = fragment;
        }

        return fragments[i];
    }


    @Override
    public int getCount() {
        return WeekdayHelper.DISPLAYED_DAYS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mWeekdayHelper.getNextWeekDayForUI(position);
    }

}