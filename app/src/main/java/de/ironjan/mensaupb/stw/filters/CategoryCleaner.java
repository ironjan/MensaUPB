package de.ironjan.mensaupb.stw.filters;

import android.text.TextUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * Cleans the categories. Replaces "category" with "subcategory" if the latter is non-empty.
 */
class CategoryCleaner implements Cleaner {
    Logger LOGGER = LoggerFactory.getLogger(CategoryCleaner.class);

    @Override
    public StwMenu clean(StwMenu menu) {
        StwMenu copy = menu.copy();
        updateDeCategory(copy);
        updateEnCategory(copy);
        return copy;
    }

    private void updateDeCategory(StwMenu menu) {
        String subcategory_de = menu.getSubcategory_de();
        subcategory_de = (subcategory_de != null) ? subcategory_de.trim() : subcategory_de;
        if (TextUtils.isEmpty(subcategory_de)) {
            return;
        }
        menu.setCategory_de(subcategory_de);
    }

    private void updateEnCategory(StwMenu menu) {
        String subcategory_en = menu.getSubcategory_en();
        subcategory_en = (subcategory_en != null) ? subcategory_en.trim() : subcategory_en;
        if (TextUtils.isEmpty(subcategory_en)) {
            return;
        }
        menu.setCategory_en(subcategory_en);
    }
}
