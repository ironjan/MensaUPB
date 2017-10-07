package de.ironjan.mensaupb.stw.rest_api;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.rest.spring.annotations.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.BuildConfig;

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

    @Override
    public StwMenu[] getMenus(String restaurant, String date) {
        if (BuildConfig.STW_URL.isEmpty()) {
            LOGGER.warn("STW_URL is empty. Mocking response for ({},{})", restaurant, date);
            return MockRestWrapper.getInstance().getMenus(restaurant, date);
        }
        return stwRest.getMenus(restaurant, date);
    }

}
