package de.ironjan.mensaupb.sync;

import android.accounts.*;
import android.content.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

/**
 * Class to generate and add an account to the device's account list. Needed for the android
 * sync framework.
 */
@EBean
public class AccountCreator {
    /**
     * Neded for synchroniztation initialization
     */
    public static final String AUTHORITY = ProviderContract.AUTHORITY;
    public static final String ACCOUNT_TYPE = ProviderContract.ACCOUNT_TYPE;

    private final Logger LOGGER = LoggerFactory.getLogger(AccountCreator.class.getSimpleName());

    @RootContext
    Context mContext;

    @SystemService
    AccountManager mAccountManager;

    private Account mAccount;

    /**
     * Returns the account associated with this app. Adds it to the account list if necessary
     *
     * @return the account associated with this app
     */
    public Account getAccount() {
        if (mAccount == null) {
            mAccount = new Account(ProviderContract.ACCOUNT, ACCOUNT_TYPE);
            if (mAccountManager == null) {
                LOGGER.warn("AccountManager was null.");
                return mAccount;
            }
            boolean mAccountCreated = mAccountManager.addAccountExplicitly(mAccount, null, null);
            if (mAccountCreated) {
                LOGGER.info("Synchronization account added.");
            } else {
                LOGGER.warn("Account already existed.");
            }
        }
        return mAccount;
    }

    /**
     * @return the authority string
     */
    public String getAuthority() {
        return AUTHORITY;
    }
}
