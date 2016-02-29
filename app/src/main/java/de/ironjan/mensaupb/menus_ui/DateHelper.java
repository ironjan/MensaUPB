package de.ironjan.mensaupb.menus_ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO merge with WeekDayHelper
 *
 * @see WeekdayHelper
 */
public class DateHelper {

    private static final SimpleDateFormat EXCHANGE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

    /**
     * Computes the offset of day to today
     *
     * @param day a day. must not be null
     * @return the offset between day and today
     * @see "http://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-in-java"
     */
    public static int computeDayOffset(Date day) {
        if (day == null) {
            throw new IllegalArgumentException("day must not be null.");
        }

        Calendar dayOne = Calendar.getInstance();
        Calendar dayTwo = Calendar.getInstance();
        dayTwo.setTime(day);

        if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
            return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
        } else {
            if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
                //swap them
                Calendar temp = dayOne;
                dayOne = dayTwo;
                dayTwo = temp;
            }
            int extraDays = 0;

            while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
                dayOne.add(Calendar.YEAR, -1);
                // getActualMaximum() important for leap years
                extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
            }

            return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOne.get(Calendar.DAY_OF_YEAR);
        }
    }

    /**
     * Converts date to a exchangeable string
     *
     * @param date the date, must not be null
     * @return an exchangeable date string
     */
    public static String toString(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null.");
        }
        return EXCHANGE_DATE_FORMAT.format(date);
    }

    /**
     * Converts the exchangeable string to a date
     *
     * @param dateString the exchangeable dateString. must match yyyy-MM-dd HH:mm:ss.SSSZ
     * @return the date
     */
    public static Date toDate(String dateString) {
        if (dateString == null) {
            throw new IllegalArgumentException("dateString must not be null.");
        }

        try {
            return EXCHANGE_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("dateString did not match the requested pattern.");
        }
    }
}
