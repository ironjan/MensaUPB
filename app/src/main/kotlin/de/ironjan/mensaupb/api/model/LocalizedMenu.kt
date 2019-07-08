package de.ironjan.mensaupb.api.model

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocalizedMenu(menu: Menu, isEnglish: Boolean) {
    private val date: String = menu.date
    var name: String? = null
        private set

    var description: String? = null
        private set

    private var category: String? = null

    private val categoryIdentifier: String

    var subcategory: String? = null
        private set

    val restaurant: String

    val pricetype: String

    val image: String

    val key: String

    val priceStudents: Double?

    val priceWorkers: Double?

    val priceGuests: Double?

    val allergens: Array<String>

    val badges: Array<String>

    val order_info: Int?

    val price: Double?
        get() = priceStudents

    val isWeighted: Boolean
        get() = "weighted" == pricetype

    init {

        if (isEnglish) {
            name = menu.name_en
            description = menu.description_en
            category = menu.category_en
            subcategory = menu.subcategory_en
        } else {
            name = menu.name_de
            description = menu.description_de
            category = menu.category_de
            subcategory = menu.subcategory_de
        }
        categoryIdentifier = menu.category
        restaurant = menu.restaurant
        pricetype = menu.pricetype
        image = menu.image
        key = menu.key
        priceStudents = menu.priceStudents
        priceWorkers = menu.priceWorkers
        priceGuests = menu.priceGuests
        allergens = menu.allergens
        badges = menu.badges
        order_info = menu.order_info
        // FIXME nutritionalInfo = menu.getNutritionalInfo();
    }

    fun getDate(): Date? {
        try {
            return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)
        } catch (e: ParseException) {

        }

        return null
    }

    fun getCategory(): String {
        return if (category.isNullOrBlank())
            "No category"
        else
            category!!
    }

    override fun toString(): String {
        return "LocalizedMenu{" +
                "date='" + date + '\''.toString() +
                ", name='" + name + '\''.toString() +
                ", categoryIdentifier='" + categoryIdentifier + '\''.toString() +
                ", restaurant='" + restaurant + '\''.toString() +
                ", pricetype='" + pricetype + '\''.toString() +
                ", priceStudents=" + priceStudents +
                '}'.toString()
    }
}
