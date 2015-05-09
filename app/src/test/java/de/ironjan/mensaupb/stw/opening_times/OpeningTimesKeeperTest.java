package de.ironjan.mensaupb.stw.opening_times;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.ironjan.mensaupb.stw.Restaurant;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class OpeningTimesKeeperTest extends TestCase {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar;

    //    Mensa Academica
//
//    Ganzjährig
//    Montag - Donnerstag	11:15 - 14:00 Uhr
//    Freitag	11:15 - 13:30 Uhr
//
    @Before
    public void setupCalendar() {
        calendar = Calendar.getInstance();
        calendar.clear();
    }

    /**
     * "Brückentag vom 15.05.2015 bis 15.05.2015"
     */
    @Test
    public void mensaAcademicaShouldBeClosedOn_2015_05_15() {
        calendar.set(2015, Calendar.MAY, 15);
        Date date = calendar.getTime();
        boolean mensaAcademicaIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_ACADEMICA.key, sdf.format(date));

        assertFalse("Mensa Academica is opened on 2015-05-15", mensaAcademicaIsOpenOn);
    }

    /**
     * "Brückentag vom 05.06.2015 bis 05.06.2015"
     */
    @Test
    public void mensaAcademicaShouldBeClosedOn_2015_06_05() {
        calendar.set(2015, Calendar.JUNE, 5);
        Date date = calendar.getTime();
        boolean mensaAcademicaIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_ACADEMICA.key, sdf.format(date));

        assertFalse("Mensa Academica is opened on 2015-06-05", mensaAcademicaIsOpenOn);
    }

    /**
     * "Betriebsferien Weihnachten und Jahreswechsel vom 19.12.2015 bis 03.01.2016"
     */
    @Test
    public void mensaAcademicaShouldBeClosedFrom_2015_12_19_To_2016_01_03() {
        Calendar fixedEnd = Calendar.getInstance();
        fixedEnd.clear();
        fixedEnd.set(2016, Calendar.JANUARY, 03);

        calendar.set(2015, Calendar.DECEMBER, 19);
        calendar.setLenient(true);

        while (calendar.before(fixedEnd)) {
            Date date = calendar.getTime();

            boolean mensaAcademicaIsOpenOn = OpeningTimesKeeper.isOpenOn(Restaurant.MENSA_ACADEMICA.key, sdf.format(date));

            String message = "Mensa Academica is opened on " + sdf.format(date);
            assertFalse(message, mensaAcademicaIsOpenOn);

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    /**
     * "Brückentag vom 15.05.2015 bis 15.05.2015"
     * "Brückentag vom 05.06.2015 bis 05.06.2015"
     * "Betriebsferien Weihnachten und Jahreswechsel vom 19.12.2015 bis 03.01.2016"
     */
    @Test
    public void mensaAcademicaShouldBeOpenBefore_2015_05_15() {
        fail();
    }

    /**
     * "Brückentag vom 15.05.2015 bis 15.05.2015"
     * "Brückentag vom 05.06.2015 bis 05.06.2015"
     * "Betriebsferien Weihnachten und Jahreswechsel vom 19.12.2015 bis 03.01.2016"
     */
    @Test
    public void mensaAcademicaShouldBeOpenFrom_2015_05_16_To_2015_06_05() {
        fail();
    }

    /**
     * "Brückentag vom 15.05.2015 bis 15.05.2015"
     * "Brückentag vom 05.06.2015 bis 05.06.2015"
     * "Betriebsferien Weihnachten und Jahreswechsel vom 19.12.2015 bis 03.01.2016"
     */
    @Test
    public void mensaAcademicaShouldBeOpenFrom_2015_06_05_To_2015_12_18() {
        fail();
    }
//
//    Mensa Forum
//
//    Ganzjährig
//    Montag - Donnerstag	11:15 - 14:00 Uhr
//    Freitag	11:15 - 13:30 Uhr
//
//    Betriebsferien	vom 21.02.2015	bis 22.03.2015
//    Betriebsferien	vom 18.07.2015	bis 11.10.2015
//    Betriebsferien Weihnachten und Jahreswechsel	vom 19.12.2015	bis 03.01.2016
//
//    Bistro Hotspot
//
//    Ganzjährig
//    Montag	09:00 - 15:30 Uhr
//    Dienstag - Freitag	09:00 - 17:00 Uhr
//    Samstag, Sonntag, Feiertage	11:00 - 17:00 Uhr
//
//    Geänderte Öffnungszeiten	vom 29.06.2015	bis 19.07.2015
//    Montag-Freitag	09:00-15:00 Uhr
//    Samstag und Sonntag	geschlossen
//
//    Grill | Café
//
//            Vorlesungszeit
//    Montag - Donnerstag	09:00 - 22:00 Uhr
//    Freitag	09:00 - 18:00 Uhr
//
//    Vorlesungsfreie Zeit	vom 14.02.2015	bis 06.04.2015
//    Montag - Donnerstag	09:00 - 19:30 Uhr
//    Freitag	09:00 - 18:00 Uhr
//
//    Vorlesungsfreie Zeit	vom 25.07.2015	bis 18.10.2015
//    Montag - Freitag	09:00 - 18:00 Uhr
//
//    Wartungsarbeiten	vom 06.03.2015	bis 06.03.2015
//    Brückentag	vom 15.05.2015	bis 15.05.2015
//    Brückentag	vom 05.06.2015	bis 05.06.2015
//    Betriebsferien Weihnachten und Jahreswechsel	vom 19.12.2015	bis 03.01.2016
//
//    Caféte
//
//            Vorlesungszeit
//    Montag- Donnerstag	08:00 - 17:00 Uhr
//    Freitag	08:00 - 15:45 Uhr
//    Samstag	10:00 - 14:00 Uhr
//
//    Vorlesungsfreie Zeit	vom 07.02.2015	bis 27.02.2015
//    Montag- Donnerstag	08:00 - 15:45 Uhr
//    Freitag	08:00 - 14:45 Uhr
//    Samstag	10:00 - 14:00 Uhr
//
//    Vorlesungsfreie Zeit	vom 28.02.2015	bis 06.04.2015
//    Montag- Donnerstag	08:00 - 15:45 Uhr
//    Freitag	08:00 - 14:45 Uhr
//    Samstag	geschlossen
//
//    Betriebsferien Weihnachten und Jahreswechsel	vom 24.12.2015	bis 03.01.2016
}