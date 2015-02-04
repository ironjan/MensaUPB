package de.ironjan.mensaupb.stw;

import android.content.*;

import com.j256.ormlite.logger.*;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.*;

/**
 * Wrapper for the StwRest
 */
@SuppressWarnings("WeakerAccess")
@EBean
public class StwRestWrapper implements StwRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StwRestWrapper.class);
    @RestService
    StwRest stwRest;
    @RootContext
    Context mContext;

    @Bean
    NoMenuRestWrapper mNoMenuRestWrapper;

    @Override
    public RawMenu[] getMenus(String restaurant, String date) {
        LOGGER.warn("Returning no menus because we're using NoMenuRestWrapper!");
        return mNoMenuRestWrapper.getMenus(restaurant, date);
    }

}
