package de.ironjan.mensaupb.fragments;


import android.app.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.support.v4.app.DialogFragment;
import android.widget.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.stw.*;
import de.ironjan.mensaupb.sync.*;

@EFragment(R.layout.fragment_menu_detail)
public class MenuDetailFragment extends DialogFragment {

    public static final String ARG_ID = "ARG_ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuDetailFragment.class.getSimpleName());
    @ViewById
    TextView textName, textCategory, textPrice;

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
        String[] projection = {Menu.NAME_GERMAN, Menu.CATEGORY, Menu.ALLERGENES, Menu.ID};
        Cursor query = getActivity().getContentResolver().query(uri, projection, null, null, null);

        query.moveToFirst();
        String name = query.getString(0);
        String category = query.getString(1);
        String allergenes = query.getString(2);
        query.close();

        textName.setText(name);
        textCategory.setText(category);
        textPrice.setText(Allergene.getExplanation(allergenes));

        if (BuildConfig.DEBUG) LOGGER.debug("bindData()");
    }
}