package de.ironjan.mensaupb.stw.filters;

import org.slf4j.*;

import java.util.*;

import de.ironjan.mensaupb.stw.*;

/**
 * Filters allergens
 */
public class AllergenFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllergenFilter.class);

    @Override
    public List<RawMenu> filter(List<RawMenu> menus) {
        List<RawMenu> filteredMenus = new ArrayList<>(menus.size());
        for (RawMenu menu : menus) {
            RawMenu cleanedMenu = cleanAllergensOf(menu);
            filteredMenus.add(cleanedMenu);
        }

        return filteredMenus;
    }


    private synchronized RawMenu cleanAllergensOf(RawMenu menu) {
        NewAllergen[] allergens = menu.getAllergens();
        NewAllergen[] filteredAllergens = filter(allergens);
        RawMenu filteredMenu = menu.copy();
        filteredMenu.setAllergens(filteredAllergens);
        return filteredMenu;
    }

    private NewAllergen[] filter(NewAllergen[] allergens) {
        Vector<NewAllergen> filteredAllergensAsVector = new Vector<>(allergens.length);
        for (NewAllergen allergen : allergens) {
            if (allergen != NewAllergen.UNKNOWN) {
                filteredAllergensAsVector.add(allergen);
            }
        }
        NewAllergen[] filteredAllergens = new NewAllergen[filteredAllergensAsVector.size()];
        filteredAllergensAsVector.copyInto(filteredAllergens);

        LOGGER.debug("{} <- filter({})", filteredAllergens, allergens);
        return filteredAllergens;
    }
}
