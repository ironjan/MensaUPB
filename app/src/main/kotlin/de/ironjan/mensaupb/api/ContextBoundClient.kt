package de.ironjan.mensaupb.api

import android.content.Context
import android.util.Log
import arrow.core.Either
import com.koushikdutta.ion.Ion
import de.ironjan.mensaupb.api.model.Menu
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit.MILLISECONDS

class ContextBoundClient(val context: Context) : ClientV2 {

    private val menusUri = ClientV2Implementation.baseUrl + ClientV2Implementation.menusPath

    private val logger = LoggerFactory.getLogger("ContextBoundClient")

    override fun getMenus(): Either<String, Array<Menu>> {

// This works
        val load = Ion.with(context)
                .load(menusUri)


        val start = System.currentTimeMillis()

        val response =
                load.setLogging("MyLogs", Log.VERBOSE)
                        .asString()
                        .withResponse()
                        .get(ClientV2Implementation.REQUEST_TIMEOUT_30_SECONDS.toLong(), MILLISECONDS)

        val duration = System.currentTimeMillis() - start

        val headers = response.headers.headers.multiMap.joinToString(separator = ";", transform = { "${it.name} = ${it.value}" })
        logger.warn("${menusUri} was LocallyCached: ${load.isLocallyCached}. Duration for request: ${duration}ms. Headers: $headers")

        val menus = Menu.ArrayDeserializer().deserialize(response.result)

        return if (menus != null) {
            Either.right(menus)
        } else {
            Either.left("something failed...")
        }
    }

    override fun getMenus(restaurant: String, date: String): Either<String, Array<Menu>> {
        val paramList = mutableListOf<Pair<String, String>>()
        if (restaurant.isNotBlank()) paramList.add(Pair("restaurant", restaurant))
        if (date.isNotBlank()) paramList.add(Pair("date", date))

        val paramsAsString = paramList.joinToString(prefix = "?", separator = "&", transform = { "${it.first}=${it.second}" })

        val url = menusUri + paramsAsString

        val inputStream = Ion.with(context)
                .load(url)
                .asInputStream()
                .get(ClientV2Implementation.REQUEST_TIMEOUT_30_SECONDS.toLong(), MILLISECONDS)

        val menus = Menu.ArrayDeserializer().deserialize(inputStream)

        return if (menus != null) {
            Either.right(menus)
        } else {
            Either.left("something failed...")
        }
    }

    override fun getMenu(key: String): Either<String, Menu> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}