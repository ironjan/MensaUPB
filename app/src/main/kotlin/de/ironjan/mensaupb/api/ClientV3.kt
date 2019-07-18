package de.ironjan.mensaupb.api

import android.content.Context
import arrow.core.Either
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.builder.Builders.Any.B
import de.ironjan.mensaupb.api.model.Menu
import de.ironjan.mensaupb.api.model.Menu.ArrayDeserializer
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.util.concurrent.TimeUnit.MILLISECONDS

class ClientV3(val context: Context)  {

    private val menusUri = baseUrl + menusPath



    fun getMenus(noCache: Boolean): Either<String, Array<Menu>>
            = getMenus("", "", noCache)


    fun getMenus(restaurant: String, date: String, noCache: Boolean): Either<String, Array<Menu>> {
        val url = constructMenusUriWithParams(restaurant, date)

        val preparedRequest = prepareRequest(url, noCache)

        return try {
            Either.right(tryMenusRequestExecution(preparedRequest))
        } catch (e: Exception) {
            wrapException(e)
        }
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

    private fun prepareRequest(url: String, forceReload: Boolean): B {
        val requestBuilder = Ion.with(context).load(url)
//                .setLogging("ClientV3", Log.DEBUG)
        return if (forceReload) {
            requestBuilder.noCache()
        } else {
            requestBuilder
        }
    }

    private fun tryMenusRequestExecution(request: B): Array<Menu> {
        val response =
                request.asString(Charsets.UTF_8)
                        .withResponse()
                        .get(REQUEST_TIMEOUT_30_SECONDS.toLong(), MILLISECONDS)


        return ArrayDeserializer().deserialize(response.result) ?: arrayOf()
    }

    private fun wrapException(e: Exception): Either<String, Nothing> {
        val sw: Writer = StringWriter()
        e.printStackTrace(PrintWriter(sw))
        return Either.left(sw.toString())
    }


    fun getMenu(restaurant: String, date: String, nameEn:String): Either<String, Menu> {
        val allMenusRequest = prepareRequest(constructMenusUriWithParams(restaurant, date), false)

        return try {
            val resp = tryMenusRequestExecution(allMenusRequest)
            val right = resp.first { it.name_en == nameEn }
            return Either.right(right)
        } catch (e: java.lang.Exception){
            wrapException(e)
        }
    }

    companion object {
        const val REQUEST_TIMEOUT_30_SECONDS = 30000
        const val baseUrl = "https://mensaupb.herokuapp.com/api"
        const val menusPath = "/menus"
    }

}