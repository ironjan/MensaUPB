package de.ironjan.mensaupb.library.stw;

import android.content.Context;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;

import de.ironjan.mensaupb.library.BuildConfig;

/**
 * Wrapper for the StwRest
 */
@EBean
public class StwRestWrapper implements StwRest {

    @RestService
    StwRest stwRest;
    @RootContext
    Context mContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(StwRestWrapper.class);

    @Override
    public RawMenu[] getMenus(String restaurant, String date) {
        if (BuildConfig.STW_URL.isEmpty()) {
            LOGGER.warn("STW_URL is empty. Mocking response for ({},{})", restaurant, date);
            return MockRestWrapper.getInstance().getMenus(restaurant, date);
        }
        return stwRest.getMenus(restaurant, date);
    }

}
