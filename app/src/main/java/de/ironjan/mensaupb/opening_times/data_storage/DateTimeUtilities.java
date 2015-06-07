package de.ironjan.mensaupb.opening_times.data_storage;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility class to work with dates and times.
 */
class DateTimeUtilities {

    public static final int YEAR_INDEX = 0;
    public static final int MONTH_INDEX = 1;
    public static final int DAY_INDEX = 2;

    /**
     * Checks if a given Calendar instance is on the given date.
     *
     * @param dateInstance a Calendar instance to check
     * @param date         Array in the format {YEAR, MONTH, DAY}
     * @return true, if dateInstance's date is {YEAR, MONTH, DAY}; false otherwise
     */
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

    /**
     * Checks if a given Calendar instance is in the given date interval.
     *
     * @param dateInstance  a Calendar instance to check
     * @param intervalStart start of the interval as array in the format {YEAR, MONTH, DAY}
     * @param intervalEnd   end of the interval as array in the format {YEAR, MONTH, DAY}
     * @return true, if dateInstance's date is in the interval; false otherwise
     */
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

    /**
     * Checks the validity of the given array
     *
     * @param interval an array that should be in the format {YEAR, MONTH, DAY}
     */
    private static void checkArray(int[] interval) {
        if (interval == null && interval.length != 3)
            throw new IllegalArgumentException("interval borders must not be null and have length 3.");
    }

    /**
     * Changes the time of <code>date</code> to hour:minute
     *
     * @param date   the date to change
     * @param hour   the new hours value
     * @param minute the new minutes value
     * @return a new Date object with the new values
     */
    static Date updateTime(Date date, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }
}
