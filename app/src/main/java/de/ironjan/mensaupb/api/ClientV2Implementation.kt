package de.ironjan.mensaupb.api

import arrow.core.Either
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import de.ironjan.mensaupb.api.model.Menu

object ClientV2Implementation : ClientV2 {

    val baseUrl = "http://mensaupb.herokuapp.com/api/"
    override fun getMenus(): Either<String, Array<Menu>> {
        return getMenus("", "")
    }

    override fun getMenus(restaurant: String, date: String): Either<String, Array<Menu>> {
        FuelManager.instance.basePath = baseUrl
        val paramList = mutableListOf<Pair<String, String>>()
        if (restaurant.isNotBlank()) paramList.add(Pair("restaurant", restaurant))
        if (date.isNotBlank()) paramList.add(Pair("date", date))

        val (_, _, result) =
                "/menus".httpGet(parameters = paramList)
                        .responseObject(Menu.ArrayDeserializer())
        val (data, error) = result

        return if (error == null) {
            Either.right(data!!)
        } else {
            Either.left(error.localizedMessage)
        }
    }
}