package de.ironjan.mensaupb.adapters;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.fragments.*;

/**
 * Created by ljan on 19.01.15.
 */
public class RestaurantPagerAdapter extends FragmentStatePagerAdapter {
    private final String[] restaurantKeys, displayedRestaurants;
    private final String mTodayKey;
    private Fragment[] fragments;

    public RestaurantPagerAdapter(Context context, FragmentManager fm, String dayKey) {
        super(fm);
        this.mTodayKey = dayKey;
        restaurantKeys = context.getResources().getStringArray(R.array.restaurants);
        displayedRestaurants = context.getResources().getStringArray(R.array.displayedRestaurants);
        fragments = new Fragment[restaurantKeys.length];
    }

    @Override
    public Fragment getItem(int position) {
        return getMenuListingFragment(position);
    }

    private Fragment getMenuListingFragment(int position) {
        if (fragments[position] == null) {
            Fragment fragment = initMenuListingFragment(position);
            fragments[position] = fragment;
        }

        return fragments[position];
    }

    private Fragment initMenuListingFragment(int position) {
        Fragment fragment = new MenuListingFragment_();
        Bundle arguments = new Bundle();

        arguments.putString(MenuListingFragment.ARG_DATE, mTodayKey);
        arguments.putString(MenuListingFragment.ARG_LOCATION, restaurantKeys[position]);

        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getCount() {
        return restaurantKeys.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return displayedRestaurants[position];
    }
}
