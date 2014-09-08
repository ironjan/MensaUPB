package de.ironjan.mensaupb.fragments;


import android.app.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.*;

import com.j256.ormlite.stmt.query.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.adapters.*;
import de.ironjan.mensaupb.library.stw.*;
import de.ironjan.mensaupb.library.stw.deprecated.Menu;
import de.ironjan.mensaupb.sync.*;

@EFragment(R.layout.fragment_menu_detail)
public class MenuDetailFragment extends DialogFragment {

    public static final String ARG_ID = "ARG_ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuDetailFragment.class.getSimpleName());
    @ViewById
    TextView textName, textCategory, textAllergens, textPrice;

    public static MenuDetailFragment newInstance(long _id) {
        if (BuildConfig.DEBUG) LOGGER.debug("newInstance({})", _id);

        Bundle args = new Bundle();
        args.putLong(ARG_ID, _id);

        MenuDetailFragment menuDetailFragment = new MenuDetailFragment_();
        menuDetailFragment.setArguments(args);

        if (BuildConfig.DEBUG) LOGGER.debug("Created new MenuDetailFragment({})", _id);

        if (BuildConfig.DEBUG) LOGGER.debug("newInstance({}) done", _id);
        return menuDetailFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Details");
        return dialog;
    }

    @AfterViews
    void bindData() {
        if (BuildConfig.DEBUG) LOGGER.debug("bindData()");

        final long _id = getArguments().getLong(ARG_ID);
        Uri uri = Uri.withAppendedPath(MenuContentProvider.MENU_URI, "" + _id);

        // FIXME allergens!
        String[] projection = {RawMenu.NAME_GERMAN, RawMenu.CATEGORY, RawMenu.STUDENTS_PRICE, BaseColumns._ID};
        int[] bindTo = {R.id.textName, R.id.textCategory, R.id.textPrice};
textAllergens.setText("Allergene werden zur Zeit nicht angezeigt. Bitte informieren Sie sich an der Essensausgabe!");
        Cursor query = getActivity().getContentResolver().query(uri, projection, null, null, null);
        query.moveToFirst();

        MenuDetailViewBinder viewBinder = new MenuDetailViewBinder();

        for (int i = 0; i < bindTo.length; i++) {
            View view = getView().findViewById(bindTo[i]);
            viewBinder.setViewValue(view, query, i);
        }

        query.close();

        if (BuildConfig.DEBUG) LOGGER.debug("bindData()");
    }
}
