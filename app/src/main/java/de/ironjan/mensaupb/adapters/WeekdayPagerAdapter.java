package de.ironjan.mensaupb.adapters;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;

import de.ironjan.mensaupb.fragments.*;

public class WeekdayPagerAdapter extends FragmentStatePagerAdapter {

    Fragment[] fragments = new Fragment[WeekdayHelper.DISPLAYED_DAYS_COUNT];
    String mRestaurant;

    WeekdayHelper mWeekdayHelper;

    public WeekdayPagerAdapter(Context context, FragmentManager fm, String restaurant) {
        super(fm);
        mWeekdayHelper = WeekdayHelper_.getInstance_(context);
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

        arguments.putString(MenuListingFragment.ARG_DATE, mWeekdayHelper.getNextWeekDayAsKey(i));
        arguments.putString(MenuListingFragment.ARG_LOCATION, mRestaurant);

        fragment.setArguments(arguments);
        return fragment;
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