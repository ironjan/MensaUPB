package de.najidev.mensaupb.persistence;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

import java.sql.*;

import de.najidev.mensaupb.rest.*;

/**
 * Created by ljan on 11.01.14.
 */
public class MenuContentDao  extends BaseDaoImpl<MenuContent,Long> {
    public MenuContentDao(Class<MenuContent> dataClass) throws SQLException {
        super(dataClass);
    }

    public MenuContentDao(ConnectionSource connectionSource, Class<MenuContent> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public MenuContentDao(ConnectionSource connectionSource, DatabaseTableConfig<MenuContent> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
