package de.ironjan.mensaupb.persistence;

import android.content.*;
import android.database.sqlite.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

import org.slf4j.*;

import java.sql.*;

import de.ironjan.mensaupb.stw.*;

/**
 * TODO javadoc
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "mensaupb.db";

    private static final int DATABASE_VERSION = 4;

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class.getSimpleName());

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
                    // TODO request sync
                    break;
                case 3:
                    TableUtils.createTable(connectionSource, SynchronizationReport.class);
                    sqLiteDatabase.execSQL("ALTER TABLE " + Menu.TABLE + " ADD COLUMN " + Menu.LAST_UPDATE_TIMESTAMP + " INTEGER");
                    sqLiteDatabase.execSQL("UPDATE " + Menu.TABLE + " SET " + Menu.LAST_UPDATE_TIMESTAMP + "=0");
                    break;
                case 4:
                    sqLiteDatabase.execSQL("ALTER TABLE " + Menu.TABLE + " ADD COLUMN " + Menu.PRICE + " REAL");
                    // TODO request sync
                    break;
                case 5:
                    sqLiteDatabase.execSQL("ALTER TABLE " + Menu.TABLE + " ADD COLUMN " +
                            Menu.PRICE_PER_100G + " INTEGERr");
                    sqLiteDatabase.execSQL("UPDATE " + Menu.TABLE + " SET " +
                            Menu.PRICE_PER_100G + " = 0 WHERE " +
                            Menu.PRICE_PER_100G + " IS NULL");
                    // TODO request sync
                    break;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("onUpgrade() done");
    }

}
