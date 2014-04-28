package de.ironjan.mensaupb.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import de.ironjan.mensaupb.fragments.MenuListingFragment;
import de.ironjan.mensaupb.fragments.MenuListingFragment_;

public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

    Fragment[] fragments = new Fragment[3];
    String mRestaurant;

    WeekdayHelper mWeekdayHelper;

    public DemoCollectionPagerAdapter(Context context, FragmentManager fm, String restaurant) {
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

        arguments.putString(MenuListingFragment.ARG_DATE, mWeekdayHelper.getNextWeekDayAsString(i));
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
        return mWeekdayHelper.getNextWeekDayAsString(position);
    }

}