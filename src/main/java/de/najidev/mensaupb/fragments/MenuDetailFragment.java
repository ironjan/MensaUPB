package de.najidev.mensaupb.fragments;


import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.stw.Allergene;
import de.najidev.mensaupb.stw.Menu;
import de.najidev.mensaupb.sync.MenuContentProvider;

@EFragment(R.layout.fragment_menu_detail)
public class MenuDetailFragment extends DialogFragment {

    public static final String KEY_ID = "_id";

    @ViewById
    TextView textName, textCategory, textAllergenes;

    @AfterViews
    void bindData() {
        final long _id = getArguments().getLong(KEY_ID);
        Uri uri = Uri.withAppendedPath(MenuContentProvider.MENU_URI, "" + _id);
        String[] projection = {Menu.NAME_GERMAN, Menu.CATEGORY, Menu.ALLERGENES, Menu.ID};
        Cursor query = getActivity().getContentResolver().query(uri, projection, null, null, null);

        query.moveToNext();
        String name = query.getString(0);
        String category = query.getString(1);
        String allergenes = query.getString(2);
        query.close();

        textName.setText(name);
        textCategory.setText(category);
        textAllergenes.setText(Allergene.getExplanation(allergenes));
    }
}
