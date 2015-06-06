package de.ironjan.mensaupb.feedback;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FromHtml;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.feedback.Mailer;

/**
 * Fragment to handle sending of feedback.
 */
@SuppressWarnings("WeakerAccess")
@EFragment(R.layout.fragment_feedback)
public class FeedbackFragment extends Fragment {

    @StringRes
    String feedbackTemplateBody, feedbackTemplateSubject;

    @Bean
    Mailer mMailer;

    @Click(R.id.btnFeedback)
    void sendFeedback() {
        String subject = String.format(feedbackTemplateSubject, BuildConfig.VERSION_NAME);
        String body = this.feedbackTemplateBody;

        mMailer.sendMail(subject,body);
    }
}
