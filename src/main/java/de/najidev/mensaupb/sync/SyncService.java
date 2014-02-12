package de.najidev.mensaupb.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SyncService extends Service {
    private static MenuSyncAdapter sMenuSyncAdapter = null;
    private static final Object sSyncAdapterLock = new Object();

    public SyncService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (sSyncAdapterLock){
            sMenuSyncAdapter = MenuSyncAdapter.getInstance(this);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sMenuSyncAdapter.getSyncAdapterBinder();
    }

}
