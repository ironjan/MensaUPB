package de.ironjan.mensaupb.feedback

import android.support.v4.app.Fragment

import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.res.StringRes

import de.ironjan.mensaupb.BuildConfig
import de.ironjan.mensaupb.R

/**
 * Fragment to handle sending of feedback.
 */
@EFragment(R.layout.fragment_feedback)
open class FeedbackFragment : Fragment() {

    @StringRes
    internal var feedbackTemplateBody: String? = null
    @StringRes
    internal var feedbackTemplateSubject: String? = null

    @Bean
    internal var mMailer: Mailer? = null

    @Click(R.id.btnFeedback)
    internal fun sendFeedback() {
        val subject = String.format(feedbackTemplateSubject!!, BuildConfig.VERSION_NAME)
        val body = this.feedbackTemplateBody

        mMailer!!.sendMail(subject, body!!)
    }
}
