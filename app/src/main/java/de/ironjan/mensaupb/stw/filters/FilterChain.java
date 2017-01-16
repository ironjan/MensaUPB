package de.ironjan.mensaupb.stw.filters;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;

import java.util.List;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * A cleaner chain, invoking all cleaners.
 */
public class FilterChain  {
    Function<StwMenu, StwMenu> cleanName = (new NameCleaner())::clean;
    Function<StwMenu, StwMenu> cleanCategory = (new CategoryCleaner())::clean;
    Function<StwMenu, StwMenu> cleanAllergens = (new AllergenCleaner())::clean;
    Function<StwMenu, StwMenu> cleanAwkwardTranslations = (new AwkwardTranslationCleaner())::clean;
    Function<StwMenu, StwMenu> fixSorting = (new SortingCleaner())::clean;

    public List<StwMenu> filter(List<StwMenu> menus) {
        return Stream.of(menus)
                .map(cleanName)
                .map(cleanCategory)
                .map(cleanAllergens)
                .map(cleanAwkwardTranslations)
                .map(fixSorting)
                .collect(Collectors.toList());
    }

}
