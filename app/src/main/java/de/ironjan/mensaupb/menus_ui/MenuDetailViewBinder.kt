package de.ironjan.mensaupb.menus_ui

import android.view.View
import android.widget.TextView
import de.ironjan.mensaupb.R
import de.ironjan.mensaupb.api.model.Badge
import de.ironjan.mensaupb.api.model.LocalizedMenu
import java.util.ArrayList
import java.util.Locale

/**
 * Binds raw menus to de.ironjan.mensaupb.R.layout.view_menu_list_item
 */
object MenuDetailViewBinder {

    fun setViewValue(view: View?, menu: LocalizedMenu?): Boolean {
        if (view == null || menu == null) {
            return false
        }

        if (view !is TextView) {
            return false
        }


        val id = view.id
        val tv: TextView = view
        when (id) {
            R.id.textName -> {
                tv.text = menu.name
                return true
            }
            R.id.textCategory -> {
                tv.text = menu.getCategory()
                return true
            }
            R.id.textPrice -> {
                bindPrice(tv, menu)
                return true
            }
            R.id.textPricePer100g -> {
                bindPricePer100g(tv, menu)
                return true
            }
            R.id.textBadges -> {
                bindBadges(tv, menu)
                return true
            }
            else -> {
                tv.text = menu.toString()
                return true
            }
        }
    }

    private fun bindPrice(tv: TextView, menu: LocalizedMenu) {
        tv.text = String.format(Locale.GERMAN, "%.2f €", menu.price)
    }


    private fun bindPricePer100g(view: TextView, menu: LocalizedMenu) {
        view.text =
                if (menu.isWeighted) {
                    "/100g"
                } else {
                    ""
                }
    }

    private fun bindBadges(textView: TextView, menu: LocalizedMenu) {
        val badgesArray = menu.badges
        val badges = ArrayList<Badge>(badgesArray.size)
        for (s in badgesArray) {
            badges.add(Badge.fromString(s))
        }

        val context = textView.context
        val resources = context.resources

        if (badges.isEmpty()) {
            textView.text = ""
            return
        }

        val stringBuilder = StringBuilder(context.getString(badges[0].stringId))
        for (i in 1 until badges.size) {
            val badgeString = resources.getString(badges[i].stringId)
            stringBuilder.append(", ")
                    .append(badgeString)
        }
        textView.text = stringBuilder.toString()
    }

}
