package de.ironjan.mensaupb.fragments;

import android.support.v4.app.*;
import android.text.method.*;
import android.text.util.*;
import android.widget.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import de.ironjan.mensaupb.*;

/**
 * Actual content of {@link de.ironjan.mensaupb.activities.About}.
 */
@EFragment(R.layout.fragment_about)
public class AboutFragment extends Fragment {

    @ViewById(R.id.txtDependencies)
    @FromHtml(R.string.dependencies)
    TextView txtDependencies;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass().getSimpleName());

    @AfterViews
    void linkify() {
        txtDependencies.setMovementMethod   (LinkMovementMethod.getInstance());
        if (BuildConfig.DEBUG) LOGGER.debug("linkify() done");
    }
}
