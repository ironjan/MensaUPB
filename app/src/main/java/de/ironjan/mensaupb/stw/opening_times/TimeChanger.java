package de.ironjan.mensaupb.stw.opening_times;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility class to change the time of a given date to a new time
 */
class TimeChanger {
    static Date updateTime(Date date, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }
}
