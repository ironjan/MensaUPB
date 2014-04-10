package de.ironjan.mensaupb.activities;

import android.os.*;
import android.support.v4.app.*;
import android.support.v7.app.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.*;

import de.ironjan.mensaupb.*;

/**
 * Wrapping activity for {@link de.ironjan.mensaupb.fragments.AboutFragment}.
 */
@EActivity(R.layout.activity_about)
public class About extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OptionsItem(android.R.id.home)
    void navUp(){
        NavUtils.navigateUpFromSameTask(this);
    }
}
