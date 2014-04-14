package de.ironjan.mensaupb.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.stw.Allergene;
import de.ironjan.mensaupb.stw.Menu;
import de.ironjan.mensaupb.stw.StwCategoryParser;

public class MenuSyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String WHERE = Menu.DATE + " = ? and " + Menu.LOCATION + " = ? and " + Menu.NAME_GERMAN + " = ?";
    public static final int SELECTION_ARG_LOCATION = 1;
    public static final int SELECTION_ARG_DATE = 0;
    public static final int SELECTION_ARG_GERMAN_NAME = 2;
    private static final Object lock = new Object();
    private static MenuSyncAdapter instance;
    private final Logger LOGGER = LoggerFactory.getLogger(MenuSyncAdapter.class.getSimpleName());
    private final ContentResolver mContentResolver;

    private MenuSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MenuSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    public static MenuSyncAdapter getInstance(Context context) {
        synchronized (lock) {
            if (instance == null) {
                instance = createSyncAdapterSingleton(context);
            }

            return instance;
        }
    }

    private static MenuSyncAdapter createSyncAdapterSingleton(Context context) {
        Context applicationContext = context.getApplicationContext();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            return new MenuSyncAdapter(applicationContext, true, false);
        } else {
            return new MenuSyncAdapter(applicationContext, true);
        }
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{})", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }

        if (syncDisabled(bundle)) {
            return;
        }

        try {
            InputStream inputStream = downloadFile();
            if (BuildConfig.DEBUG) LOGGER.debug("parseInputStream({})", inputStream);

            final ContentResolver cr = getContext().getContentResolver();

            Scanner sc = new Scanner(inputStream, "windows-1252");

            sc.nextLine(); // skip description line

            int[] counter = {0, 0};

            ContentValues menu = null;
            final String[] selectionArgs = new String[3];
            while (sc.hasNextLine()) {
                String[] parts = prepareNextLine(sc);
                if (skipThisLine(parts)) {
                    // skip
                } else {
                    menu = parseLine(menu, parts, selectionArgs);

                    createOrUpdate(mContentResolver, counter, selectionArgs, menu);

                    cr.notifyChange(MenuContentProvider.MENU_URI, null);
                }
            }

            if (BuildConfig.DEBUG)
                LOGGER.debug("parseInputStream({}) done, {} were new, {} were updated", new Object[]{inputStream, counter});
        } catch (IOException e) {
            syncResult.stats.numIoExceptions++;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{}) done", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }
    }

    private boolean syncDisabled(Bundle bundle) {
        if (bundle.containsKey(ContentResolver.SYNC_EXTRAS_MANUAL)) {
            return false;
        }

        AccountManager am = AccountManager.get(getContext());
        Account account = am.getAccountsByType(AccountCreator.ACCOUNT_TYPE)[0];
        final boolean isYourAccountSyncEnabled = ContentResolver.getSyncAutomatically(account, AccountCreator.AUTHORITY);
        final boolean isMasterSyncEnabled = ContentResolver.getMasterSyncAutomatically();

        final boolean syncDisabled = !( isYourAccountSyncEnabled && isMasterSyncEnabled);

        return syncDisabled;
    }

    void createOrUpdate(ContentResolver cr, int[] counter, String[] selectionArgs, ContentValues menu) {
        int updatedLines = tryUpdate(cr, counter, selectionArgs, menu);

        if (0 == updatedLines) {
            doInsert(cr, counter, menu);
        }
    }

    private void doInsert(ContentResolver cr, int[] counter, ContentValues menu) {
        cr.insert(MenuContentProvider.MENU_URI, menu);
        counter[0]++;
    }

    private int tryUpdate(ContentResolver cr, int[] counter, String[] selectionArgs, ContentValues menu) {
        int updatedLines = cr.update(MenuContentProvider.MENU_URI, menu, WHERE, selectionArgs);
        counter[1] += updatedLines;
        return updatedLines;
    }

    private boolean skipThisLine(String[] parts) {
        return parts.length < 2 || TextUtils.equals("Mensa Hamm", parts[1]);
    }


    private String[] prepareNextLine(Scanner sc) {
        if (BuildConfig.DEBUG) LOGGER.trace("prepareNextLine(..)");

        String line = sc.nextLine();

        if (BuildConfig.DEBUG) LOGGER.debug("nextLine -> ({})", line);

        line = line.replaceAll("\"", "");

        if (BuildConfig.DEBUG) LOGGER.debug("prepareNextLine(..) done");
        return line.split(";");
    }

    private ContentValues parseLine(ContentValues menu, String[] parts, String[] selectionArgs) {
        if (BuildConfig.DEBUG) LOGGER.trace("parseLine({}) {}", parts, "");

        if (menu == null) {
            menu = new ContentValues();
        }

        String date = parts[2];
        String name = parts[6];
        final String location;
        if ("a".equals(parts[5])) {
            location = "Abendmensa";
        } else {
            location = parts[1];
        }
        menu.put(Menu.LOCATION, location);


        menu.put(Menu.DATE, date);
        menu.put(Menu.CATEGORY, StwCategoryParser.getCategory(parts[3]));
        menu.put(Menu.SORT, StwCategoryParser.getSort(parts[3]));

        // FIXME can i use resource here?


        menu.put(Menu.NAME_GERMAN, name);

        menu.put(Menu.NAME_ENGLISH, parts[7]);
        menu.put(Menu.ALLERGENES, Allergene.filterAllergenes(parts[8]));


        selectionArgs[SELECTION_ARG_LOCATION] = location;
        selectionArgs[SELECTION_ARG_DATE] = date;
        selectionArgs[SELECTION_ARG_GERMAN_NAME] = name;

        if (BuildConfig.DEBUG) LOGGER.debug("parseLine({}) -> {}", parts, menu);
        return menu;
    }


    private InputStream downloadFile() throws IOException {
        if (BuildConfig.DEBUG) LOGGER.trace("downloadFile()");

        URL url = new URL(BuildConfig.STW_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        if (BuildConfig.DEBUG) LOGGER.debug("downloadFile() done");
        return conn.getInputStream();

    }
}
