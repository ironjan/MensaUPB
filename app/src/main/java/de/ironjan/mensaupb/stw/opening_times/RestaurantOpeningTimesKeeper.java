package de.ironjan.mensaupb.stw.opening_times;

import java.util.Date;

interface RestaurantOpeningTimesKeeper {
    /**
     * @param date a date to check this restaurant for
     * @return true, if the restaurant is open on that date
     */
    boolean isOpenOn(Date date);
}
