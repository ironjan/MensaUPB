package de.ironjan.mensaupb.stw.filters;

import android.text.*;

import org.slf4j.*;

import de.ironjan.mensaupb.stw.*;

/**
 * Filters the categories
 */
class CategoryFilter extends FilterBase {
    Logger LOGGER = LoggerFactory.getLogger(CategoryFilter.class);

    @Override
    public RawMenu filter(RawMenu menu) {
        LOGGER.debug("filter({})", menu);
        RawMenu copy = menu.copy();
        updateDeCategory(copy);
        updateEnCategory(copy);
        LOGGER.debug("{} <- filter({}) done", copy, menu);
        return copy;
    }

    private void updateDeCategory(RawMenu menu) {
        String subcategory_de = menu.getSubcategory_de();
        if (subcategory_de != null && TextUtils.isEmpty(subcategory_de.trim())) {
            return;
        }
        menu.setCategory_de(subcategory_de);
    }

    private void updateEnCategory(RawMenu menu) {
        String subcategory_en = menu.getSubcategory_en();
        if (subcategory_en != null && TextUtils.isEmpty(subcategory_en.trim())) {
            return;
        }
        menu.setCategory_en(subcategory_en);
    }
}
