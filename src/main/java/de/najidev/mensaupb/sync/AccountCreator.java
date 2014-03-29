package de.najidev.mensaupb.sync;

import android.accounts.*;
import android.content.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

/**
 * Created by ljan on 10.01.14.
 */
@EBean
public class AccountCreator {
    @RootContext
    Context mContext;
    @SystemService
    AccountManager mAccountManager;

    /**
     * Neded for synchroniztation initialization
     */
    public static final String AUTHORITY = ProviderContract.AUTHORITY,

            ACCOUNT = ProviderContract.ACCOUNT;
    public static final String ACCOUNT_TYPE = ProviderContract.ACCOUNT_TYPE;

    private final Logger LOGGER = LoggerFactory.getLogger(AccountCreator.class.getSimpleName());
    private boolean mAccountCreated = false;
    private Account mAccount;

    public Account build(Context context) {
        if (mAccount == null) {
            mAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
            mAccountCreated = mAccountManager.addAccountExplicitly(mAccount, null, null);
            if (mAccountCreated) {
                LOGGER.info("Synchronization account added.");
            } else {
                LOGGER.warn("Account already existed.");
            }
        }
        return mAccount;
    }

    public boolean wasAccountCreated() {
        return mAccountCreated;
    }

    public String getAuthority() {
        return AUTHORITY;
    }
}
