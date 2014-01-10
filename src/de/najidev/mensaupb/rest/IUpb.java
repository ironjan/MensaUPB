package de.najidev.mensaupb.rest;

import org.androidannotations.annotations.rest.*;
import org.springframework.http.converter.json.*;

/**
 * i-upb-REST API
 */
@Rest(rootUrl = "http://www.i-upb.de/api/v1/", converters =  MappingJackson2HttpMessageConverter.class)
public interface IUpb {

    @Get("/restaurants.json")
    public Restaurant[] getRestaurants();

    @Get("/menus/{restaurantName}")
    public MenuEntry[] getMenus(final String restaurantName);
}
