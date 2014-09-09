package de.ironjan.mensaupb.sync;

import android.app.*;
import android.content.*;
import android.os.*;

/**
 * A service that instantiates the {@link de.ironjan.mensaupb.sync.StubAuthenticator}
 */
public class AuthenticatorService extends Service {

    private StubAuthenticator mAuthenticator;

    public AuthenticatorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new StubAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
