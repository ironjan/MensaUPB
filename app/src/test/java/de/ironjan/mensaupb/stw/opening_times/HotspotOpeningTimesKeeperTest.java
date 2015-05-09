package de.ironjan.mensaupb.stw.opening_times;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.robolectric.annotation.Config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.ironjan.mensaupb.stw.Restaurant;

/**
 * Ganzjährig
 * Montag 09:00 - 15:30 Uhr
 * Dienstag - Freitag 09:00 - 17:00 Uhr
 * Samstag, Sonntag, Feiertage 11:00 - 17:00 Uhr
 * <p/>
 * Geänderte Öffnungszeiten	vom 29.06.2015 bis 19.07.2015
 * Montag - Freitag 09:00-15:00 Uhr
 * Samstag und Sonntag geschlossen
 *
 * @see <a href="http://www.studentenwerk-pb.de/gastronomie/oeffnungszeiten/">Official source of opening times</a>
 */
@Config(emulateSdk = 18)
@RunWith(BlockJUnit4ClassRunner.class)
public class HotspotOpeningTimesKeeperTest extends TestCase {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar;

    @Before
    public void setupCalendar() {
        calendar = Calendar.getInstance();
        calendar.clear();
    }

    @Test
    public void hotspotShouldBeClosedOnSaturdaysAndSundaysFrom_2015_06_29_To_2015_07_19() {
        calendar.set(2015, Calendar.JUNE, 29);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.JULY, 19);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();
            boolean hotspotIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.BISTRO_HOTSPOT.key, sdf.format(date));
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            boolean isSaturdayOrSunday = (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);

            if (isSaturdayOrSunday) {
                String message = "Hotspot is open on " + sdf.format(date);
                assertFalse(message, hotspotIsOpenOn);
            } else {
                String message = "Hotspot is closed on " + sdf.format(date);
                assertTrue(message, hotspotIsOpenOn);
            }

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
