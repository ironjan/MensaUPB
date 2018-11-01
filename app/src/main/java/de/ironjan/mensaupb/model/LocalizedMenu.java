package de.ironjan.mensaupb.model;

public class LocalizedMenu {
    private String date;
    private String name;
    private String description;
    private String category;
    private String categoryIdentifier;
    private String subcategory;
    private String restaurant;
    private String pricetype;
    private String image;
    private String key;
    private Double priceStudents;
    private Double priceWorkers;
    private Double priceGuests;
    private String[] allergens;
    private String[] badges;
    private Integer order_info;
    private NutritionalInfo nutritionalInfo;

    public LocalizedMenu(de.ironjan.mensaupb.api.model.Menu menu, boolean isEnglish) {
        date = menu.getDate();

        if (isEnglish) {
            name = menu.getName_en();
            description = menu.getDescription_en();
            category = menu.getCategory_en();
            subcategory = menu.getSubcategory_en();
        } else {
            name = menu.getName_de();
            description = menu.getDescription_de();
            category = menu.getCategory_de();
            subcategory = menu.getSubcategory_de();
        }
        categoryIdentifier = menu.getCategory();
        restaurant = menu.getRestaurant();
        pricetype = menu.getPricetype();
        image = menu.getImage();
        key = menu.getKey();
        priceStudents = menu.getPriceStudents();
        priceWorkers = menu.getPriceWorkers();
        priceGuests = menu.getPriceGuests();
        allergens = menu.getAllergens();
        badges = menu.getBadges();
        order_info = menu.getOrder_info();
        // FIXME nutritionalInfo = menu.getNutritionalInfo();
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getPricetype() {
        return pricetype;
    }

    public String getImage() {
        return image;
    }

    public String getKey() {
        return key;
    }

    public Double getPriceStudents() {
        return priceStudents;
    }

    public Double getPriceWorkers() {
        return priceWorkers;
    }

    public Double getPriceGuests() {
        return priceGuests;
    }

    public String[] getAllergens() {
        return allergens;
    }

    public String[] getBadges() {
        return badges;
    }

    public Integer getOrder_info() {
        return order_info;
    }

    public NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }

    public Double getPrice() {
        return getPriceStudents();
    }

    public String getCategoryIdentifier() {
        return categoryIdentifier;
    }

    public int getSortOrder() {
        switch (getCategoryIdentifier()) {
            case "dish-default":
                return 0;
            case "soups":
                return 0;
            case "dish-grill":
                return 0;
            case "dish-pasta":
                return 0;
            case "dish-wok":
                return 0;
            case "sidedish":
                return 35;
            case "dessert":
                return 70;
            case "dessert-counter":
                return 70;
            case "happydinner":
                return 5;
            case "classics-evening":
                return 35;
            case "snacks":
                return 50;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return "LocalizedMenu{" +
                "date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", categoryIdentifier='" + categoryIdentifier + '\'' +
                ", restaurant='" + restaurant + '\'' +
                ", pricetype='" + pricetype + '\'' +
                ", priceStudents=" + priceStudents +
                '}';
    }
}
