package de.ironjan.mensaupb.library.stw;

import org.androidannotations.annotations.rest.*;
import org.springframework.http.converter.json.*;

import de.ironjan.mensaupb.library.*;

/**
 * Created by ljan on 01.09.14.
 */
@Rest(converters = MappingJackson2HttpMessageConverter.class, rootUrl = BuildConfig.STW_URL)
public interface StwRest {
    @Get("")
    RawMenu[] getAll();
}
