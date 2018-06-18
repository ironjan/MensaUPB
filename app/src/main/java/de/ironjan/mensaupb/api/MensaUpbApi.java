package de.ironjan.mensaupb.api;

import java.util.List;

import de.ironjan.mensaupb.model.Menu;
import de.ironjan.mensaupb.model.Restaurant;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MensaUpbApi {
    @GET("restaurants")
    Observable<List<Restaurant>> getRestaurants();

    @GET("menus")
    Observable<List<Menu>> getMenus(@Query("restaurant") String restaurant, @Query("date") String date);
}
