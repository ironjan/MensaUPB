package de.ironjan.mensaupb.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import de.ironjan.mensaupb.stw.Menu;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "mensaupb.db";

    private static final int DATABASE_VERSION = 3;

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
        switch (old) {
            case 1:
                try {
                    TableUtils.dropTable(connectionSource, Menu.class, true);
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
                onCreate(sqLiteDatabase, connectionSource);
                break;
            case 2:
                // there was a bug which added many entries to abendmensa....
                sqLiteDatabase.delete(Menu.TABLE, Menu.LOCATION + " = ?", new String[]{"Abendmensa"});
                break;
        }
        LOGGER.info("onUpgrade() done");
    }

}
