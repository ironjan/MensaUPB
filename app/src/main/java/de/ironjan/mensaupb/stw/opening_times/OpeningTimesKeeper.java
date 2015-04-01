package de.ironjan.mensaupb.stw.opening_times;

import java.util.Date;

import de.ironjan.mensaupb.stw.Restaurant;

/**
 * A class to fetch the opening times of the restaurants from.
 *
 * @see <a href="http://www.studentenwerk-pb.de/gastronomie/oeffnungszeiten/">Official source of opening times</a>
 */
public class OpeningTimesKeeper {
    public static boolean isOpenOn(Restaurant restaurant, Date date) {
        if (Restaurant.MENSA_ACADEMICA == restaurant) {
            return (new MensaAcademicaOpeningTimesKeeper()).isOpenOn(date);
        }
        return true;
    }

}
