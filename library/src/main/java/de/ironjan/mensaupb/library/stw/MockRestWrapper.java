package de.ironjan.mensaupb.library.stw;

/**
 * Created by ljan on 21.11.14.
 */
public class MockRestWrapper implements StwRest {
    private static final NewAllergen[] NO_ALLERGENS = new NewAllergen[0];
    private static MockRestWrapper instance;

    NewAllergen[] ALL_ALLERGENS = {
            NewAllergen.COLORED,
            NewAllergen.CONSERVED,
            NewAllergen.ANTIOXIDANTS,
            NewAllergen.FLAVOR_ENHANCERS,
            NewAllergen.PHOSPHAT,
            NewAllergen.SULFURATED,
            NewAllergen.WAXED,
            NewAllergen.BLACKENED,
            NewAllergen.SWEETENER,
            NewAllergen.PHENYLALANINE,
            NewAllergen.TAURINE,
            NewAllergen.NITRATE_SALT,
            NewAllergen.COFFEINE,
            NewAllergen.QUININE,
            NewAllergen.LACTOPROTEIN,
            NewAllergen.CRUSTACEAN,
            NewAllergen.EGGS,
            NewAllergen.FISH,
            NewAllergen.SOYA,
            NewAllergen.LACTOSE,
            NewAllergen.NUTS,
            NewAllergen.CELERIAC,
            NewAllergen.MUSTARD,
            NewAllergen.SESAME,
            NewAllergen.SULFITES,
            NewAllergen.MOLLUSKS,
            NewAllergen.LUPINE,
            NewAllergen.GLUTEN,
            NewAllergen.PEANUTS
    };

    private MockRestWrapper() {
    }

    public static MockRestWrapper getInstance() {
        if (instance == null) {
            instance = new MockRestWrapper();
        }
        return instance;
    }

    @Override
    public RawMenu[] getMenus(String restaurant, String date) {
        return constructMockMenus(restaurant, date);
    }

    private RawMenu[] constructMockMenus(String restaurant, String date) {
        RawMenu wok = buildRawMenu(restaurant, date, "weighted wok allergens", "weighted test food for wok with all allergens", SortOrder.DISH_WOK, "wok", ALL_ALLERGENS, PriceType.WEIGHT);
        RawMenu pasta = buildRawMenu(restaurant, date, "weighted pasta no Allergens", "weighted test food for pasta with no allergens", SortOrder.DISH_PASTA, "pasta", NO_ALLERGENS, PriceType.WEIGHT);
        RawMenu defaultDish = buildRawMenu(restaurant, date, "fixed default no Allergens", "fixed test default food with no allergens", SortOrder.DISH_DEFAULT, "default", NO_ALLERGENS, PriceType.FIXED);
        RawMenu soup = buildRawMenu(restaurant, date, "fixed soup no Allergens", "fixed test soup with no allergens", SortOrder.SOUPS, "soup", NO_ALLERGENS, PriceType.FIXED);
        RawMenu sidedish = buildRawMenu(restaurant, date, "fixed sidedish no Allergens", "fixed test sidedish with no allergens", SortOrder.SIDEDISH, "sidedish", NO_ALLERGENS, PriceType.FIXED);
        RawMenu dessert = buildRawMenu(restaurant, date, "fixed dessert no Allergens", "fixed test dessert with no allergens", SortOrder.DESSERT, "dessert", NO_ALLERGENS, PriceType.FIXED);
        RawMenu counterdessert = buildRawMenu(restaurant, date, "fixed counter dessert no Allergens", "fixed counter dessert default food with no allergens", SortOrder.DESSERT_COUNTER, "counter dessert", NO_ALLERGENS, PriceType.FIXED);
        RawMenu grill = buildRawMenu(restaurant, date, "fixed grill no Allergens", "fixed test grill food with no allergens", SortOrder.DISH_GRILL, "grill", NO_ALLERGENS, PriceType.FIXED);
        return new RawMenu[]{wok, pasta, defaultDish, soup, sidedish, dessert, counterdessert, grill};
    }

    private RawMenu buildRawMenu(String restaurant, String date, String name, String description, String categoryIdentifier, String category, NewAllergen[] allergens, PriceType pricetype) {
        RawMenu menu = new RawMenu();

        menu.setName_de(name);
        menu.setName_en(name);
        menu.setDate(date);
        menu.setDescription_de(description);
        menu.setDescription_en(description);
        menu.setPricetype(pricetype);
        menu.setPriceStudents(1.00);
        menu.setPriceWorkers(12.00);
        menu.setPriceGuests(100.00);
        menu.setCategoryIdentifier(categoryIdentifier);
        menu.setCategory_de(category);
        menu.setCategory_en(category);
        menu.setRestaurant(restaurant);
        menu.setAllergens(allergens);

        return menu;
    }
}
