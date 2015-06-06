package de.ironjan.mensaupb.feedback;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.res.StringRes;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;

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
