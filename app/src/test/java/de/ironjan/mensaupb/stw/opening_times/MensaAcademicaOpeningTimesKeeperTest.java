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
 * Mensa Academica
 * Ganzjährig
 * Montag - Donnerstag	11:15 - 14:00 Uhr
 * Freitag	11:15 - 13:30 Uhr
 * Brückentag vom 15.05.2015 bis 15.05.2015
 * Brückentag vom 05.06.2015 bis 05.06.2015
 * Betriebsferien Weihnachten und Jahreswechsel vom 19.12.2015 bis 03.01.2016
 *
 * @see <a href="http://www.studentenwerk-pb.de/gastronomie/oeffnungszeiten/">Official source of opening times</a>
 */
@Config(emulateSdk = 18)
@RunWith(BlockJUnit4ClassRunner.class)
public class MensaAcademicaOpeningTimesKeeperTest extends TestCase {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar;

    @Before
    public void setupCalendar() {
        calendar = Calendar.getInstance();
        calendar.clear();
    }

    @Test
    public void mensaAcademicaShouldBeOpenBefore_2015_05_15() {
        calendar.set(2015, Calendar.MAY, 1);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.MAY, 15);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();
            boolean mensaAcademicaIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_ACADEMICA, date);

            String message = "Mensa Academica is closed on " + sdf.format(date);
            assertTrue(message, mensaAcademicaIsOpenOn);


            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void mensaAcademicaShouldBeClosedOn_2015_05_15() {
        calendar.set(2015, Calendar.MAY, 15);
        Date date = calendar.getTime();

        boolean mensaAcademicaIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_ACADEMICA, date);

        assertFalse("Mensa Academica is opened on 2015-05-15", mensaAcademicaIsOpenOn);
    }

    @Test
    public void mensaAcademicaShouldBeOpenFrom_2015_05_16_To_2015_06_04() {
        calendar.set(2015, Calendar.MAY, 16);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.JUNE, 5);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();
            boolean mensaAcademicaIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_ACADEMICA, date);

            String message = "Mensa Academica is closed on " + sdf.format(date);
            assertTrue(message, mensaAcademicaIsOpenOn);


            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void mensaAcademicaShouldBeClosedOn_2015_06_05() {
        calendar.set(2015, Calendar.JUNE, 5);
        Date date = calendar.getTime();
        boolean mensaAcademicaIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_ACADEMICA, date);

        assertFalse("Mensa Academica is opened on 2015-06-05", mensaAcademicaIsOpenOn);
    }

    @Test
    public void mensaAcademicaShouldBeOpenFrom_2015_06_06_To_2015_12_17() {
        calendar.set(2015, Calendar.JUNE, 6);
        calendar.setLenient(true);

        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2015, Calendar.DECEMBER, 18);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();
            boolean mensaAcademicaIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_ACADEMICA, date);

            String message = "Mensa Academica is closed on " + sdf.format(date);
            assertTrue(message, mensaAcademicaIsOpenOn);


            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void mensaAcademicaShouldBeClosedFrom_2015_12_19_To_2016_01_03() {
        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2016, Calendar.JANUARY, 3);

        calendar.set(2015, Calendar.DECEMBER, 19);
        calendar.setLenient(true);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();

            boolean mensaAcademicaIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_ACADEMICA, date);

            String message = "Mensa Academica is opened on " + sdf.format(date);
            assertFalse(message, mensaAcademicaIsOpenOn);

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}