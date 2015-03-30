package de.ironjan.mensaupb.stw.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

import de.ironjan.mensaupb.stw.rest_api.NewAllergen;
import de.ironjan.mensaupb.stw.rest_api.RawMenu;

/**
 * Filters allergens
 */
public class AllergenFilter extends FilterBase {
    Logger LOGGER = LoggerFactory.getLogger(AllergenFilter.class);

    @Override
    public RawMenu filter(RawMenu menu) {
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
