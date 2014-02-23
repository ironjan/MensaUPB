package de.ironjan.mensaupb.sync;

import android.accounts.*;
import android.annotation.*;
import android.content.*;
import android.os.*;
import android.text.*;

import org.slf4j.*;

import java.io.*;
import java.net.*;
import java.util.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.persistence.*;
import de.ironjan.mensaupb.stw.*;

/**
 * Created by ljan on 10.01.14.
 */
public class MenuSyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String SYNC_FINISHED = "SYNC_FINISHED";
    public static final String WHERE = Menu.DATE + " = ? and " + Menu.LOCATION + " = ? and " + Menu.NAME_GERMAN + " = ?";
    public static final int SELECTION_ARG_LOCATION = 1;
    public static final int SELECTION_ARG_DATE = 0;
    public static final int SELECTION_ARG_GERMAN_NAME = 2;
    private static MenuSyncAdapter instance;
    private final Logger LOGGER = LoggerFactory.getLogger(MenuSyncAdapter.class.getSimpleName());
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private static Object lock = new Object();

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

    private MenuSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MenuSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
        mContext = context;
    }


    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{})", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }

        try{
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
                    continue;
                } else {
                    menu = parseLine(menu, parts, selectionArgs);

                    createOrUpdate(mContentResolver, counter, selectionArgs, menu, parts);

                    cr.notifyChange(MenuContentProvider.MENU_URI, null);
                }
            }

            if (BuildConfig.DEBUG)
                LOGGER.debug("parseInputStream({}) done, {} were new, {} were updated", new Object[]{inputStream, counter});
        }
        catch(IOException e){
            syncResult.stats.numIoExceptions++;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{}) done", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }
    }

    void createOrUpdate(ContentResolver cr, int[] counter, String[] selectionArgs, ContentValues menu, String[] parts) {
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
        if (BuildConfig.DEBUG) LOGGER.debug("prepareNextLine(..)");

        String line = sc.nextLine();

        if (BuildConfig.DEBUG) LOGGER.debug("nextLine -> ({})", line);

        line = line.replaceAll("\"", "");

        if (BuildConfig.DEBUG) LOGGER.debug("prepareNextLine(..) done");
        return line.split(";");
    }

    private ContentValues parseLine(ContentValues menu, String[] parts, String[] selectionArgs) {
        if (BuildConfig.DEBUG) LOGGER.debug("parseLine({}) {}", parts, "");

        if (menu == null) {
            menu = new ContentValues();
        }

        menu.put(Menu.LOCATION, parts[1]);
        menu.put(Menu.DATE, parts[2]);
        menu.put(Menu.CATEGORY, StwCategoryParser.getCategory(parts[3]));
        menu.put(Menu.SORT, StwCategoryParser.getSort(parts[3]));

        if ("a".equals(parts[5])) menu.put(Menu.LOCATION, "Abendmensa");

        menu.put(Menu.NAME_GERMAN, parts[6]);

        menu.put(Menu.NAME_ENGLISH, parts[7]);
        menu.put(Menu.ALLERGENES, Allergene.filterAllergenes(parts[8]));


        selectionArgs[SELECTION_ARG_LOCATION] = parts[1];
        selectionArgs[SELECTION_ARG_DATE] = parts[2];
        selectionArgs[SELECTION_ARG_GERMAN_NAME] = parts[6];

        if (BuildConfig.DEBUG) LOGGER.debug("parseLine({}) -> {}", parts, menu);
        return menu;
    }

    private void clear() {
        DatabaseManager dbManager = new DatabaseManager();
        int count = dbManager.getHelper(getContext())
                .getWritableDatabase()
                .delete(Menu.TABLE, null, null);
        if (BuildConfig.DEBUG) LOGGER.info("Deleted {} menus from database.", count);
    }

    private InputStream downloadFile() throws IOException {
        LOGGER.debug("downloadFile()");

        URL url = new URL(BuildConfig.STW_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        LOGGER.debug("downloadFile() done");
        return conn.getInputStream();

    }
}
