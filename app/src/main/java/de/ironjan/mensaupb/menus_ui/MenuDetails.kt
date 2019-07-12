package de.ironjan.mensaupb.menus_ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import de.ironjan.mensaupb.R
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.Extra
import org.androidannotations.annotations.OptionsItem

/**
 * Wrapping activity for [MenuDetailFragment].
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_menu_details)
open class MenuDetails : AppCompatActivity() {
    @Extra(value = MenuDetailFragment.ARG_KEY)
    internal lateinit var menuKey: String
    private var mFragment: MenuDetailFragment? = null

    @AfterViews
    internal fun bindFragment() {
        val ft = supportFragmentManager.beginTransaction()
        mFragment = MenuDetailFragment.newInstance(menuKey!!)
        ft.replace(R.id.fragmentMenuDetails, mFragment!!, "mFragment")
        ft.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    @OptionsItem(android.R.id.home)
    internal fun navUp() {
        val intent = NavUtils.getParentActivityIntent(this)
        NavUtils.navigateUpTo(this, intent!!)
    }
}
