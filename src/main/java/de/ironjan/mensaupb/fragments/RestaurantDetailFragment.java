package de.ironjan.mensaupb.fragments;


import android.os.*;
import android.support.v4.app.*;
import android.widget.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;
import org.slf4j.*;

import de.ironjan.mensaupb.*;

@EFragment(R.layout.fragment_restaurant_detail)
public class RestaurantDetailFragment extends DialogFragment {

    public static final String ARG_ID = "ARG_ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantDetailFragment.class.getSimpleName());
    @ViewById
    TextView content;

    @StringArrayRes(R.array.displayedRestaurants)
    String[] mDisplayedRestaurants;

    @StringArrayRes(R.array.opening_times)
    String[] mOpeningTimes;

    public static RestaurantDetailFragment newInstance(int _id) {
        if (BuildConfig.DEBUG) LOGGER.debug("newInstance({})", _id);

        Bundle args = new Bundle();
        args.putInt(ARG_ID, _id);

        RestaurantDetailFragment menuDetailFragment = new RestaurantDetailFragment_();
        menuDetailFragment.setArguments(args);

        if (BuildConfig.DEBUG) LOGGER.trace("Created new MenuDetailFragment({})", _id);
        return menuDetailFragment;
    }

    @AfterViews
    void bindData() {
        if (BuildConfig.DEBUG) LOGGER.trace("bindData()");

        final int _id = getArguments().getInt(ARG_ID);

        showDetails(_id);

        // FIXME show opening times
        if (BuildConfig.DEBUG) LOGGER.debug("bindData()");
    }

    private void showDetails(int _id) {
        if (BuildConfig.DEBUG) LOGGER.debug("showDetails({})", _id);

        setTitle(mDisplayedRestaurants[_id]);
        content.setText(mOpeningTimes[_id]);

        if (BuildConfig.DEBUG) LOGGER.debug("showDetails({}) done", _id);
    }


    private void setTitle(String title) {
        getDialog().setTitle(title);
    }
}
