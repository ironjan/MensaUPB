package de.ironjan.mensaupb.stw.opening_times;

import java.util.Date;

interface RestaurantOpeningTimesKeeper {
    /**
     * @param date a date to check this restaurant for
     * @return true, if the restaurant is open on that date
     */
    boolean isOpenOn(Date date);

    /**
     * @param date The date to check for
     * @return The date with the closing time for this day
     */
    Date hasCheapFoodUntil(Date date);
}
