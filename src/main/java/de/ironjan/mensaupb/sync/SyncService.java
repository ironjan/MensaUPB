package de.ironjan.mensaupb.sync;

import android.app.*;
import android.content.*;
import android.os.*;

public class SyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static MenuSyncAdapter sMenuSyncAdapter = null;

    public SyncService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (sSyncAdapterLock) {
            sMenuSyncAdapter = MenuSyncAdapter.getInstance(this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sMenuSyncAdapter.getSyncAdapterBinder();
    }

}
