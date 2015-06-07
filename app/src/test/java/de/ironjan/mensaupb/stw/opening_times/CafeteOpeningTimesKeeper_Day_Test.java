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
import de.ironjan.mensaupb.opening_times.data_storage.OpeningTimesKeeper;

/**
 * Vorlesungszeit
 * Montag - Donnerstag	09:00 - 22:00 Uhr
 * Freitag	09:00 - 18:00 Uhr
 * <p/>
 * Vorlesungsfreie Zeit	vom 14.02.2015	bis 06.04.2015
 * Montag - Donnerstag	09:00 - 19:30 Uhr
 * Freitag	09:00 - 18:00 Uhr
 * <p/>
 * Vorlesungsfreie Zeit	vom 25.07.2015	bis 18.10.2015
 * Montag - Freitag	09:00 - 18:00 Uhr
 * <p/>
 * Brückentag	vom 15.05.2015	bis 15.05.2015
 * Brückentag	vom 05.06.2015	bis 05.06.2015
 * Betriebsferien Weihnachten und Jahreswechsel	vom 19.12.2015	bis 03.01.2016
 *
 * @see <a href="http://www.studentenwerk-pb.de/gastronomie/oeffnungszeiten/">Official source of opening times</a>
 */
@Config(emulateSdk = 18)
@RunWith(BlockJUnit4ClassRunner.class)
public class CafeteOpeningTimesKeeper_Day_Test extends TestCase {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar;

    @Before
    public void setupCalendar() {
        calendar = Calendar.getInstance();
        calendar.clear();
    }

    @Test
    public void cafeteShouldBeOpenBefore_2015_12_24() {
        calendar.set(2015, Calendar.APRIL, 1);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.DECEMBER, 24);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();
            boolean cafeteIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.CAFETE.key, sdf.format(date));

            String message = "Cafete is closed on " + sdf.format(date);
            assertTrue(message, cafeteIsOpenOn);


            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void cafeteShouldBeClosedFrom_2015_12_24_To_2016_01_03() {
        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2016, Calendar.JANUARY, 3);

        calendar.set(2015, Calendar.DECEMBER, 24);
        calendar.setLenient(true);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();

            boolean cafeteIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.CAFETE.key, sdf.format(date));

            String message = "Cafete is opened on " + sdf.format(date);
            assertFalse(message, cafeteIsOpenOn);

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
