package de.ironjan.mensaupb.app_info

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import de.ironjan.mensaupb.R
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.OptionsItem

/**
 * Wrapping activity for [AboutFragment].
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_about)
open class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @OptionsItem(android.R.id.home)
    internal fun navUp() {
        NavUtils.navigateUpFromSameTask(this)
    }
}
