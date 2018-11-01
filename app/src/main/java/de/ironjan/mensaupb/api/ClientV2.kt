package de.ironjan.mensaupb.api

import arrow.core.Either
import de.ironjan.mensaupb.api.model.Menu

interface ClientV2 {
    fun getMenus(): Either<String, Array<Menu>>
    fun getMenus(restaurant: String, date: String): Either<String, Array<Menu>>

    companion object {

        /**
         * Gets a singleton instance of the client for usage.
         */
        fun getClient(): ClientV2 {
            return ClientV2Implementation
        }
    }
}