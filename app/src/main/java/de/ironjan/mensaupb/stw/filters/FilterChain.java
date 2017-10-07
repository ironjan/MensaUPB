package de.ironjan.mensaupb.stw.filters;

import java.util.ArrayList;
import java.util.List;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * A filter chain, invoking all filters.
 */
public class FilterChain implements Filter {
    private static final Filter[] filters = {
            new NameFilter(),
            new CategoryFilter(),
            new AllergenFilter(),
            new AwkwardTranslationFilter(),
            new SideDishAddCategoryFilter(),
            new SortingFilter()
    };

    @Override
    public List<StwMenu> filter(List<StwMenu> menus) {
        List<StwMenu> filteredMenus = new ArrayList<>(menus);
        for (Filter filter : filters) {
            filteredMenus = filter.filter(filteredMenus);
        }
        return filteredMenus;
    }

    @Override
    public StwMenu filter(StwMenu menu) {
        StwMenu filteredMenu = menu;
        for (Filter filter : filters) {
            filteredMenu = filter.filter(filteredMenu);
        }
        return filteredMenu;
    }
}
