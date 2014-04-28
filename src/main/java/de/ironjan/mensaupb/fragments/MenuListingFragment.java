package de.ironjan.mensaupb.fragments;


import android.database.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.support.v4.widget.*;
import android.view.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.stw.Menu;
import de.ironjan.mensaupb.stw.*;
import de.ironjan.mensaupb.sync.*;

@EFragment(R.layout.fragment_menu_listing)
public class MenuListingFragment extends ListFragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    public static final String[] LIST_PROJECTION = {de.ironjan.mensaupb.stw.Menu.NAME_GERMAN, de.ironjan.mensaupb.stw.Menu.CATEGORY, de.ironjan.mensaupb.stw.Menu.ALLERGENES, de.ironjan.mensaupb.stw.Menu.ID};
    private static final String SELECTION = de.ironjan.mensaupb.stw.Menu.DATE + " = ? AND " + Menu.LOCATION + " = ?";
    public static String ARG_DATE = "date";
    public static String ARG_LOCATION = "location";
    private final Logger LOGGER = LoggerFactory.getLogger(MenuListingFragment.class.getSimpleName());
    private SimpleCursorAdapter adapter;

    @ViewById(android.R.id.empty)
    View mLoadingView;
    @ViewById(android.R.id.content)
    View mNoMenus;
    @Bean
    OpeningTimesLookup mOpeningTimesLookup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mOpeningTimesLookup.isPotentiallyClosed(getArgDate()
                , getArgLocation())) {
            return inflater.inflate(R.layout.fragment_menu_listing_closed, null);
        }
        return null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = LIST_PROJECTION;

        final String[] selectionArgs = {getArgDate(), getArgLocation()};

        return new CursorLoader(getActivity(),
                MenuContentProvider.MENU_URI, projection, SELECTION, selectionArgs, null);
    }

    private String getArgLocation() {
        return getArguments().getString(ARG_LOCATION);
    }

    private String getArgDate() {
        return getArguments().getString(ARG_DATE);
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
