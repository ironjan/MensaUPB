package de.ironjan.mensaupb.fragments;


import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.widget.*;
import android.view.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.sharedpreferences.*;
import org.slf4j.*;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.activities.*;
import de.ironjan.mensaupb.adapters.*;
import de.ironjan.mensaupb.prefs.*;
import de.ironjan.mensaupb.sync.*;
import se.emilsjolander.stickylistheaders.*;

@SuppressWarnings("WeakerAccess")
@EFragment(R.layout.fragment_menu_listing)
public class MenuListingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String ARG_DATE = "date";
    public static final String ARG_LOCATION = "restaurant";

    private final Logger LOGGER = LoggerFactory.getLogger(MenuListingFragment.class.getSimpleName());

    @ViewById(android.R.id.empty)
    View mLoadingView;
    @ViewById(R.id.noMenusToday)
    View mNoMenus;
    @ViewById(android.R.id.list)
    StickyListHeadersListView list;
    @Pref
    InternalKeyValueStore_ mInternalKeyValueStore;

    @Bean
    AccountCreator mAccountCreator;
    private MenuListingAdapter adapter;

    public static MenuListingFragment getInstance(String dateAsKey, String restaurant) {
        MenuListingFragment fragment = new MenuListingFragment_();
        Bundle arguments = new Bundle();

        arguments.putString(MenuListingFragment.ARG_DATE, dateAsKey);
        arguments.putString(MenuListingFragment.ARG_LOCATION, restaurant);

        fragment.setArguments(arguments);
        return fragment;
    }

    private String getArgLocation() {
        String location = getArguments().getString(ARG_LOCATION);
        return location.replaceAll("\\*", "%");
    }

    private String getArgDate() {
        return getArguments().getString(ARG_DATE);
    }

    @AfterViews
    void loadContent() {
        long lastSyncTimeStamp = mInternalKeyValueStore.lastSyncTimeStamp().get();
        if (0L == lastSyncTimeStamp) {
            list.setEmptyView(mLoadingView);
        } else {
            list.setEmptyView(mNoMenus);
        }
        list.setAreHeadersSticky(false);
        adapter = new MenuListingAdapter(getActivity(), getArgDate(), getArgLocation());
        adapter.setViewBinder(new MenuDetailViewBinder());
        getLoaderManager().initLoader(0, null, adapter);
        list.setAdapter(adapter);
    }

    @ItemClick
    void listItemClicked(int pos) {
        if (BuildConfig.DEBUG) LOGGER.debug("listItemClicked({})", pos);

        final long _id = adapter.getItemId(pos);
        MenuDetails_.intent(this).menuId(_id).start();

        if (BuildConfig.DEBUG) LOGGER.debug("listItemClicked({}) done", pos);
    }

    @Override
    public void onRefresh() {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mAccountCreator.getAccount(), AccountCreator.AUTHORITY, settingsBundle);
        LOGGER.debug("Sync requested.");
    }

}
