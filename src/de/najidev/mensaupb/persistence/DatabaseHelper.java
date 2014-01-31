package de.najidev.mensaupb.persistence;

import android.content.*;
import android.database.sqlite.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

import org.slf4j.*;

import java.sql.*;

import de.najidev.mensaupb.rest.*;
import de.najidev.mensaupb.stw.*;

/**
 * Created by ljan on 11.01.14.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "mensaupb.db";

    private static final int DATABASE_VERSION = 1;

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
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//        try {
//            LOGGER.info("onUpgrade()");
//            switch (old){
//                case 1:
//                    Dao<Account, Integer> dao = getHelper().getAccountDao();
//// change the table to add a new column named "age"
//                    dao.executeRaw("ALTER TABLE `account` ADD COLUMN age INTEGER;"); }
//
//        } catch (SQLException e) {
//            LOGGER.error("Can't drop databases", e);
//            throw new RuntimeException(e);
//        }
        LOGGER.info("onUpgrade() done");
    }

}
