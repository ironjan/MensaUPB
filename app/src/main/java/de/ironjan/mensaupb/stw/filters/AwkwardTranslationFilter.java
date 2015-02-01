package de.ironjan.mensaupb.stw.filters;

import java.util.*;

import de.ironjan.mensaupb.stw.*;

/**
 * A filter to replace an awkward translation
 */
public class AwkwardTranslationFilter implements Filter {

    public static final String BAD_TRANSLATION = "Default Menu";
    public static final String BETTER_TRANSLATION = "Recommendations";

    @Override
    public List<RawMenu> filter(List<RawMenu> menus) {
        List<RawMenu> filteredMenus = new ArrayList<>(menus.size());
        for (RawMenu menu : menus) {
            RawMenu cleanedMenu = filter(menu);
            filteredMenus.add(cleanedMenu);
        }
        return filteredMenus;
    }

    private RawMenu filter(RawMenu menu) {
        RawMenu filteredMenu = menu.copy();
        String category_en = menu.getCategory_en();
        category_en = category_en.replaceAll(BAD_TRANSLATION, BETTER_TRANSLATION);
        filteredMenu.setCategory_en(category_en);
        return filteredMenu;
    }
}
