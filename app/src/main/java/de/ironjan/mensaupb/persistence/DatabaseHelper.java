package de.ironjan.mensaupb.persistence;

import android.content.*;
import android.database.sqlite.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

import org.slf4j.*;

import java.sql.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.library.stw.deprecated.Menu;
import de.ironjan.mensaupb.sync.*;

/**
 * TODO javadoc
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "mensaupb.db";

    private static final int DATABASE_VERSION = 6;

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class.getSimpleName());
    private final Context mContext;

    AccountCreator mAccountCreator;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        mAccountCreator = AccountCreator_.getInstance_(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            LOGGER.info("onCreate()");
            TableUtils.createTable(connectionSource, Menu.class);
            LOGGER.info("Created database.");
        } catch (SQLException e) {
            LOGGER.error("Can't create database", e);
            throw new RuntimeException(e);
        }
        LOGGER.info("onCreate() done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int old, int newVersion) {
        LOGGER.info("onUpgrade()");
        try {
            switch (old) {
                case 1:
                    TableUtils.dropTable(connectionSource, Menu.class, true);
                    onCreate(sqLiteDatabase, connectionSource);
                    break;
                case 2:
                    // there was a bug which added many entries to abendmensa....
                    sqLiteDatabase.delete(Menu.TABLE, Menu.LOCATION + " = ?", new String[]{"Abendmensa"});
                    break;
                case 3:
                    TableUtils.createTable(connectionSource, SynchronizationReport.class);
                    sqLiteDatabase.execSQL("ALTER TABLE " + Menu.TABLE + " ADD COLUMN " + Menu.LAST_UPDATE_TIMESTAMP + " INTEGER");
                    sqLiteDatabase.execSQL("UPDATE " + Menu.TABLE + " SET " + Menu.LAST_UPDATE_TIMESTAMP + "=0");
                    sqLiteDatabase.execSQL("ALTER TABLE " + Menu.TABLE + " ADD COLUMN " + Menu.PRICE + " REAL");
                    break;
                case 4:
                case 5:
                    sqLiteDatabase.execSQL("ALTER TABLE " + Menu.TABLE + " ADD COLUMN " +
                            Menu.PRICE_PER_100G + " INTEGERr");
                    sqLiteDatabase.execSQL("UPDATE " + Menu.TABLE + " SET " +
                            Menu.PRICE_PER_100G + " = 0 WHERE " +
                            Menu.PRICE_PER_100G + " IS NULL");
                    break;
                default:
                    if(BuildConfig.DEBUG) LOGGER.warn("Default upgrade method. Deleting and Recreating.");
                    mContext.deleteDatabase(DATABASE_NAME);
                    onCreate(sqLiteDatabase,connectionSource);
            }
            requestSync();
        } catch (SQLException e) {
            LOGGER.error("Failed to upgrade database; deleting and recreating.", e);
            mContext.deleteDatabase(DATABASE_NAME);
            onCreate(sqLiteDatabase,connectionSource);
        }
        LOGGER.info("onUpgrade() done");
    }

    private void requestSync() {

    }

}
