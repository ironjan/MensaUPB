package de.ironjan.mensaupb.stw.filters;

import java.util.Vector;

import de.ironjan.mensaupb.stw.rest_api.Allergen;
import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * Filters allergens
 */
public class AllergenCleaner implements Cleaner {
    @Override
    public StwMenu clean(StwMenu menu) {
        Allergen[] allergens = menu.getAllergens();
        Allergen[] filteredAllergens = filter(allergens);
        StwMenu filteredMenu = menu.copy();
        filteredMenu.setAllergens(filteredAllergens);
        return filteredMenu;
    }

    private Allergen[] filter(Allergen[] allergens) {
        Vector<Allergen> filteredAllergensAsVector = new Vector<>(allergens.length);
        for (Allergen allergen : allergens) {
            if (allergen != Allergen.UNKNOWN) {
                filteredAllergensAsVector.add(allergen);
            }
        }
        Allergen[] filteredAllergens = new Allergen[filteredAllergensAsVector.size()];
        filteredAllergensAsVector.copyInto(filteredAllergens);

        return filteredAllergens;
    }
}
