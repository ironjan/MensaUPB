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
 * Vorlesungszeit
 * Montag - Donnerstag	09:00 - 22:00 Uhr
 * Freitag	09:00 - 18:00 Uhr
 * <p/>
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
public class CafeteOpeningTimesKeeper_Time_Test extends TestCase {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private Calendar calendar;

    @Before
    public void setupCalendar() {
        calendar = Calendar.getInstance();
        calendar.clear();
    }

    @Test
    public void cafeteShouldBeOpenUntil_22_inSummerTerm() {
        calendar.set(2015, Calendar.APRIL, 7);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.JULY, 25);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();
            Date openUntil = OpeningTimesKeeper.isOpenUntil(Restaurant.CAFETE.key, sdf.format(date));

            assertEquals("22:00", time.format(openUntil));

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    /**
     * Vorlesungsfreie Zeit	vom 25.07.2015	bis 18.10.2015
     */
    @Test
    public void cafeteShouldBeOpenUntil_18_inSemesterBreak() {
        calendar.set(2015, Calendar.JULY, 26);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.JULY, 18);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();
            String dateAsKey = sdf.format(date);
            Date openUntil = OpeningTimesKeeper.isOpenUntil(Restaurant.CAFETE.key, dateAsKey);

            assertEquals("Fail for " + dateAsKey, "18:00", time.format(openUntil));

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void cafeteShouldBeOpenUntil_22_inWinterTerm() {
        calendar.set(2015, Calendar.OCTOBER, 19);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.DECEMBER, 31);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();
            Date openUntil = OpeningTimesKeeper.isOpenUntil(Restaurant.CAFETE.key, sdf.format(date));

            assertEquals("Fail for " + sdf.format(date), "22:00", time.format(openUntil));

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

}
