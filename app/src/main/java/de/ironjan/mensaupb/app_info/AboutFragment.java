package de.ironjan.mensaupb.app_info;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FromHtml;
import org.androidannotations.annotations.ViewById;
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
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity == null) return;
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar == null) return;
        String app_name = getResources().getString(R.string.app_name);
        String title = app_name + " " + BuildConfig.VERSION_NAME;
        actionBar.setTitle(title);
    }

}
