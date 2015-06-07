package de.ironjan.mensaupb.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
