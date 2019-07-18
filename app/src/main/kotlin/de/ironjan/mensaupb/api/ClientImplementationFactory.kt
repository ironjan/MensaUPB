package de.ironjan.mensaupb.api

import android.content.Context

object ClientImplementationFactory {
    fun getClient(context: Context): ClientV3Implementation = ClientV3Implementation(context)
}