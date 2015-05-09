package de.ironjan.mensaupb.stw.opening_times;

import java.util.Calendar;
import java.util.Date;

/**
 * Bistro Hotspot
 * <p/>
 * Ganzjährig
 * Montag	09:00 - 15:30 Uhr
 * Dienstag - Freitag	09:00 - 17:00 Uhr
 * Samstag, Sonntag, Feiertage	11:00 - 17:00 Uhr
 * <p/>
 * Geänderte Öffnungszeiten	vom 29.06.2015	bis 19.07.2015
 * Montag-Freitag	09:00-15:00 Uhr
 * Samstag und Sonntag	geschlossen
 * <p/>
 * Essensausgabe
 * Montag - Freitag
 * 11:30 - 14:00 Uhr
 */
class BistroHotspotOpeningTimeKeeper implements RestaurantOpeningTimesKeeper {
    @Override
    public boolean isOpenOn(Date date) {
        Calendar dateInstance = Calendar.getInstance();
        dateInstance.setTime(date);

        int[] specialTimeStart = {2015, Calendar.JUNE, 29};
        int[] specialTimeEnd = {2016, Calendar.JULY, 19};
        boolean isInSpecialTime = IntervalChecker.isInInterval(dateInstance, specialTimeStart, specialTimeEnd);
        int dayOfWeek = dateInstance.get(Calendar.DAY_OF_WEEK);
        boolean isSaturday = (dayOfWeek == Calendar.SATURDAY);
        boolean isSunday = (dayOfWeek == Calendar.SUNDAY);

        if (isInSpecialTime && (isSaturday || isSunday)) {
            return false;
        }
        return true;
    }

    @Override
    public Date isOpenUntil(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
    }
}
