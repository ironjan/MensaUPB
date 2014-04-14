package de.ironjan.mensaupb.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringArrayRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;

@EFragment(R.layout.fragment_restaurant_detail)
public class RestaurantDetailFragment extends DialogFragment {

    public static final String ARG_ID = "ARG_ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantDetailFragment.class.getSimpleName());
    @ViewById
    TextView content;

    @StringArrayRes(R.array.restaurants)
    String[] restaurants;

    @StringArrayRes(R.array.opening_times)
    String[] mOpeningTimes;

    public static RestaurantDetailFragment newInstance(long _id) {
        if (BuildConfig.DEBUG) LOGGER.debug("newInstance({})", _id);

        Bundle args = new Bundle();
        args.putLong(ARG_ID, _id);

        RestaurantDetailFragment menuDetailFragment = new RestaurantDetailFragment_();
        menuDetailFragment.setArguments(args);

        if (BuildConfig.DEBUG) LOGGER.debug("Created new MenuDetailFragment({})", _id);

        if (BuildConfig.DEBUG) LOGGER.debug("newInstance({}) done", _id);
        return menuDetailFragment;
    }

    @AfterViews
    void bindData() {
        if (BuildConfig.DEBUG) LOGGER.debug("bindData()");

        final int _id = getArguments().getInt(ARG_ID);

        showDetails(_id);

        // FIXME show opening times
        if (BuildConfig.DEBUG) LOGGER.debug("bindData()");
    }

    private void showDetails(int _id) {
        setTitle(restaurants[_id]);
        content.setText(mOpeningTimes[_id]);
    }


    private void setTitle(String title) {
        getDialog().setTitle(title);
    }
}
