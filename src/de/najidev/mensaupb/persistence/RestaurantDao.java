package de.najidev.mensaupb.persistence;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

import java.sql.*;

/**
 * Created by ljan on 11.01.14.
 */
public class RestaurantDao extends BaseDaoImpl<RestaurantDao,Long> {
    protected RestaurantDao(Class<RestaurantDao> dataClass) throws SQLException {
        super(dataClass);
    }

    protected RestaurantDao(ConnectionSource connectionSource, Class<RestaurantDao> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected RestaurantDao(ConnectionSource connectionSource, DatabaseTableConfig<RestaurantDao> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
