package de.ironjan.mensaupb.feature_toggle

import android.content.Context
import android.content.SharedPreferences

class FeatureTogglePrefs(context: Context) {
    private val prefsKeyFeatureToggles = "de.ironjan.mensaupb.feature_toggle.prefs"
    private val toggleClientV3 = "FeatureToggle.ClientV3"

    val prefs: SharedPreferences = context.getSharedPreferences(prefsKeyFeatureToggles, Context.MODE_PRIVATE)

    var clientV3Enabled: Boolean
        get() = prefs.getBoolean(toggleClientV3, false)
        set(value) = prefs.edit().putBoolean(toggleClientV3, value).apply()

}