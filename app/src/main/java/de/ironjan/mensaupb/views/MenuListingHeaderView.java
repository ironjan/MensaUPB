package de.ironjan.mensaupb.views;

import android.content.*;
import android.widget.*;

import org.androidannotations.annotations.*;

import de.ironjan.mensaupb.*;

/**
 * Created by ljan on 24.11.14.
 */
@EViewGroup(R.layout.view_menu_list_header)
public class MenuListingHeaderView extends FrameLayout {
    @ViewById(android.R.id.content)
    TextView mContent;

    public MenuListingHeaderView(Context context) {
        super(context);
    }

    public void setText(String text) {
        mContent.setText(text);
    }
}
