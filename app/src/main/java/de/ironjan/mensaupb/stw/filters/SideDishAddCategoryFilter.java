package de.ironjan.mensaupb.stw.filters;

import org.slf4j.LoggerFactory;

import java.util.Vector;

import de.ironjan.mensaupb.stw.rest_api.Allergen;
import de.ironjan.mensaupb.stw.rest_api.PriceType;
import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * Filters allergens
 */
public class SideDishAddCategoryFilter extends FilterBase {
    @Override
    public StwMenu filter(StwMenu menu) {

        StwMenu filteredMenu = menu.copy();
        if(isMostLikelySideDish(menu)){
            filteredMenu.setCategoryIdentifier(SortingFilter.SIDEDISH);
        }
        return filteredMenu;
    }

    private boolean isMostLikelySideDish(StwMenu menu) {
        String categoryIdentifier = menu.getCategoryIdentifier();
        boolean isNoDessert = !SortingFilter.DESSERT.equals(categoryIdentifier);
        boolean isTooCheapForMainDish = menu.getPriceStudents() < 1;
        boolean isNotWeighted = menu.getPricetype() == PriceType.FIXED;
        LoggerFactory.getLogger(SideDishAddCategoryFilter.class).warn(String.format("cat: %s, %s && %s && %s - menu: %s",
                categoryIdentifier, isNoDessert, isTooCheapForMainDish, isNotWeighted, menu.getName_de()));
        return isNoDessert && isTooCheapForMainDish && isNotWeighted;
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
