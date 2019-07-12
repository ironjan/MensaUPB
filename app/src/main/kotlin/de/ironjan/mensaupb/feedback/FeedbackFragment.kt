package de.ironjan.mensaupb.feedback

import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import android.widget.Toast
import de.ironjan.mensaupb.BuildConfig
import de.ironjan.mensaupb.R
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.res.StringRes

/**
 * Fragment to handle sending of feedback.
 */
@EFragment(R.layout.fragment_feedback)
open class FeedbackFragment : Fragment() {

    @StringRes
    internal lateinit var feedbackTemplateBody: String
    @StringRes
    internal lateinit var feedbackTemplateSubject: String


    @StringRes
    internal lateinit var feedbackNoEmailNote: String


    @Click(R.id.btnFeedback)
    internal fun sendFeedback() {
        val subject = String.format(feedbackTemplateSubject, BuildConfig.VERSION_NAME)
        val body = feedbackTemplateBody

        sendMail(subject, body)
    }

    private val developerMails = arrayOf("lippertsjan+mensaupb@gmail.com")

    /**
     * Sends an email with the given subject and body. Notifies the user that an email app should be
     * set up, if there is none available.
     *
     * @param subject The mail's subject
     * @param body The mail's body
     */
    fun sendMail(subject: String, body: String) {
        if (context != null) {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, developerMails)
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, body)

            val activityExists = intent.resolveActivity(context!!.packageManager) != null
            if (activityExists) {
                context!!.startActivity(intent)
            } else {
                Toast.makeText(context, feedbackNoEmailNote, Toast.LENGTH_LONG).show()
            }
        }
    }

}
