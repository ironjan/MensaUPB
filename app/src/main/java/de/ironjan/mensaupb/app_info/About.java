package de.ironjan.mensaupb.app_info;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import de.ironjan.mensaupb.R;

/**
 * Wrapping activity for {@link AboutFragment} and
 * {@link de.ironjan.mensaupb.monitoring.MonitoringSettingsFragment}.
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_about)
public class About extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OptionsItem(android.R.id.home)
    void navUp() {
        NavUtils.navigateUpFromSameTask(this);
    }
}