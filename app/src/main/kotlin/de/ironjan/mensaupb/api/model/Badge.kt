package de.ironjan.mensaupb.api.model

import de.ironjan.mensaupb.R

/**
 * Enum for the badges used
 */
enum class Badge(val stringId: Int) {
    LOW_CALORIE(R.string.lowCalorie),
    FAT_FREE(R.string.fatFree),
    VEGETARIAN(R.string.vegetarian),
    VEGAN(R.string.vegan),
    NO_LACTOSE(R.string.noLactose),
    NO_GLUTEN(R.string.noGluten),
    EMPTY(R.string.empty);

    private object Constants {
        val LOW_CALORIE = "low-calorie"
        val FAT_FREE = "nonfat"
        val VEGETARIAN = "vegetarian"
        val VEGAN = "vegan"
        val NO_LACTOSE = "lactose-free"
        val NO_GLUTEN = "gluten-free"
    }

    companion object {


        fun fromString(s: String): Badge = when (s) {
            Constants.LOW_CALORIE -> LOW_CALORIE
            Constants.FAT_FREE -> FAT_FREE
            Constants.VEGETARIAN -> VEGETARIAN
            Constants.VEGAN -> VEGAN
            Constants.NO_LACTOSE -> NO_LACTOSE
            Constants.NO_GLUTEN -> NO_GLUTEN
            else -> EMPTY
        }
    }
}
