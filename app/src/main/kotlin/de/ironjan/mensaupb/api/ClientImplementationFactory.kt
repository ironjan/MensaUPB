package de.ironjan.mensaupb.api

import android.content.Context
import de.ironjan.mensaupb.feature_toggle.FeatureTogglePrefs

object ClientImplementationFactory {
    fun getClient(context: Context): ClientV3 {
        val featureTogglePrefs = FeatureTogglePrefs(context)

        return if (featureTogglePrefs.clientV3Enabled) {
            ClientV3Implementation(context)
        } else {
            ClientV2Implementation
        }
    }
}