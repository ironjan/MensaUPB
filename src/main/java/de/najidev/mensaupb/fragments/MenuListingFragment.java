package de.najidev.mensaupb.fragments;


import android.accounts.*;
import android.content.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.*;
import android.view.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.activities.*;
import de.najidev.mensaupb.stw.Menu;
import de.najidev.mensaupb.sync.*;

@EFragment(R.layout.fragment_menu_listing)
public class MenuListingFragment extends ListFragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private static final String SELECTION = de.najidev.mensaupb.stw.Menu.DATE + " = ? AND " + Menu.LOCATION + " = ?";

    public static String ARG_DATE = "date";
    public static String ARG_LOCATION = "location";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }


    public static final String[] LIST_PROJECTION = {de.najidev.mensaupb.stw.Menu.NAME_GERMAN, de.najidev.mensaupb.stw.Menu.CATEGORY, de.najidev.mensaupb.stw.Menu.ALLERGENES, de.najidev.mensaupb.stw.Menu.ID};
    private SimpleCursorAdapter adapter;



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

    Logger LOGGER = LoggerFactory.getLogger(MenuListingFragment.class.getSimpleName());

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
        final long _id = getListAdapter().getItemId(pos);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        MenuDetailFragment fragment = MenuDetailFragment.newInstance(_id);
        fragment.show(fm, "fragment_edit_name");
    }

}
