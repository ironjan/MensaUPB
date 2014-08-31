package de.ironjan.mensaupb.sync;

import android.app.*;
import android.content.*;
import android.os.*;

import org.slf4j.*;

import de.ironjan.mensaupb.*;

public class SyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    public static final Logger LOGGER = LoggerFactory.getLogger("SyncService");
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
