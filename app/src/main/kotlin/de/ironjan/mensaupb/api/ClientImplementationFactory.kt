package de.ironjan.mensaupb.api

import android.content.Context
import de.ironjan.mensaupb.BuildConfig

object ClientImplementationFactory {
    fun getClient(context: Context): ClientV3 {
        return if (BuildConfig.DEBUG) {
            ContextBoundClient(context)
        } else {
            ClientV2Implementation
        }
    }
}