package de.ironjan.mensaupb.api

import arrow.core.Either
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import de.ironjan.mensaupb.api.model.Menu
import org.slf4j.LoggerFactory


object ClientV2Implementation : ClientV3 {
    override fun getMenus(restaurant: String, date: String, noCache: Boolean): Either<String, Array<Menu>> =
            getMenus(restaurant, date)

    override fun getMenus(noCache: Boolean): Either<String, Array<Menu>> = getMenus()

    private val LOGGER = LoggerFactory.getLogger(ClientV2Implementation::class.java)!!

    override fun getMenus(): Either<String, Array<Menu>> {
        return getMenus("", "")
    }

    override fun getMenus(restaurant: String, date: String): Either<String, Array<Menu>> {
        FuelManager.instance.basePath = ClientV3.baseUrl
        val paramList = mutableListOf<Pair<String, String>>()
        if (restaurant.isNotBlank()) paramList.add(Pair("restaurant", restaurant))
        if (date.isNotBlank()) paramList.add(Pair("date", date))

        val httpGet = ClientV3.menusPath.httpGet(parameters = paramList)


        val (_, _, result) =
                httpGet.timeout(ClientV3.REQUEST_TIMEOUT_30_SECONDS)
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
        FuelManager.instance.basePath = ClientV3.baseUrl

        val (_, _, result) =
                "/menus/$key".httpGet()
                        .timeout(ClientV3.REQUEST_TIMEOUT_30_SECONDS)
                        .responseObject(Menu.Deserializer())
        val (data, error) = result

        return if (error == null) {
            Either.right(data!!)
        } else {
            Either.left(error.localizedMessage)
        }
    }
}