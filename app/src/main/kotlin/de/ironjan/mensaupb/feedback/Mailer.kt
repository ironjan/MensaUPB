package de.ironjan.mensaupb.feedback

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import org.androidannotations.annotations.res.StringRes

/**
 * Utility class to send Emails. If no email app is setup, the user is notified to do so.
 */
@EBean
open class Mailer {

    @RootContext
    internal var mContext: Context? = null

    @StringRes
    internal var feedbackNoEmailNote: String? = null

    /**
     * Sends an email with the given subject and body. Notifies the user that an email app should be
     * set up, if there is none available.
     *
     * @param subject The mail's subject
     * @param body The mail's body
     */
    fun sendMail(subject: String, body: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, DEVELOPER_EMAILS)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        val activityExists = intent.resolveActivity(mContext!!.packageManager) != null
        if (activityExists) {
            mContext!!.startActivity(intent)
        } else {
            Toast.makeText(mContext, feedbackNoEmailNote, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        val DEVELOPER_EMAILS = arrayOf("lippertsjan+mensaupb@gmail.com")
        val MAIL_MIME_TYPE = "text/plain"
    }
}
