package de.ironjan.mensaupb.library.stw.deprecated;

import android.content.ContentValues;

import de.ironjan.mensaupb.library.stw.deprecated.*;


/**
 * TODO javadoc
 */
@Deprecated
public class MenuToContentValuesConverter {


    /**
     * TODO javadoc
     */
    public static ContentValues convert(Menu menu) {
        ContentValues cv = new ContentValues();

        cv.put(Menu.NAME_GERMAN, menu.getNameGerman());
        cv.put(Menu.NAME_ENGLISH, menu.getNameEnglish());
        cv.put(Menu.DATE, menu.getDate());
        cv.put(Menu.LOCATION, menu.getLocation());
        cv.put(Menu.CATEGORY, menu.getCategory());
        cv.put(Menu.ALLERGENES, menu.getAllergenes());


        return cv;
    }
}
