package de.najidev.mensaupb.persistence;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

import java.sql.*;

import de.najidev.mensaupb.rest.*;

/**
 * Created by ljan on 11.01.14.
 */
public class RestaurantDao extends BaseDaoImpl<Restaurant,Long> {
    protected RestaurantDao(Class<Restaurant> dataClass) throws SQLException {
        super(dataClass);
    }

    protected RestaurantDao(ConnectionSource connectionSource, Class<Restaurant> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

}
