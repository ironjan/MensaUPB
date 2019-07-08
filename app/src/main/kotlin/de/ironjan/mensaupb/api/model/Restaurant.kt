package de.ironjan.mensaupb.api.model

import de.ironjan.mensaupb.R

/**
 * Enum to represent restaurants. Each restaurant has a key and a string id - the latter
 * is used to retrieve a potentially localized name.
 */
enum class Restaurant(val key: String, val restaurantName: Int) {
    MENSA_ACADEMICA(Constants.MENSA_ACADEMICA_PADERBORN, R.string.nameMensaAcademica),
    MENSA_FORUM(Constants.MENSA_FORUM_PADERBORN, R.string.nameMensaForum),
    BISTRO_HOTSPOT(Constants.BISTRO_HOTSPOT, R.string.nameBistroHotspot),
    GRILL_CAFE(Constants.GRILL_CAFE, R.string.nameGrillCafe),
    CAFETE(Constants.CAFETE, R.string.nameCafete);

    private object Constants {
        val MENSA_ACADEMICA_PADERBORN = "mensa-academica-paderborn"
        val MENSA_FORUM_PADERBORN = "mensa-forum-paderborn"
        val BISTRO_HOTSPOT = "bistro-hotspot"
        val GRILL_CAFE = "grill-cafe"
        val CAFETE = "cafete"
    }

    companion object {

        val keys = arrayOf(Constants.MENSA_ACADEMICA_PADERBORN,
                Constants.MENSA_FORUM_PADERBORN,
                Constants.BISTRO_HOTSPOT,
                Constants.GRILL_CAFE,
                Constants.CAFETE)

        val nameStringIds: Array<Int>
            get() {
                val nameStringIds = arrayOf(keys.size)
                for (i in keys.indices) {
                    nameStringIds[i] = getNameFromKey(keys[i])
                }
                return nameStringIds
            }

        private fun getNameFromKey(key: String): Int =
                fromKey(key).restaurantName

        /**
         * Retrieves a Restaurant by key
         *
         * @param key the restaurant's key
         * @return the corresponding restaurant
         */
        fun fromKey(key: String): Restaurant {
            when (key) {
                Constants.MENSA_ACADEMICA_PADERBORN -> return MENSA_ACADEMICA
                Constants.MENSA_FORUM_PADERBORN -> return MENSA_FORUM
                Constants.BISTRO_HOTSPOT -> return BISTRO_HOTSPOT
                Constants.GRILL_CAFE -> return GRILL_CAFE
                Constants.CAFETE -> return CAFETE
            }
            throw IllegalArgumentException("Unknown Restaurant key.")
        }
    }
}
