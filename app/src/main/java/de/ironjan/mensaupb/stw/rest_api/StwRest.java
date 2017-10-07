package de.ironjan.mensaupb.stw.rest_api;

import org.androidannotations.rest.spring.annotations.Accept;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.api.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.androidannotations.rest.spring.annotations.Rest;

import de.ironjan.mensaupb.BuildConfig;

/**
 * Do not use this class directly, use StwRestWrapper instead!
 * Rest-API of the Studentenwerk Paderborn.
 * BuildConfig.STW_URL is a secret URL that must not be shared because of "quality assurance"
 */
@Rest(converters = MappingJackson2HttpMessageConverter.class, rootUrl = BuildConfig.STW_URL)
interface StwRest {
    @Get("&restaurant={restaurant}&date={date}")
    @Accept(MediaType.APPLICATION_JSON)
    StwMenu[] getMenus(@Path String restaurant, @Path String date);
}
