package de.ironjan.mensaupb.persistence;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;
import de.ironjan.mensaupb.sync.AccountCreator;
import de.ironjan.mensaupb.sync.AccountCreator_;

/**
 * Class to manage the underlying database scheme.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "mensaupb.db";

    private static final int DATABASE_VERSION = 15;

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class.getSimpleName());

    private final AccountCreator mAccountCreator;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mAccountCreator = AccountCreator_.getInstance_(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        LOGGER.debug("onCreate()");
        try {
            TableUtils.createTableIfNotExists(connectionSource, StwMenu.class);
            LOGGER.info("Created database.");
        } catch (SQLException e) {
            LOGGER.error("Can't create database", e);
            throw new RuntimeException(e);
        }
        requestSync();
        LOGGER.info("onCreate() done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int old, int newVersion) {
        LOGGER.info("onUpgrade()");
        try {
            //noinspection MagicNumber
            if (old <= 14) {
                TableUtils.dropTable(connectionSource, StwMenu.class, true);
                onCreate(sqLiteDatabase, connectionSource);
                LOGGER.info("Database updated from {} to {}", old, newVersion);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't update database", e);
            throw new RuntimeException(e);
        }
        requestSync();
        LOGGER.info("onUpgrade() done");
    }


    private void requestSync() {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        ContentResolver.requestSync(mAccountCreator.getAccount(), mAccountCreator.getAuthority(), settingsBundle);
    }

}
