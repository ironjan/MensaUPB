package de.ironjan.mensaupb.fragments;

import android.support.v4.app.*;
import android.text.method.*;
import android.widget.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import de.ironjan.mensaupb.*;

/**
 * Actual content of {@link de.ironjan.mensaupb.activities.About}.
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
    @ViewById(R.id.txtAppVersion)
    TextView txtAppVersion;

    @ViewById(R.id.textSourceLink)
    @FromHtml(R.string.srcLink)
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
        txtAppVersion.setText(BuildConfig.VERSION_NAME);
    }
}
