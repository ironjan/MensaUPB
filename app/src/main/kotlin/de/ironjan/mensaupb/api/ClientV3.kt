package de.ironjan.mensaupb.api

import arrow.core.Either
import de.ironjan.mensaupb.api.model.Menu

interface ClientV3 {
    fun getMenus(): Either<String, Array<Menu>>
    fun getMenus(restaurant: String, date: String): Either<String, Array<Menu>>
    fun getMenu(key: String): Either<String, Menu>
    fun getMenus(noCache: Boolean): Either<String, Array<Menu>>
    fun getMenus(restaurant: String, date: String, noCache: Boolean): Either<String, Array<Menu>>

    companion object {
        const val REQUEST_TIMEOUT_30_SECONDS = 30000
        const val baseUrl = "https://mensaupb.herokuapp.com/api"
        const val menusPath = "/menus"
    }
}