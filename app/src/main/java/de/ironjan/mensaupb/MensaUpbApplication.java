package de.ironjan.mensaupb;

import android.content.ContentResolver;
import android.os.Bundle;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;
import org.piwik.sdk.PiwikApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.sync.AccountCreator;

/**
 * Used to set up synchronization on first start.
 */
@SuppressWarnings("WeakerAccess")
@EApplication
public class MensaUpbApplication extends PiwikApplication {
    private final Logger LOGGER = LoggerFactory.getLogger(MensaUpbApplication.class.getSimpleName()
    );
    @Bean
    AccountCreator mAccountCreator;

    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) LOGGER.debug("onCreate()");
        super.onCreate();
        setupSynchronization();
        setupUserMonitoring();
        if (BuildConfig.DEBUG) LOGGER.debug("onCreate() done");
    }

    private void setupSynchronization() {
        if (BuildConfig.DEBUG) LOGGER.debug("setupSynchronization()");
        ContentResolver.addPeriodicSync(mAccountCreator.getAccount(), mAccountCreator.getAuthority(), new Bundle(), BuildConfig.SYNC_INTERVAL);

        forceFirstSync();

        if (BuildConfig.DEBUG) LOGGER.debug("setupSynchronization() done");
    }

    private void forceFirstSync() {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(mAccountCreator.getAccount(), mAccountCreator.getAuthority(), settingsBundle);
    }

    @Override
    public String getTrackerUrl() {
        return "http://ironjan.de/piwik/piwik.php";
    }

    @Override
    public Integer getSiteId() {
        return 1;
    }

    private void setupUserMonitoring() {
        getTracker().setDispatchInterval(BuildConfig.SYNC_INTERVAL);
    }


}
