package de.ironjan.mensaupb.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.stw.Menu;
import de.ironjan.mensaupb.sync.MenuContentProvider;

@EFragment(R.layout.fragment_menu_listing)
public class MenuListingFragment extends ListFragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    public static final String[] LIST_PROJECTION = {de.ironjan.mensaupb.stw.Menu.NAME_GERMAN, de.ironjan.mensaupb.stw.Menu.CATEGORY, de.ironjan.mensaupb.stw.Menu.ALLERGENES, de.ironjan.mensaupb.stw.Menu.ID};
    private static final String SELECTION = de.ironjan.mensaupb.stw.Menu.DATE + " = ? AND " + Menu.LOCATION + " = ?";
    public static String ARG_DATE = "date";
    public static String ARG_LOCATION = "location";
    Logger LOGGER = LoggerFactory.getLogger(MenuListingFragment.class.getSimpleName());
    private SimpleCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = LIST_PROJECTION;

        final String[] selectionArgs = {getArguments().getString(ARG_DATE), getArguments().getString(ARG_LOCATION)};

        return new CursorLoader(getActivity(),
                MenuContentProvider.MENU_URI, projection, SELECTION, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContent();

    }

    void loadContent() {
        String[] uiBindFrom = LIST_PROJECTION;
        int[] uiBindTo = {R.id.textName, R.id.textCategory, R.id.textAllergenes};
        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(
                getActivity(), R.layout.view_menu_list_item,
                null, uiBindFrom, uiBindTo, 0);
        setListAdapter(adapter);
    }




    @ItemClick
    void listItemClicked(int pos) {
        if (BuildConfig.DEBUG) LOGGER.debug("listItemClicked({})", pos);

        final long _id = getListAdapter().getItemId(pos);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        MenuDetailFragment fragment = MenuDetailFragment.newInstance(_id);
        fragment.show(fm, "fragment_edit_name");

        if (BuildConfig.DEBUG) LOGGER.debug("listItemClicked({}) done", pos);
    }

}
