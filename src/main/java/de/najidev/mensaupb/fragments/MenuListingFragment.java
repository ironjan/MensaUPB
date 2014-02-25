package de.najidev.mensaupb.fragments;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.SyncInfo;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import de.najidev.mensaupb.BuildConfig;
import de.najidev.mensaupb.R;
import de.najidev.mensaupb.stw.Allergene;
import de.najidev.mensaupb.stw.Menu;
import de.najidev.mensaupb.sync.AccountCreator;
import de.najidev.mensaupb.sync.MenuContentProvider;

/**
 * Created by ljan on 01.02.14.
 */
@EFragment(R.layout.fragment_menu_listing)
@OptionsMenu(R.menu.main)
public class MenuListingFragment extends ListFragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, SyncStatusObserver {

    private static final String SELECTION = de.najidev.mensaupb.stw.Menu.DATE + " = ? AND " + Menu.LOCATION + " = ?";

    public static String ARG_DATE = "date";
    public static String ARG_LOCATION = "location";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }


    public static final String[] LIST_PROJECTION = {de.najidev.mensaupb.stw.Menu.NAME_GERMAN, de.najidev.mensaupb.stw.Menu.CATEGORY, de.najidev.mensaupb.stw.Menu.ALLERGENES, de.najidev.mensaupb.stw.Menu.ID};
    private SimpleCursorAdapter adapter;

    @Bean
    AccountCreator mAccountCreator;


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = LIST_PROJECTION;

        final String[] selectionArgs = {getArguments().getString(ARG_DATE), getArguments().getString(ARG_LOCATION)};

        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                MenuContentProvider.MENU_URI, projection, SELECTION, selectionArgs, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    Logger LOGGER = LoggerFactory.getLogger(MenuListingFragment.class.getSimpleName());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] uiBindFrom = LIST_PROJECTION;
        int[] uiBindTo = {R.id.textName, R.id.textCategory, R.id.textAllergenes};
        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(
                getActivity(), R.layout.view_menu_list_item,
                null, uiBindFrom, uiBindTo, 0);
        setListAdapter(adapter);
        setupSynchronization();

        int mask = ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE | ContentResolver.SYNC_OBSERVER_TYPE_PENDING | ContentResolver.SYNC_OBSERVER_TYPE_SETTINGS;
        ContentResolver.addStatusChangeListener(mask, this);
    }

    private void setupSynchronization() {
        final Account account = mAccountCreator.build(getActivity());

        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(account, mAccountCreator.getAuthority(), settingsBundle);

        ContentResolver.addPeriodicSync(account, mAccountCreator.getAuthority(), new Bundle(), BuildConfig.SYNC_INTERVAL);
    }

    @OptionsItem(R.id.ab_refresh)
    void refreshClicked() {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        ContentResolver.requestSync(mAccountCreator.build(getActivity()), mAccountCreator.getAuthority(), settingsBundle);
    }


    @Override
    @UiThread
    public void onStatusChanged(int status) {
        List<SyncInfo> currentSyncs = ContentResolver.getCurrentSyncs();

        for (SyncInfo syncInfo : currentSyncs) {
            String name = syncInfo.account.name;
            String authority = syncInfo.authority;


            if (name.equals(AccountCreator.ACCOUNT) && authority.equals(authority)) {
                getActivity().setProgressBarIndeterminate(true);
                getActivity().setProgressBarVisibility(true);
                return;

            }
        }
        getActivity().setProgressBarIndeterminate(false);
        getActivity().setProgressBarVisibility(false);
    }

    @ItemClick
    void listItemClicked(int pos) {
        final long _id = getListAdapter().getItemId(pos);
        Uri uri = Uri.withAppendedPath(MenuContentProvider.MENU_URI, "" + _id);
        String[] projection = {Menu.NAME_GERMAN, Menu.ALLERGENES, Menu.ID};
        Cursor query = getActivity().getContentResolver().query(uri, projection, null, null, null);

        query.moveToNext();
        String name = query.getString(0);
        String allergenes = query.getString(1);
        query.close();

        String explainedAllergens = Allergene.getExplanation(allergenes);

        LOGGER.warn(name);
        LOGGER.warn(explainedAllergens);
    }
}
