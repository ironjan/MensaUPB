package de.ironjan.mensaupb.feedback;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringRes;

/**
 * Utility class to send Emails. If no email app is setup, the user is notified to do so.
 */
@EBean
public class Mailer {
    public static final String[] DEVELOPER_EMAILS = new String[]{"lippertsjan+mensaupb@gmail.com"};
    public static final String MAIL_MIME_TYPE = "text/plain";

    @RootContext
    Context mContext;

    @StringRes
    String feedbackNoEmailNote;

    /**
     * Sends an email with the given subject and body. Notifies the user that an email app should be
     * set up, if there is none available.
     *
     * @param subject
     * @param body
     */
    public void sendMail(String subject, String body) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setType(MAIL_MIME_TYPE);
        intent.putExtra(Intent.EXTRA_EMAIL, DEVELOPER_EMAILS);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        boolean activityExists = intent.resolveActivityInfo(mContext.getPackageManager(), 0) != null;

        if (activityExists) {
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, feedbackNoEmailNote, Toast.LENGTH_LONG).show();
        }
    }
}
