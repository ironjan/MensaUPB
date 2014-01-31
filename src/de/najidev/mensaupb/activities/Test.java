package de.najidev.mensaupb.activities;

import android.accounts.*;
import android.app.*;
import android.content.*;
import android.database.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.stw.Menu;
import de.najidev.mensaupb.sync.*;

@EActivity(R.layout.fragment_menu_listing)
public class Test extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String[] LIST_PROJECTION = {Menu.NAME_GERMAN, Menu.CATEGORY, Menu.ALLERGENES, Menu.ID};
    private SimpleCursorAdapter adapter;

    @Bean
    AccountCreator mAccountCreator;

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = LIST_PROJECTION;
        CursorLoader cursorLoader = new CursorLoader(this,
                MenuContentProvider.MENU_URI, projection, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        adapter.swapCursor(null);
    }


    Logger LOGGER = LoggerFactory.getLogger("Test");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] uiBindFrom = LIST_PROJECTION;
        int[] uiBindTo = {R.id.textName, R.id.textCategory, R.id.textAllergenes};
        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(
                this, R.layout.view_menu_list_item,
                null, uiBindFrom, uiBindTo, 0);
        LOGGER.warn("{} --> {}", uiBindFrom, uiBindTo);

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
            LOGGER.warn("{} --> {}", cursor.getString(i), view.getId());
                String[] cc = {cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)};
                LOGGER.warn("cursor has: {},{},{},{}", cc);
                return false;
            }
        });
        setListAdapter(adapter);
        setupSynchronization();
    }

    private void setupSynchronization() {
        final Account account = mAccountCreator.build(this);

        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(account, mAccountCreator.getAuthority(), settingsBundle);

        ContentResolver.addPeriodicSync(account, mAccountCreator.getAuthority(), new Bundle(), BuildConfig.SYNC_INTERVAL);
    }


}
