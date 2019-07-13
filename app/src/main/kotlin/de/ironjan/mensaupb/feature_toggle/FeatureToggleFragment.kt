package de.ironjan.mensaupb.feature_toggle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.CheckBox
import de.ironjan.mensaupb.R
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.ViewById

@EFragment(R.layout.fragment_feature_toggles)
open class FeatureToggleFragment : Fragment() {

    @ViewById(R.id.toggleClientV3)
    internal lateinit var toggleClientV3: CheckBox

    lateinit var featureTogglePrefs: FeatureTogglePrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nonNullContext = context ?: return
        featureTogglePrefs = FeatureTogglePrefs(nonNullContext)
    }


    @AfterViews
    fun loadSettings() {
        toggleClientV3.isChecked = featureTogglePrefs.clientV3Enabled
    }

    override fun onPause() {
        featureTogglePrefs.clientV3Enabled = toggleClientV3.isChecked
        super.onPause()
    }
}