package de.ironjan.mensaupb.api.model

import android.util.Log
import de.ironjan.mensaupb.model.LocalizedMenu
import de.ironjan.mensaupb.stw.rest_api.PriceType
import de.ironjan.mensaupb.stw.rest_api.StwMenu
import java.util.Collections
import java.util.Comparator

object MenuSorter {
    private val SORT_ORDER = String.format("%s ASC, %s ASC, %s ASC",
            StwMenu.SORT_ORDER, StwMenu.PRICE_TYPE, StwMenu.STUDENTS_PRICE)

    fun sort(menus: List<LocalizedMenu>): List<LocalizedMenu> {
        Log.e("MenuSorter", menus.toString())
        Collections.sort(menus, PriceTypeComparator())
        Collections.sort(menus, PriceComparator())
        Collections.sort(menus, CategorySorter())
        Log.e("MenuSorter", menus.toString())
        return menus
    }

    private class PriceTypeComparator : java.util.Comparator<LocalizedMenu> {
        override fun compare(o1: LocalizedMenu?, o2: LocalizedMenu?): Int {
            if (o1 == null || o2 == null) {
                return 0
            }

            val pt1 = PriceType.fromString(o1.pricetype)
            val pt2 = PriceType.fromString(o2.pricetype)

            return if (pt1!!.id < pt2.id) {
                -1
            } else 1
        }
    }

    private class PriceComparator : java.util.Comparator<LocalizedMenu> {
        override fun compare(o1: LocalizedMenu?, o2: LocalizedMenu?): Int {
            if (o1 == null || o2 == null) {
                return 0
            }

            if (o1.price < o2.price) {
                return -1
            }
            return if (o1.price > o2.price) {
                1
            } else 0
        }
    }

    private class CategorySorter : Comparator<LocalizedMenu> {
        override fun compare(o1: LocalizedMenu?, o2: LocalizedMenu?): Int {
            if (o1 == null || o2 == null) {
                return 0
            }

            if (o1.sortOrder < o2.sortOrder) {
                return -1
            }
            return if (o1.sortOrder > o2.sortOrder) {
                1
            } else 0
        }
    }
}
