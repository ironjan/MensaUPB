package de.ironjan.mensaupb.menus_ui;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import de.ironjan.mensaupb.R;

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
