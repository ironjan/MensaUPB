package de.ironjan.mensaupb.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FromHtml;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;

/**
 * Fragment with some information about the app.
 */
@SuppressWarnings("WeakerAccess")
@EFragment(R.layout.fragment_about)
public class AboutFragment extends Fragment {

    public static final String[] DEVELOPER_EMAILS = new String[]{"lippertsjan+mensaupb@gmail.com"};
    public static final String MAIL_MIME_TYPE = "text/plain";
    private final Logger LOGGER = LoggerFactory.getLogger(getClass().getSimpleName());
    @ViewById(R.id.txtDependencies)
    @FromHtml(R.string.dependencies)
    TextView mTxtDependencies;
    @ViewById(R.id.txtAbout)
    @FromHtml(R.string.aboutText)
    TextView mTxtAbout;

    @ViewById(R.id.txtDependencyNames)
    @FromHtml(R.string.dependencyNames)
    TextView mTxtDependencyNames;

    @ViewById(R.id.textSource)
    @FromHtml(R.string.source)
    TextView mTextSourceLink;


    @StringRes
    String feedbackTemplateBody, feedbackTemplateSubject, feedbackNoEmailNote;

    @AfterViews
    void linkify() {
        final MovementMethod movementMethod = LinkMovementMethod.getInstance();
        mTxtDependencies.setMovementMethod(movementMethod);
        mTxtAbout.setMovementMethod(movementMethod);
        mTxtDependencyNames.setMovementMethod(movementMethod);
        mTextSourceLink.setMovementMethod(movementMethod);
        if (BuildConfig.DEBUG) LOGGER.debug("linkify() done");
    }

    @AfterViews
    void bindVersion() {
        ActionBarActivity activity = (ActionBarActivity) getActivity();
        if (activity == null) return;
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar == null) return;
        String app_name = getResources().getString(R.string.app_name);
        String title = app_name + " " + BuildConfig.VERSION_NAME;
        actionBar.setTitle(title);
    }

    @Click(R.id.btnFeedback)
    void sendFeedback() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setType(MAIL_MIME_TYPE);
        intent.putExtra(Intent.EXTRA_EMAIL, DEVELOPER_EMAILS);
        String subject = String.format(feedbackTemplateSubject, BuildConfig.VERSION_NAME);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, feedbackTemplateBody);
        boolean activityExists = intent.resolveActivityInfo(getActivity().getPackageManager(), 0) != null;

        if (activityExists) {
            getActivity().startActivity(intent);
        } else {
            Toast.makeText(getActivity(), feedbackNoEmailNote, Toast.LENGTH_LONG).show();
        }

    }
}
