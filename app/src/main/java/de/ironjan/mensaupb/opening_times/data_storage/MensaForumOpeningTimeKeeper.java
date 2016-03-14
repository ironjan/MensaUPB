package de.ironjan.mensaupb.opening_times.data_storage;

import java.util.Calendar;
import java.util.Date;

/**
 * Ganzjährig
 * Montag - Donnerstag 11:15 - 14:00 Uhr
 * Freitag 11:15 - 13:30 Uhr
 * <p/>
 * Betriebsferien vom 18.07.2015 bis 11.10.2015
 * Betriebsferien Weihnachten und Jahreswechsel	vom 19.12.2015 bis 03.01.2016
 *
 * @see <a href="http://www.studentenwerk-pb.de/gastronomie/oeffnungszeiten/">Official source of opening times</a>
 */
class MensaForumOpeningTimeKeeper implements RestaurantOpeningTimesKeeper {
    @Override
    public boolean isOpenOn(Date date) {
        int[] closedIntervalStart = {2015, Calendar.JULY, 18};
        int[] closedIntervalEnd = {2015, Calendar.OCTOBER, 11};

        int[] closedIntervalWinterStart = {2015, Calendar.DECEMBER, 19, 3};
        int[] closedIntervalWinterEnd = {2016, Calendar.JANUARY, 3};

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        boolean inInterval = DateTimeUtilities.isInInterval(calendar, closedIntervalStart, closedIntervalEnd);
        boolean inChristmas = DateTimeUtilities.isInInterval(calendar, closedIntervalWinterStart, closedIntervalWinterEnd);

        return !(inInterval || inChristmas);
    }


    @Override
    public Date hasCheapFoodUntil(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        boolean isFriday = Calendar.FRIDAY == calendar.get(Calendar.DAY_OF_WEEK);
        if (isFriday) {
            return DateTimeUtilities.updateTime(date, 13, 30);
        } else {
            return DateTimeUtilities.updateTime(date, 14, 0);
        }
    }
}
