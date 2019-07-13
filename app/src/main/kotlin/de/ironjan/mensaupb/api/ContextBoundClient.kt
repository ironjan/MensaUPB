package de.ironjan.mensaupb.api

import android.content.Context
import android.util.Log
import arrow.core.Either
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.bitmap.LocallyCachedStatus.CACHED
import com.koushikdutta.ion.builder.Builders.Any.B
import de.ironjan.mensaupb.api.model.Menu
import de.ironjan.mensaupb.api.model.Menu.ArrayDeserializer
import de.ironjan.mensaupb.api.model.Menu.Deserializer
import org.slf4j.LoggerFactory
import java.io.PrintWriter
import java.io.StringWriter
import java.util.concurrent.TimeUnit.MILLISECONDS

class ContextBoundClient(val context: Context) : ClientV2 {

    private val menusUri = ClientV2Implementation.baseUrl + ClientV2Implementation.menusPath

    private val logger = LoggerFactory.getLogger("ContextBoundClient")

    override fun getMenus(): Either<String, Array<Menu>> = getMenus("", "", false)

    fun getMenus(noCache: Boolean): Either<String, Array<Menu>> = getMenus("", "", noCache)

    override fun getMenus(restaurant: String, date: String): Either<String, Array<Menu>> =
            getMenus(restaurant, date, false)

    private fun getMenus(restaurant: String, date: String, noCache: Boolean): Either<String, Array<Menu>> {
        val url = constructMenusUriWithParams(restaurant, date)

        val preparedRequest = prepareRequest(url, noCache)

        return try {
            Either.right(tryMenusRequestExecution(preparedRequest))
        } catch (e: Exception) {
            wrapException(e)
        }
    }

    private fun wrapException(e: Exception): Either<String, Nothing> {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        e.printStackTrace(pw)

        return Either.left(sw.toString())
    }

    private fun tryMenusRequestExecution(request: B): Array<Menu> {
        val response =
                request.asString()
                        .withResponse()
                        .get(ClientV2Implementation.REQUEST_TIMEOUT_30_SECONDS.toLong(), MILLISECONDS)


        return ArrayDeserializer().deserialize(response.result) ?: arrayOf()
    }

    private fun prepareRequest(url: String, forceReload: Boolean): B {
        val requestBuilder = Ion.with(context).load(url)
//                .setLogging("ContextBoundClient", Log.DEBUG)
        return if (forceReload) {
            requestBuilder.noCache()
        } else {
            requestBuilder
        }
    }

    override fun getMenu(key: String): Either<String, Menu> {
        val splitKey = key.split("_")
        val date = splitKey[0]
        val restaurant = splitKey[1]

        val allMenusRequest = prepareRequest(constructMenusUriWithParams("", ""), false)

        val cachedStatus = allMenusRequest.isLocallyCached

        return try {
            val resp = tryMenusRequestExecution(allMenusRequest)
            val right = resp.first { it.key == key }
            return Either.right(right)
        } catch (e: java.lang.Exception){
            wrapException(e)
        }

        if (cachedStatus == CACHED) {
            val resp = tryMenusRequestExecution(allMenusRequest)
            val right = resp.first { it.key == key }
            return Either.right(right)
        }


        val menusRequest = prepareRequest(constructMenusUriWithParams(restaurant, date), false)
        val locallyCached = menusRequest.isLocallyCached

        if (locallyCached == CACHED) {
            val resp = tryMenusRequestExecution(menusRequest)
            val right = resp.first { it.key == key }
            return Either.right(right)
        }
        val menuRequest = prepareRequest(constructMenuUriWithKey(key), false)
       return try {
            Either.right(tryMenuRequestExecution(menuRequest))
        } catch (e: Exception) {
            wrapException(e)
        }
    }

    private fun tryMenuRequestExecution(request: B): Menu {
        val response =
                request.asString()
                        .withResponse()
                        .get(ClientV2Implementation.REQUEST_TIMEOUT_30_SECONDS.toLong(), MILLISECONDS)


        return Deserializer().deserialize(response.result)
    }

    private fun constructMenusUriWithParams(restaurant: String = "", date: String = ""): String {
        val paramList = mutableListOf<Pair<String, String>>()
        if (restaurant.isNotBlank()) paramList.add(Pair("restaurant", restaurant))
        if (date.isNotBlank()) paramList.add(Pair("date", date))

        val paramsAsString =
                if (paramList.isEmpty()) ""
                else paramList.joinToString(prefix = "?", separator = "&", transform = { "${it.first}=${it.second}" })

        return "$menusUri$paramsAsString"
    }

    private fun constructMenuUriWithKey(key: String): String = "$menusUri/$key"

}