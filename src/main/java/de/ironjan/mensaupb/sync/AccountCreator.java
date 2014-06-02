package de.ironjan.mensaupb.sync;

import android.accounts.*;
import android.content.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

/**
 * TODO javadoc
 */
@EBean
public class AccountCreator {
    /**
     * Neded for synchroniztation initialization
     */
    public static final String AUTHORITY = ProviderContract.AUTHORITY,

    ACCOUNT = ProviderContract.ACCOUNT;
    /**
     * TODO javadoc
     */
    public static final String ACCOUNT_TYPE = ProviderContract.ACCOUNT_TYPE;

    private final Logger LOGGER = LoggerFactory.getLogger(AccountCreator.class.getSimpleName());

    @RootContext
    Context mContext;

    @SystemService
    AccountManager mAccountManager;

    private boolean mAccountCreated = false;
    private Account mAccount;

    /**
     * TODO javadoc
     */
    public Account getAccount() {
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

    /**
     * TODO javadoc
     */
    public boolean ismAccountCreated() {
        return mAccountCreated;
    }

    /**
     * TODO javadoc
     */
    public String getAuthority() {
        return AUTHORITY;
    }
}
