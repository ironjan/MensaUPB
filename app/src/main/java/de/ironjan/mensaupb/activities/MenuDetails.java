package de.ironjan.mensaupb.activities;

import android.annotation.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v7.app.*;

import org.androidannotations.annotations.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.fragments.*;

/**
 * Wrapping activity for {@link de.ironjan.mensaupb.fragments.MenuDetailFragment}.
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_menu_details)
public class MenuDetails extends ActionBarActivity {
    @SuppressWarnings("WeakerAccess")
    @Extra(value = MenuDetailFragment.ARG_ID)
    long menuId;
    private MenuDetailFragment mFragment;

    @AfterViews
    void bindFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mFragment = MenuDetailFragment.newInstance(menuId);
        ft.replace(R.id.fragmentMenuDetails, mFragment, "mFragment");
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OptionsItem(android.R.id.home)
    void navUp() {
        Intent intent = NavUtils.getParentActivityIntent(this);
        NavUtils.navigateUpTo(this, intent);
    }
}
