package de.ironjan.mensaupb.persistence;

import android.content.*;

import com.j256.ormlite.android.apptools.*;

/**
 * Class to manage a DatabaseHelper instance
 */
public class DatabaseManager {

    private DatabaseHelper databaseHelper = null;

    /**
     * gets a helper once one is created ensures it doesnt create a new one
     *
     * @param context the corresponding context
     * @return a helper instance
     */
    public DatabaseHelper getHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    /**
     * Releases the helper once usages has ended
     *
     * @param helper the helper to be released
     */
    public void releaseHelper(DatabaseHelper helper) {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

}
