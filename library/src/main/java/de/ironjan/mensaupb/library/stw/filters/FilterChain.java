package de.ironjan.mensaupb.library.stw.filters;

import org.slf4j.*;

import java.util.*;

import de.ironjan.mensaupb.library.stw.*;

/**
 * Created by ljan on 13.12.14.
 */
public class FilterChain implements Filter {
    Filter[] filters = {
            new NameFilter(),
            new CategoryFilter()
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterChain.class);

    @Override
    public List<RawMenu> filter(List<RawMenu> menus) {
        List<RawMenu> filteredMenus = new ArrayList<>(menus);
        for (Filter filter : filters) {
            LOGGER.debug("Filtering with {}", filter.getClass());
            filteredMenus = filter.filter(filteredMenus);
        }
        return filteredMenus;
    }
}
