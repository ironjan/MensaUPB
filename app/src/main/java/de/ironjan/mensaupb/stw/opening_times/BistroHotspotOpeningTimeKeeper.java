package de.ironjan.mensaupb.stw.opening_times;

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
        return true;
    }
}
