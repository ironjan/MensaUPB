package de.ironjan.mensaupb.stw.opening_times;

import java.util.Calendar;

/**
 * Provides utility methods to check if a given Calendar date instance is a given date or in
 * a given interval. Dates must be given as integer array in the format {YEAR, MONTH, DAY}.
 */
class IntervalChecker {

    public static final int YEAR_INDEX = 0;
    public static final int MONTH_INDEX = 1;
    public static final int DAY_INDEX = 2;

    public static boolean isOn(Calendar dateInstance, int[] date) {
        checkArray(date);

        int year = dateInstance.get(Calendar.YEAR);
        int month = dateInstance.get(Calendar.MONTH);
        int day = dateInstance.get(Calendar.DAY_OF_MONTH);

        boolean isOnDate = year == date[YEAR_INDEX]
                && month == date[MONTH_INDEX]
                && day == date[DAY_INDEX];

        return isOnDate;
    }

    public static boolean isInInterval(Calendar dateInstance, int[] intervalStart, int[] intervalEnd) {
        checkArray(intervalStart);
        checkArray(intervalEnd);

        Calendar intervalStartCalendar = Calendar.getInstance();
        intervalStartCalendar.clear();
        intervalStartCalendar.setLenient(true);
        intervalStartCalendar.set(intervalStart[YEAR_INDEX], intervalStart[MONTH_INDEX], intervalStart[DAY_INDEX] - 1);


        Calendar intervalEndCalendar = Calendar.getInstance();
        intervalEndCalendar.clear();
        intervalEndCalendar.setLenient(true);
        intervalEndCalendar.set(intervalEnd[YEAR_INDEX], intervalEnd[MONTH_INDEX], intervalEnd[DAY_INDEX] + 1);


        if (intervalStartCalendar.before(dateInstance)
                && dateInstance.before(intervalEndCalendar)) {
            return true;
        }
        return false;
    }

    private static void checkArray(int[] interval) {
        if (interval == null && interval.length != 3)
            throw new IllegalArgumentException("interval borders must not be null and have length 3.");
    }

}
