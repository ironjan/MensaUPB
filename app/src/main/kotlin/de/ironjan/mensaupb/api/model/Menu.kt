package de.ironjan.mensaupb.api.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.Reader

data class Menu(val date: String,
                val name_de: String,
                val name_en: String,
                val description_de: String,
                val description_en: String,
                val category: String,
                val category_de: String,
                val category_en: String,
                val subcategory_de: String,
                val subcategory_en: String,
                val priceStudents: Double,
                val priceWorkers: Double,
                val priceGuests: Double,
                val allergens: Array<String>,
                val order_info: Int,
                val badges: Array<String>,
                val restaurant: String,
                val pricetype: String,
                val image: String,
                val key: String) {

    val isWeighted: Boolean
            get() = "weighted" == pricetype

    val price: Double
            get() = priceStudents

    fun localizedName(isEnglish: Boolean) = if(isEnglish) name_en else name_de
    fun localizedDescription(isEnglish: Boolean) = if(isEnglish) description_en else description_de
    fun localizedCategory(isEnglish: Boolean) = if(isEnglish) category_en else category_de
    fun localizedSubCategory(isEnglish: Boolean) = if(isEnglish) subcategory_en else subcategory_de

    class Deserializer : ResponseDeserializable<Menu> {
        override fun deserialize(reader: Reader): Menu {
            return customDateFormatGson
                    .fromJson(reader, Menu::class.java)!!
        }

        override fun deserialize(content: String): Menu {
            return customDateFormatGson.fromJson(content, Menu::class.java)!!
        }
    }

    class ArrayDeserializer : ResponseDeserializable<Array<Menu>> {
        override fun deserialize(reader: Reader): Array<Menu>? {
            val type = object : TypeToken<Array<Menu>>() {}.type
            return customDateFormatGson.fromJson(reader, type)
        }
        override fun deserialize(content: String): Array<Menu>? {
            val type = object : TypeToken<Array<Menu>>() {}.type
            return customDateFormatGson.fromJson(content, type)
        }
    }


    companion object {
        val customDateFormatGson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm")
                .create()
    }
}