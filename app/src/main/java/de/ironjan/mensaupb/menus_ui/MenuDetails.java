package de.ironjan.mensaupb.menus_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;

import de.ironjan.mensaupb.R;

/**
 * Wrapping activity for {@link MenuDetailFragment}.
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
