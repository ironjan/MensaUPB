package de.ironjan.mensaupb.api

import arrow.core.Either
import de.ironjan.mensaupb.api.model.Menu

interface ClientV3 : ClientV2{
    fun getMenus(noCache: Boolean): Either<String, Array<Menu>>
    fun getMenus(restaurant: String, date: String, noCache: Boolean): Either<String, Array<Menu>>

}