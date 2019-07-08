package de.ironjan.mensaupb.api.model

import de.ironjan.mensaupb.R
import org.slf4j.LoggerFactory

/**
 * Class that represents Allergens and Additionals.
 */
enum class Allergen(val type: String, val stringId: Int) : Comparable<Allergen> {
    UNKNOWN("", R.string.empty),
    COLORED(Constants.COLORED, R.string.colored),
    CONSERVED(Constants.CONSERVED, R.string.conserved),
    ANTIOXIDANTS(Constants.ANTIOXIDANTS, R.string.antioxidants),
    FLAVOR_ENHANCERS(Constants.FLAVOR_ENHANCERS, R.string.tasteEnhancer),
    PHOSPHAT(Constants.PHOSPHAT, R.string.phosphate),
    SULFURATED(Constants.SULFURATED, R.string.sulfured),
    WAXED(Constants.WAXED, R.string.waxed),
    BLACKENED(Constants.BLACKENED, R.string.blackened),
    SWEETENER(Constants.SWEETENER, R.string.sweetener),
    PHENYLALANINE(Constants.PHENYLALANINE, R.string.phenylalanine),
    TAURINE(Constants.TAURINE, R.string.taurine),
    NITRATE_SALT(Constants.NITRATE_SALT, R.string.nitrate_salt),
    COFFEINE(Constants.COFFEINE, R.string.caffeine),
    QUININE(Constants.QUININE, R.string.quinine),
    LACTOPROTEIN(Constants.LACTOPROTEIN, R.string.lacto_protein),
    CRUSTACEAN(Constants.CRUSTACEAN, R.string.crustacean),
    EGGS(Constants.EGGS, R.string.eggs),
    FISH(Constants.FISH, R.string.fish),
    SOYA(Constants.SOYA, R.string.soy),
    LACTOSE(Constants.LACTOSE, R.string.milk),
    NUTS(Constants.NUTS, R.string.nuts),
    CELERIAC(Constants.CELERIAC, R.string.celeriac),
    MUSTARD(Constants.MUSTARD, R.string.mustard),
    SESAME(Constants.SESAME, R.string.sesame),
    SULFITES(Constants.SULFITES, R.string.sulfates),
    MOLLUSKS(Constants.MOLLUSKS, R.string.mollusks),
    LUPINE(Constants.LUPINE, R.string.lupines),
    GLUTEN(Constants.GLUTEN, R.string.gluten),
    PEANUTS(Constants.PEANUTS, R.string.peanuts);

    override fun toString(): String {
        return type
    }


    private object Constants {
        val COLORED = "1"
        val CONSERVED = "2"
        val ANTIOXIDANTS = "3"
        val FLAVOR_ENHANCERS = "4"
        val PHOSPHAT = "5"
        val SULFURATED = "6"
        val WAXED = "7"
        val BLACKENED = "8"
        val SWEETENER = "9"
        val PHENYLALANINE = "10"
        val TAURINE = "11"
        val NITRATE_SALT = "12"
        val COFFEINE = "13"
        val QUININE = "14"
        val LACTOPROTEIN = "15"
        val CRUSTACEAN = "A2"
        val EGGS = "A3"
        val FISH = "A4"
        val SOYA = "A6"
        val LACTOSE = "A7"
        val NUTS = "A8"
        val CELERIAC = "A9"
        val MUSTARD = "A10"
        val SESAME = "A11"
        val SULFITES = "A12"
        val LUPINE = "A13"
        val MOLLUSKS = "A14"
        val GLUTEN = "A1"
        val PEANUTS = "A5"
    }

    companion object {

        /**
         * Converts a string to a Allergen
         *
         * @param string the string
         * @return the Allergen which is textually represented as String, if known. Else "Unknown".
         */
        fun fromString(string: String): Allergen {
            when (string) {
                Constants.COLORED -> return COLORED
                Constants.CONSERVED -> return CONSERVED
                Constants.ANTIOXIDANTS -> return ANTIOXIDANTS
                Constants.FLAVOR_ENHANCERS -> return FLAVOR_ENHANCERS
                Constants.PHOSPHAT -> return PHOSPHAT
                Constants.SULFURATED -> return SULFURATED
                Constants.WAXED -> return WAXED
                Constants.BLACKENED -> return BLACKENED
                Constants.SWEETENER -> return SWEETENER
                Constants.PHENYLALANINE -> return PHENYLALANINE
                Constants.TAURINE -> return TAURINE
                Constants.NITRATE_SALT -> return NITRATE_SALT
                Constants.COFFEINE -> return COFFEINE
                Constants.QUININE -> return QUININE
                Constants.LACTOPROTEIN -> return LACTOPROTEIN
                Constants.CRUSTACEAN -> return CRUSTACEAN
                Constants.EGGS -> return EGGS
                Constants.FISH -> return FISH
                Constants.SOYA -> return SOYA
                Constants.LACTOSE -> return LACTOSE
                Constants.NUTS -> return NUTS
                Constants.CELERIAC -> return CELERIAC
                Constants.MUSTARD -> return MUSTARD
                Constants.SESAME -> return SESAME
                Constants.SULFITES -> return SULFITES
                Constants.MOLLUSKS -> return MOLLUSKS
                Constants.LUPINE -> return LUPINE
                Constants.GLUTEN -> return GLUTEN
                Constants.PEANUTS -> return PEANUTS
            }

            LoggerFactory.getLogger(Allergen::class.java).debug("Requested unknown value: $string")
            return UNKNOWN
        }
    }


}
