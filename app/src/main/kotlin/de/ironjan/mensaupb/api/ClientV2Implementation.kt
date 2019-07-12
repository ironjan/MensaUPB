package de.ironjan.mensaupb.api

import arrow.core.Either
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import de.ironjan.mensaupb.api.model.Menu
import org.slf4j.LoggerFactory

private val REQUEST_TIMEOUT_30_SECONDS = 30000

object ClientV2Implementation : ClientV2 {
    val baseUrl = "https://mensaupb.herokuapp.com/api/"
    var LOGGER = LoggerFactory.getLogger(ClientV2Implementation::class.java)

    override fun getMenus(): Either<String, Array<Menu>> {
        return getMenus("", "")
    }
    override fun getMenus(restaurant: String, date: String): Either<String, Array<Menu>> {
        FuelManager.instance.basePath = baseUrl
        val paramList = mutableListOf<Pair<String, String>>()
        if (restaurant.isNotBlank()) paramList.add(Pair("restaurant", restaurant))
        if (date.isNotBlank()) paramList.add(Pair("date", date))

        val httpGet = "/menus".httpGet(parameters = paramList)
        val cacheableKey = httpGet.url.toString()


        val (_, _, result) =
                httpGet.timeout(REQUEST_TIMEOUT_30_SECONDS)
                       .responseObject(Menu.ArrayDeserializer())
        val (data, error) = result

        return if (error == null) {
            Either.right(data!!)
        } else {
            LOGGER.error(error.localizedMessage)
            Either.left(error.localizedMessage)
        }
    }

    override fun getMenu(key: String): Either<String, Menu> {
        FuelManager.instance.basePath = baseUrl

        val (_, _, result) =
                "/menus/$key".httpGet()
                        .timeout(REQUEST_TIMEOUT_30_SECONDS)
                        .responseObject(Menu.Deserializer())
        val (data, error) = result

        return if (error == null) {
            Either.right(data!!)
        } else {
            Either.left(error.localizedMessage)
        }
    }
}