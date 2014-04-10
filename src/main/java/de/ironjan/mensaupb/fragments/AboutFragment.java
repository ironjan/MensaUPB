package de.ironjan.mensaupb.fragments;

import android.support.v4.app.*;
import android.widget.*;

import org.androidannotations.annotations.*;

import de.ironjan.mensaupb.*;
@EFragment(R.layout.fragment_about)
public class AboutFragment extends Fragment {

@ViewById(R.id.txtDependencies)
    @FromHtml(R.string.dependencies)
    TextView txtDependencies;
}
