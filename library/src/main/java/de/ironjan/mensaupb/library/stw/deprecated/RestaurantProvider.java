package de.ironjan.mensaupb.library.stw.deprecated;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.ironjan.mensaupb.library.BuildConfig;

/**
 * TODO javadoc
 */
@EBean(scope = EBean.Scope.Singleton)
@Deprecated
public class RestaurantProvider {
    @Bean
    Restaurants mRestaurants;

    private final Logger LOGGER = LoggerFactory.getLogger(RestaurantProvider.class.getSimpleName());

    private static final SimpleDateFormat LOGGING_DATE_FORMAT = new SimpleDateFormat("");


    /**
     * TODO javadoc
     */
    public int getLocation() {
        if (BuildConfig.DEBUG) LOGGER.trace("getLocation()");
        if (isAbendmensaTime()) {
            if (BuildConfig.DEBUG) LOGGER.debug("getLocation() -> time for Abendmensa ");
            return mRestaurants.getIdOfAbendmensa();
        }
        if (BuildConfig.DEBUG) LOGGER.debug("getLocation() -> Mensa (nothing special)");
        return mRestaurants.getIdOfMensa();
    }

    private boolean isAbendmensaTime() {
        if (BuildConfig.DEBUG) LOGGER.trace("isAbendmensaTime()");

        Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        Date now = calendar.getTime();
        if (BuildConfig.DEBUG)
            LOGGER.trace("isAbendmensaTime() - now:   {}", LOGGING_DATE_FORMAT.format(now));

        calendar.set(year, month, day, 14, 00, 00);
        Date showAbendMensaStart = calendar.getTime();
        if (BuildConfig.DEBUG)
            LOGGER.trace("isAbendmensaTime() - from:  {}", LOGGING_DATE_FORMAT.format(showAbendMensaStart));

        calendar.set(year, month, day, 19, 30, 00);
        Date showAbendMensaEnd = calendar.getTime();
        if (BuildConfig.DEBUG)
            LOGGER.trace("isAbendmensaTime()- until:  {}", LOGGING_DATE_FORMAT.format(showAbendMensaEnd));

        final boolean isAbendMensaTime = showAbendMensaStart.before(now) && now.before(showAbendMensaEnd);
        if (BuildConfig.DEBUG) LOGGER.debug("isAbendmensaTime() -> {}", isAbendMensaTime);
        return isAbendMensaTime;
    }
}
