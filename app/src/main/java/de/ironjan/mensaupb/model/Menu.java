package de.ironjan.mensaupb.model;


import java.util.Optional;

public class Menu {
    String date;
    String name_de;
    String name_en;
    String description_de;
    String description_en;
    String category;
    String category_de;
    String category_en;
    String subcategory_de;
    String subcategory_en;
    String restaurant;
    String pricetype;
    String image;
    String key;
    Double priceStudents;
    Double priceWorkers;
    Double priceGuests;
    String[] allergens;
    String[] badges;
    Integer order_info;
    NutritionalInfo nutritionalInfo;

    public Menu() {
    }

    public String getDate() {

        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName_de() {
        return this.name_de;
    }

    public void setName_de(String name_de) {
        this.name_de = name_de;
    }

    public String getName_en() {
        return this.name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getDescription_de() {
        return this.description_de;
    }

    public void setDescription_de(String description_de) {
        this.description_de = description_de;
    }

    public String getDescription_en() {
        return this.description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_de() {
        return this.category_de;
    }

    public void setCategory_de(String category_de) {
        this.category_de = category_de;
    }

    public String getCategory_en() {
        return this.category_en;
    }

    public void setCategory_en(String category_en) {
        this.category_en = category_en;
    }

    public String getSubcategory_de() {
        return this.subcategory_de;
    }

    public void setSubcategory_de(String subcategory_de) {
        this.subcategory_de = subcategory_de;
    }

    public String getSubcategory_en() {
        return this.subcategory_en;
    }

    public void setSubcategory_en(String subcategory_en) {
        this.subcategory_en = subcategory_en;
    }

    public String getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getPricetype() {
        return this.pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getPriceStudents() {
        return this.priceStudents;
    }

    public void setPriceStudents(Double priceStudents) {
        this.priceStudents = priceStudents;
    }

    public Double getPriceWorkers() {
        return this.priceWorkers;
    }

    public void setPriceWorkers(Double priceWorkers) {
        this.priceWorkers = priceWorkers;
    }

    public Double getPriceGuests() {
        return this.priceGuests;
    }

    public void setPriceGuests(Double priceGuests) {
        this.priceGuests = priceGuests;
    }

    public String[] getAllergens() {
        return this.allergens;
    }

    public void setAllergens(String[] allergens) {
        this.allergens = allergens;
    }

    public String[] getBadges() {
        return this.badges;
    }

    public void setBadges(String[] badges) {
        this.badges = badges;
    }

    public Integer getOrder_info() {
        return this.order_info;
    }

    public void setOrder_info(Integer order_info) {
        this.order_info = order_info;
    }

    public NutritionalInfo getNutritionalInfo() {
        return this.nutritionalInfo;
    }

    public void setNutritionalInfo(NutritionalInfo nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }

    public boolean hasNutritionalInfo(){
        return nutritionalInfo != null;
    }
}
