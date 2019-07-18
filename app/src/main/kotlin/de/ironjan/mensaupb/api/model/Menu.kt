package de.ironjan.mensaupb.api.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
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
                val key: String)
    :Parcelable {

    val isWeighted: Boolean
            get() = "weighted" == pricetype

    val price: Double
            get() = priceStudents

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.createStringArray(),
            parcel.readInt(),
            parcel.createStringArray(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    fun localizedName(isEnglish: Boolean) = if(isEnglish) name_en else name_de
    fun localizedDescription(isEnglish: Boolean) = if(isEnglish) description_en else description_de
    fun localizedCategory(isEnglish: Boolean) = if(isEnglish) category_en else category_de
    fun localizedSubCategory(isEnglish: Boolean) = if(isEnglish) subcategory_en else subcategory_de

    class Deserializer : ResponseDeserializable<Menu> {
        val customDateFormatGson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm")
                .create()

        override fun deserialize(reader: Reader): Menu {
            return customDateFormatGson
                    .fromJson(reader, Menu::class.java)!!
        }

        override fun deserialize(content: String): Menu {
            return customDateFormatGson.fromJson(content, Menu::class.java)!!
        }
    }

    class ArrayDeserializer : ResponseDeserializable<Array<Menu>> {
        val customDateFormatGson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm")
                .create()

        override fun deserialize(reader: Reader): Array<Menu>? {
            val type = object : TypeToken<Array<Menu>>() {}.type
            return customDateFormatGson.fromJson(reader, type)
        }
        override fun deserialize(content: String): Array<Menu>? {
            val type = object : TypeToken<Array<Menu>>() {}.type
            return customDateFormatGson.fromJson(content, type)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(name_de)
        parcel.writeString(name_en)
        parcel.writeString(description_de)
        parcel.writeString(description_en)
        parcel.writeString(category)
        parcel.writeString(category_de)
        parcel.writeString(category_en)
        parcel.writeString(subcategory_de)
        parcel.writeString(subcategory_en)
        parcel.writeDouble(priceStudents)
        parcel.writeDouble(priceWorkers)
        parcel.writeDouble(priceGuests)
        parcel.writeStringArray(allergens)
        parcel.writeInt(order_info)
        parcel.writeStringArray(badges)
        parcel.writeString(restaurant)
        parcel.writeString(pricetype)
        parcel.writeString(image)
        parcel.writeString(key)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<Menu> {
        override fun createFromParcel(parcel: Parcel): Menu {
            return Menu(parcel)
        }

        override fun newArray(size: Int): Array<Menu?> {
            return arrayOfNulls(size)
        }
    }
}