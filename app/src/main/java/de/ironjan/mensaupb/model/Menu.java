package de.ironjan.mensaupb.model;


import com.google.gson.annotations.SerializedName;

public class Menu {
    private String date;
    private String name_de;
    private String name_en;
    private String description_de;
    private String description_en;
    @SerializedName("category")
    private String categoryIdentifier;
    private String category_de;
    private String category_en;
    private String subcategory_de;
    private String subcategory_en;
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

    public Menu() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName_de() {
        return name_de;
    }

    public void setName_de(String name_de) {
        this.name_de = name_de;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getDescription_de() {
        return description_de;
    }

    public void setDescription_de(String description_de) {
        this.description_de = description_de;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    public String getCategory_de() {
        return category_de;
    }

    public void setCategory_de(String category_de) {
        this.category_de = category_de;
    }

    public String getCategory_en() {
        return category_en;
    }

    public void setCategory_en(String category_en) {
        this.category_en = category_en;
    }

    public String getSubcategory_de() {
        return subcategory_de;
    }

    public void setSubcategory_de(String subcategory_de) {
        this.subcategory_de = subcategory_de;
    }

    public String getSubcategory_en() {
        return subcategory_en;
    }

    public void setSubcategory_en(String subcategory_en) {
        this.subcategory_en = subcategory_en;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getPriceStudents() {
        return priceStudents;
    }

    public void setPriceStudents(Double priceStudents) {
        this.priceStudents = priceStudents;
    }

    public Double getPriceWorkers() {
        return priceWorkers;
    }

    public void setPriceWorkers(Double priceWorkers) {
        this.priceWorkers = priceWorkers;
    }

    public Double getPriceGuests() {
        return priceGuests;
    }

    public void setPriceGuests(Double priceGuests) {
        this.priceGuests = priceGuests;
    }

    public String[] getAllergens() {
        return allergens;
    }

    public void setAllergens(String[] allergens) {
        this.allergens = allergens;
    }

    public String[] getBadges() {
        return badges;
    }

    public void setBadges(String[] badges) {
        this.badges = badges;
    }

    public Integer getOrder_info() {
        return order_info;
    }

    public void setOrder_info(Integer order_info) {
        this.order_info = order_info;
    }

    NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }

    public void setNutritionalInfo(NutritionalInfo nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }

    public boolean hasNutritionalInfo(){
        return nutritionalInfo != null;
    }

    public String getCategoryIdentifier() {
        return this.categoryIdentifier;
    }

    public void setCategoryIdentifier(String categoryIdentifier) {
        this.categoryIdentifier = categoryIdentifier;
    }
}
