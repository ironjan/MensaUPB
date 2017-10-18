package de.ironjan.mensaupb.sync;

import de.ironjan.mensaupb.BuildConfig;

/**
 * General contract for the content provider of this app
 */
public class ProviderContract {
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    public static final String ACCOUNT_TYPE = BuildConfig.APPLICATION_ID + ".account";
    public static final String ACCOUNT = "dummy";
}
