package de.ironjan.mensaupb.stw.opening_times;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.robolectric.annotation.Config;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
public class MensaForumOpeningTimesKeeper_Time_Test extends TestCase {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private Calendar calendar;

    @Before
    public void setupCalendar() {
        calendar = Calendar.getInstance();
        calendar.clear();
    }

    @Test
    public void niy() {
        fail("niy");
    }
}