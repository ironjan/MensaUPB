package de.ironjan.mensaupb.feature_toggle

import android.os.Bundle
import android.support.v4.app.Fragment
import de.ironjan.mensaupb.R
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(R.layout.fragment_feature_toggles)
open class FeatureToggleFragment : Fragment() {


    lateinit var featureTogglePrefs: FeatureTogglePrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nonNullContext = context ?: return
        featureTogglePrefs = FeatureTogglePrefs(nonNullContext)
    }


    @AfterViews
    fun loadSettings() {
    }

    override fun onPause() {
        super.onPause()
    }
}