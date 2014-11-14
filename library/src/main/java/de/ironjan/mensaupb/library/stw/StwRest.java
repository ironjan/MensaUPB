package de.ironjan.mensaupb.library.stw;

import org.androidannotations.annotations.rest.*;
import org.androidannotations.api.rest.*;
import org.springframework.http.converter.json.*;

import de.ironjan.mensaupb.library.*;

/**
 * Rest-API of the Studentenwerk Paderborn.
 * BuildConfig.STW_URL is a secret URL that must not be shared because of "quality assurance"
 */
@Rest(converters = MappingJackson2HttpMessageConverter.class, rootUrl = BuildConfig.STW_URL)
public interface StwRest {
    @Get("&restaurant={restaurant}&date={date}")
    @Accept(MediaType.APPLICATION_JSON)
    RawMenu[] getMenus(String restaurant, String date);
}
