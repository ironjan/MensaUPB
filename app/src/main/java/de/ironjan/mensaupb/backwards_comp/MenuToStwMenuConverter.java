package de.ironjan.mensaupb.backwards_comp;

import de.ironjan.mensaupb.model.Menu;
import de.ironjan.mensaupb.stw.rest_api.Allergen;
import de.ironjan.mensaupb.stw.rest_api.Badge;
import de.ironjan.mensaupb.stw.rest_api.PriceType;
import de.ironjan.mensaupb.stw.rest_api.StwMenu;

public class MenuToStwMenuConverter {
    public static StwMenu convert(Menu menu) {
        StwMenu stwMenu = new StwMenu();
        stwMenu.setDate(menu.getDate());
        stwMenu.setName_de(menu.getName_de());
        stwMenu.setName_en(menu.getName_en());
        stwMenu.setDescription_de(menu.getDescription_de());
        stwMenu.setDescription_en(menu.getDescription_en());
        stwMenu.setCategory_de(menu.getCategory_de());
        stwMenu.setCategory_en(menu.getCategory_en());
        stwMenu.setSubcategory_de(menu.getSubcategory_de());
        stwMenu.setSubcategory_en(menu.getSubcategory_en());
        stwMenu.setRestaurant(menu.getRestaurant());
        stwMenu.setPricetype(PriceType.fromString(menu.getPricetype()));
        stwMenu.setPriceStudents(menu.getPriceStudents());
        stwMenu.setPriceWorkers(menu.getPriceWorkers());
        stwMenu.setPriceGuests(menu.getPriceGuests());
        stwMenu.setAllergens(convertAllergens(menu.getAllergens()));
        stwMenu.setBadges(convertBadges(menu.getBadges()));
        stwMenu.setOrder_info(menu.getOrder_info());
        stwMenu.setImage(menu.getImage());
        return stwMenu;
    }

    private static Allergen[] convertAllergens(String[] a) {
        Allergen[] b = new Allergen[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = Allergen.fromString(a[i]);
        }
        return b;
    }

    private static Badge[] convertBadges(String[] a) {
        Badge[] b = new Badge[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = Badge.fromString(a[i]);
        }
        return b;
    }
}
