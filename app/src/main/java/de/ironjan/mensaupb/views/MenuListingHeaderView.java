package de.ironjan.mensaupb.views;

import android.content.*;
import android.widget.*;

import org.androidannotations.annotations.*;

import de.ironjan.mensaupb.*;

/**
 * Custom View for headers in menu listing.
 */
@EViewGroup(R.layout.view_menu_list_header)
public class MenuListingHeaderView extends FrameLayout {
    @SuppressWarnings("WeakerAccess")
    @ViewById(android.R.id.content)
    TextView mContent;

    public MenuListingHeaderView(Context context) {
        super(context);
    }

    public void setText(CharSequence text) {
        mContent.setText(text);
    }
}
