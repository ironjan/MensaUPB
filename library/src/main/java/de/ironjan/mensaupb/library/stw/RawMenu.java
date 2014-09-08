package de.ironjan.mensaupb.library.stw;

import android.provider.*;

import com.fasterxml.jackson.annotation.*;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

import java.text.*;
import java.util.*;

/**
 * A class representing a raw menu with all possible information
 */
@DatabaseTable(tableName = RawMenu.TABLE)
public class RawMenu {
    public static final String TABLE = "menus";
    public static final String PSEUDO_HASH = "pseudoHash";
    public static final String NAME_GERMAN = "name_de";
    public static final String CATEGORY = "category_de";
    public static final String STUDENTS_PRICE = "priceStudents";
    public static final String PRICE_TYPE = "pricetype";
    public static final String DATE = "date";
    public static final String RESTAURANT = "restaurant";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    @DatabaseField(generatedId = true,columnName = BaseColumns._ID)
    long _id;
    @DatabaseField
    int order_info;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @DatabaseField(canBeNull = false,columnName = DATE, dataType = DataType.DATE_STRING, format = DATE_FORMAT)
    private Date date;
    @DatabaseField(canBeNull = false,columnName = "name_de")
    private String name_de;
    @DatabaseField(canBeNull = false)
    private String name_en;
    @DatabaseField
    private String description_de;
    @DatabaseField
    private String description_en;
    @DatabaseField
    private String category_de;
    @DatabaseField
    private String category_en;
    @DatabaseField
    private String subcategory_de;
    @DatabaseField
    private String subcategory_en;
    @DatabaseField(canBeNull = false,columnName = STUDENTS_PRICE)
    private double priceStudents;
    @DatabaseField(canBeNull = false)
    private double priceWorkers;
    @DatabaseField(canBeNull = false)
    private double priceGuests;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private NewAllergen[] allergens;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Badge[] badges;
    @DatabaseField(columnName = RESTAURANT)
    private String restaurant;
    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE,columnName = PRICE_TYPE)
    private PriceType pricetype;
    @DatabaseField
    private String image;
    @DatabaseField
    private String thumbnail;
    @DatabaseField(canBeNull = false, columnName = PSEUDO_HASH)
    private long pseudoHash;

    public RawMenu() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        updatePseudoHash();
    }

    public String getName_de() {
        return name_de;
    }


    public void setName_de(String name_de) {
        this.name_de = name_de;
        updatePseudoHash();
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

    public double getPriceStudents() {
        return priceStudents;
    }

    public void setPriceStudents(double priceStudents) {
        this.priceStudents = priceStudents;
    }

    public double getPriceWorkers() {
        return priceWorkers;
    }

    public void setPriceWorkers(double priceWorkers) {
        this.priceWorkers = priceWorkers;
    }

    public double getPriceGuests() {
        return priceGuests;
    }

    public void setPriceGuests(double priceGuests) {
        this.priceGuests = priceGuests;
    }

    public NewAllergen[] getAllergens() {
        return allergens;
    }

    public void setAllergens(NewAllergen[] allergens) {
        this.allergens = allergens;
    }

    public int getOrder_info() {
        return order_info;
    }

    public void setOrder_info(int order_info) {
        this.order_info = order_info;
    }

    public Badge[] getBadges() {
        return badges;
    }

    public void setBadges(Badge[] badges) {
        this.badges = badges;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
        updatePseudoHash();
    }

    public PriceType getPricetype() {
        return pricetype;
    }

    public void setPricetype(PriceType pricetype) {
        this.pricetype = pricetype;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    private void updatePseudoHash() {
        // using some arbitrary positive non-zero ints as base
        int dateHash = (date != null) ? date.hashCode() : 3;
        int nameHash = (name_de != null) ? name_de.hashCode() : 5;
        int restaurantHash = (restaurant != null) ? restaurant.hashCode() : 7;
        long pseudoHash = 17;
        pseudoHash = 31 * pseudoHash + dateHash;
        pseudoHash = 31 * pseudoHash + nameHash;
        pseudoHash = 31 * pseudoHash + restaurantHash;
        this.pseudoHash = pseudoHash;
    }

    @Override
    public String toString() {
        return "RawMenu{" +
                "date=" + date +
                ", name_de='" + name_de + '\'' +
                ", restaurant='" + restaurant + '\'' +
                ", pseudoHash=" + pseudoHash +
                ", name_en='" + name_en + '\'' +
                ", description_de='" + description_de + '\'' +
                ", description_en='" + description_en + '\'' +
                ", category_de='" + category_de + '\'' +
                ", category_en='" + category_en + '\'' +
                ", subcategory_de='" + subcategory_de + '\'' +
                ", subcategory_en='" + subcategory_en + '\'' +
                ", priceStudents=" + priceStudents +
                ", priceWorkers=" + priceWorkers +
                ", priceGuests=" + priceGuests +
                ", allergens=" + Arrays.toString(allergens) +
                ", order_info=" + order_info +
                ", badges=" + Arrays.toString(badges) +
                ", pricetype=" + pricetype +
                ", image='" + image + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

    public long getPseudoHash() {
        return pseudoHash;
    }
}
