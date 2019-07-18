package de.ironjan.mensaupb.app_info

import android.support.v4.app.Fragment
import android.support.v4.text.HtmlCompat
import android.support.v4.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.widget.TextView
import de.ironjan.mensaupb.BuildConfig
import de.ironjan.mensaupb.R
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.ViewById
import org.slf4j.LoggerFactory

/**
 * Fragment with some information about the app.
 */
@EFragment(R.layout.fragment_about)
open class AboutFragment : Fragment() {

    private val LOGGER = LoggerFactory.getLogger(javaClass.simpleName)

    @ViewById(R.id.txtDependencies)
    internal lateinit var mTxtDependencies: TextView

    @ViewById(R.id.txtAbout)
    internal lateinit var mTxtAbout: TextView

    @ViewById(R.id.txtDependencyNames)
    internal lateinit var mTxtDependencyNames: TextView

    @ViewById(R.id.textSource)
    internal lateinit var mTextSourceLink: TextView

    @AfterViews
    internal fun linkify() {
        val nonNullActivity = activity ?: return

        val movementMethod = LinkMovementMethod.getInstance()
        mTxtAbout.text = HtmlCompat.fromHtml(nonNullActivity.getString(R.string.aboutText), FROM_HTML_MODE_COMPACT)
        mTxtDependencyNames.text = HtmlCompat.fromHtml(nonNullActivity.getString(R.string.dependencyNames), FROM_HTML_MODE_COMPACT)
        mTxtDependencies.text = HtmlCompat.fromHtml(nonNullActivity.getString(R.string.dependencies), FROM_HTML_MODE_COMPACT)
        mTextSourceLink.text = HtmlCompat.fromHtml(nonNullActivity.getString(R.string.source), FROM_HTML_MODE_COMPACT)

        mTxtAbout.movementMethod = movementMethod
        mTxtDependencyNames.movementMethod = movementMethod
        mTxtDependencies.movementMethod = movementMethod
        mTextSourceLink.movementMethod = movementMethod

        if (BuildConfig.DEBUG) LOGGER.debug("linkify() done")
    }

    @AfterViews
    internal fun bindVersion() {
        val activity = activity as AppCompatActivity? ?: return
        val actionBar = activity.supportActionBar ?: return
        val app_name = resources.getString(R.string.app_name)
        val title = app_name + " " + BuildConfig.VERSION_NAME
        actionBar.title = title
    }

}
