package de.ironjan.mensaupb.stw;

import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.*;

import java.util.*;

/**
 * Bean to get restaurant names via ids
 */
@EBean(scope = EBean.Scope.Singleton)
public class RestaurantHelper {
    @StringArrayRes
    String[] restaurants, displayedRestaurants;

    HashMap<String, String> restaurantIdToName = new HashMap<>(0);

    @AfterInject
    void buildHashMap() {
        restaurantIdToName = new HashMap<>(restaurants.length);

        for (int i = 0; i < restaurants.length; i++) {
            String id = restaurants[i];
            String name = displayedRestaurants[i];
            restaurantIdToName.put(id, name);
        }
    }

    public String getNameFor(String id) {
        return restaurantIdToName.get(id);
    }
}
