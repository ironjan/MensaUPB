package de.ironjan.mensaupb.stw.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import de.ironjan.mensaupb.stw.rest_api.RawMenu;

/**
 * A filter chain, invoking all filters.
 */
public class FilterChain implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterChain.class);
    private static final Filter[] filters = {
            new NameFilter(),
            new CategoryFilter(),
            new AllergenFilter(),
            new AwkwardTranslationFilter(),
            new SortingFilter()
    };

    @Override
    public List<RawMenu> filter(List<RawMenu> menus) {
        List<RawMenu> filteredMenus = new ArrayList<>(menus);
        for (Filter filter : filters) {
            LOGGER.debug("Filtering with {}", filter.getClass());
            filteredMenus = filter.filter(filteredMenus);
        }
        return filteredMenus;
    }

    @Override
    public RawMenu filter(RawMenu menu) {
        RawMenu filteredMenu = menu;
        for (Filter filter : filters) {
            LOGGER.debug("Filtering with {}", filter.getClass());
            filteredMenu = filter.filter(filteredMenu);
        }
        return filteredMenu;
    }
}
