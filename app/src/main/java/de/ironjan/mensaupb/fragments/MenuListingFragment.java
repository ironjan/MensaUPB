package de.ironjan.mensaupb.fragments;


import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.widget.*;
import android.view.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.activities.*;
import de.ironjan.mensaupb.adapters.*;
import de.ironjan.mensaupb.sync.*;
import se.emilsjolander.stickylistheaders.*;

@EFragment(R.layout.fragment_menu_listing)
public class MenuListingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SyncStatusObserver {

    public static String ARG_DATE = "date";
    public static String ARG_LOCATION = "restaurant";

    private final Logger LOGGER = LoggerFactory.getLogger(MenuListingFragment.class.getSimpleName());
    @ViewById(android.R.id.empty)
    View mLoadingView;
    @ViewById(android.R.id.content)
    View mNoMenus;
    @ViewById(android.R.id.list)
    StickyListHeadersListView list;

    @ViewById(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @Bean
    AccountCreator mAccountCreator;
    private MenuListingAdapter adapter;
    private Object mChangeListenerHandle;

    private String getArgLocation() {
        return getArguments().getString(ARG_LOCATION);
    }

    private String getArgDate() {
        return getArguments().getString(ARG_DATE);
    }

    @AfterViews
    void loadContent() {
        list.setEmptyView(mLoadingView);
        list.setAreHeadersSticky(false);
        adapter = new MenuListingAdapter(getActivity(), getArgDate(), getArgLocation());
        adapter.setViewBinder(new MenuDetailViewBinder());
        getLoaderManager().initLoader(0, null, adapter);
        list.setAdapter(adapter);
        swipeRefresh.setOnRefreshListener(this);
    }

    @AfterInject
    void addStatusListener() {
        int mask = ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE | ContentResolver.SYNC_OBSERVER_TYPE_PENDING;
        mChangeListenerHandle = ContentResolver.addStatusChangeListener(mask, this);
        LOGGER.debug("Added status listener");
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
        addStatusListener();
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mAccountCreator.getAccount(), AccountCreator.AUTHORITY, settingsBundle);
        LOGGER.debug("Sync requested.");
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onStatusChanged(int which) {
//        LOGGER.debug("onStatusChanged({})",which);
//        not working yet...

//        boolean syncActive = ContentResolver.isSyncActive(mAccountCreator.getAccount(), mAccountCreator.getAuthority());
//        boolean syncPending = ContentResolver.isSyncPending(mAccountCreator.getAccount(), mAccountCreator.getAuthority());
//        boolean refreshing = syncActive || syncPending;
//
//        swipeRefresh.setRefreshing(refreshing);
//
//        LOGGER.debug("onStatusChanged({}) done",which);
    }
}
