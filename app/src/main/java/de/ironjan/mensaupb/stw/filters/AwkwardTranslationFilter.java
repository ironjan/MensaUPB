package de.ironjan.mensaupb.stw.filters;

import android.text.*;

import de.ironjan.mensaupb.stw.*;

/**
 * A filter to replace an awkward translation
 */
public class AwkwardTranslationFilter extends FilterBase {

    public static final String BAD_TRANSLATION = "Default Menu";
    public static final String BETTER_TRANSLATION = "Recommendation";

    @Override
    public RawMenu filter(RawMenu menu) {
        RawMenu filteredMenu = menu.copy();
        String category_en = menu.getCategory_en();
        if (TextUtils.isEmpty(category_en)) {
            filteredMenu.setCategory_en("");
        } else {
            category_en = category_en.replaceAll(BAD_TRANSLATION, BETTER_TRANSLATION);
        }
        filteredMenu.setCategory_en(category_en);
        return filteredMenu;
    }
}
