package de.ironjan.mensaupb.stw.rest_api;

/**
 * Mock implementation of StwRest to test without a secret STW url
 */
public class MockRestWrapper implements StwRest {
    private static final Allergen[] NO_ALLERGENS = new Allergen[0];
    private static MockRestWrapper instance;

    private final Allergen[] ALL_ALLERGENS = {
            Allergen.COLORED,
            Allergen.CONSERVED,
            Allergen.ANTIOXIDANTS,
            Allergen.FLAVOR_ENHANCERS,
            Allergen.PHOSPHAT,
            Allergen.SULFURATED,
            Allergen.WAXED,
            Allergen.BLACKENED,
            Allergen.SWEETENER,
            Allergen.PHENYLALANINE,
            Allergen.TAURINE,
            Allergen.NITRATE_SALT,
            Allergen.COFFEINE,
            Allergen.QUININE,
            Allergen.LACTOPROTEIN,
            Allergen.CRUSTACEAN,
            Allergen.EGGS,
            Allergen.FISH,
            Allergen.SOYA,
            Allergen.LACTOSE,
            Allergen.NUTS,
            Allergen.CELERIAC,
            Allergen.MUSTARD,
            Allergen.SESAME,
            Allergen.SULFITES,
            Allergen.MOLLUSKS,
            Allergen.LUPINE,
            Allergen.GLUTEN,
            Allergen.PEANUTS
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
    public StwMenu[] getMenus(String restaurant, String date) {
        return constructMockMenus(restaurant, date);
    }

    private StwMenu[] constructMockMenus(String restaurant, String date) {
        StwMenu wok = buildRawMenu(restaurant, date, "weighted wok allergens", "weighted test food for wok with all allergens", "wok", ALL_ALLERGENS, PriceType.WEIGHT);
        StwMenu pasta = buildRawMenu(restaurant, date, "weighted pasta no Allergens", "weighted test food for pasta with no allergens", "pasta", NO_ALLERGENS, PriceType.WEIGHT);
        StwMenu defaultDish = buildRawMenu(restaurant, date, "fixed default no Allergens", "fixed test default food with no allergens", "default", NO_ALLERGENS, PriceType.FIXED);
        StwMenu soup = buildRawMenu(restaurant, date, "fixed soup no Allergens", "fixed test soup with no allergens", "soup", NO_ALLERGENS, PriceType.FIXED);
        StwMenu sidedish = buildRawMenu(restaurant, date, "fixed sidedish no Allergens", "fixed test sidedish with no allergens", "sidedish", NO_ALLERGENS, PriceType.FIXED);
        StwMenu dessert = buildRawMenu(restaurant, date, "fixed dessert no Allergens", "fixed test dessert with no allergens", "dessert", NO_ALLERGENS, PriceType.FIXED);
        StwMenu counterdessert = buildRawMenu(restaurant, date, "fixed counter dessert no Allergens", "fixed counter dessert default food with no allergens", "counter dessert", NO_ALLERGENS, PriceType.FIXED);
        StwMenu grill = buildRawMenu(restaurant, date, "fixed grill no Allergens", "fixed test grill food with no allergens", "grill", NO_ALLERGENS, PriceType.FIXED);
        return new StwMenu[]{wok, pasta, defaultDish, soup, sidedish, dessert, counterdessert, grill};
    }

    @SuppressWarnings("MagicNumber")
    private StwMenu buildRawMenu(String restaurant, String date, String name, String description, String category, Allergen[] allergens, PriceType pricetype) {
        StwMenu menu = new StwMenu();

        menu.setName_de(name);
        menu.setName_en(name);
        menu.setDate(date);
        menu.setDescription_de(description);
        menu.setDescription_en(description);
        menu.setPricetype(pricetype);
        menu.setPriceStudents(1.00);
        menu.setPriceWorkers(12.00);
        menu.setPriceGuests(100.00);
        menu.setCategory_de(category);
        menu.setCategory_en(category);
        menu.setRestaurant(restaurant);
        menu.setAllergens(allergens);

        return menu;
    }
}
