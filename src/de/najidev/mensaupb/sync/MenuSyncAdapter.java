package de.najidev.mensaupb.sync;

import android.accounts.*;
import android.annotation.*;
import android.content.*;
import android.os.*;

import org.slf4j.*;

import java.io.*;
import java.net.*;
import java.util.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.stw.*;

/**
 * Created by ljan on 10.01.14.
 */
public class MenuSyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String SYNC_FINISHED = "SYNC_FINISHED";
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
            InputStream in = downloadFile();
            final List<Menu> menus = StwParser.parseInputStream(in);
            final ContentValues[] cvs = convertToContentValues(menus);
            bulkInsert(cvs);

        }
        catch(IOException e){
            syncResult.stats.numIoExceptions++;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("onPerformeSync({},{},{},{},{}) done", new Object[]{account, bundle, s, contentProviderClient, syncResult});
        }
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


    private ContentValues[] convertToContentValues(List<Menu> menus) {
        if(BuildConfig.DEBUG) LOGGER.debug("convertToContentValues(...)");

        int size = menus.size();

        ContentValues[] cvs = new ContentValues[size];
        for(int i=0; i<size; i++){
            Menu menu = menus.get(i);
            ContentValues cv = MenuToContentValuesConverter.convert(menu);
            cvs[i] = cv;
        }

        if(BuildConfig.DEBUG) LOGGER.debug("convertToContentValues(...) done");
        return cvs;
    }

    private void bulkInsert(ContentValues[] cvs) {
        if(BuildConfig.DEBUG) LOGGER.debug("bulkInsert(...)");

        final ContentResolver cr = getContext().getContentResolver();
        cr.bulkInsert(MenuContentProvider.MENU_URI, cvs);
        cr.notifyChange(MenuContentProvider.MENU_URI, null);

        if(BuildConfig.DEBUG) LOGGER.debug("bulkInsert(...) done");
    }
}
