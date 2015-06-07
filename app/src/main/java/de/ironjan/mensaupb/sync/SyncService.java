package de.ironjan.mensaupb.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.BuildConfig;

public class SyncService extends Service {
    private static final Logger LOGGER = LoggerFactory.getLogger("SyncService");
    private static final Object sSyncAdapterLock = new Object();
    private static MenuSyncAdapter sMenuSyncAdapter = null;

    public SyncService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (sSyncAdapterLock) {
            if (BuildConfig.DEBUG) LOGGER.info("Got MenuSyncAdapter instance.");
            sMenuSyncAdapter = MenuSyncAdapter.getInstance(this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sMenuSyncAdapter.getSyncAdapterBinder();
    }

}
