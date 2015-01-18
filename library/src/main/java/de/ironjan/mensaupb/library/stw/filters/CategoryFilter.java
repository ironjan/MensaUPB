package de.ironjan.mensaupb.library.stw.filters;

import android.text.*;

import org.slf4j.*;

import java.util.*;

import de.ironjan.mensaupb.library.stw.*;

/**
 * Filters the categories
 */
class CategoryFilter implements Filter {
    Logger LOGGER = LoggerFactory.getLogger(CategoryFilter.class);

    @Override
    public List<RawMenu> filter(List<RawMenu> menus) {
        LOGGER.debug("filter(list)");
        List<RawMenu> filteredMenus = new ArrayList<>(menus.size());
        for (RawMenu menu : menus) {
            RawMenu cleanedMenu = cleanCategories(menu);
            filteredMenus.add(cleanedMenu);
        }
        LOGGER.debug("filter(list) done");
        return filteredMenus;
    }

    private RawMenu cleanCategories(RawMenu menu) {
        LOGGER.debug("cleanCategories({})", menu);
        RawMenu copy = menu.copy();
        updateDeCategory(copy);
        updateEnCategory(copy);
        LOGGER.debug("{} <- cleanCategories({}) done", copy, menu);
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
