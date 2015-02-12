package de.ironjan.mensaupb.stw;

import android.provider.BaseColumns;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * A class representing a raw menu with all possible information
 */
@DatabaseTable(tableName = RawMenu.TABLE)
public class RawMenu implements Cloneable {
    public static final String TABLE = "menus";
    public static final String NAME_GERMAN = "name_de";
    public static final String CATEGORY_DE = "category_de";
    public static final String STUDENTS_PRICE = "priceStudents";
    public static final String PRICE_TYPE = "pricetype";
    public static final String DATE = "date";
    public static final String RESTAURANT = "restaurant";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String ALLERGENS = "allergens";
    public static final Logger LOGGER = LoggerFactory.getLogger(RawMenu.class);
    public static final String SORT_ORDER = "sortOrder";
    public static final String BADGES = "badges";
    public static final String NAME_EN = "name_en";
    public static final String CATEGORY_EN = "category_en";
    public static final String DESCRIPTION_EN = "description_en";
    public static final String DESCRIPTION_DE = "description_de";

    @DatabaseField(generatedId = true, columnName = BaseColumns._ID)
    long _id;
    @DatabaseField
    int order_info;
    @DatabaseField(canBeNull = false, columnName = DATE, dataType = DataType.DATE_STRING, format = DATE_FORMAT)
    private Date date;
    @DatabaseField(canBeNull = false, columnName = NAME_GERMAN)
    private String name_de;
    @DatabaseField(canBeNull = false, columnName = NAME_EN)
    private String name_en;
    @DatabaseField(columnName = DESCRIPTION_DE)
    private String description_de = "";
    @DatabaseField(columnName = DESCRIPTION_EN)
    private String description_en = "";
    @DatabaseField
    @JsonProperty("category")
    private String categoryIdentifier;
    @DatabaseField(columnName = CATEGORY_DE)
    private String category_de;
    @DatabaseField(columnName = CATEGORY_EN)
    private String category_en;
    @DatabaseField
    private String subcategory_de = "";
    @DatabaseField
    private String subcategory_en = "";
    @DatabaseField(canBeNull = false, columnName = STUDENTS_PRICE)
    private double priceStudents;
    @DatabaseField(canBeNull = false)
    private double priceWorkers;
    @DatabaseField(canBeNull = false)
    private double priceGuests;
    @DatabaseField(columnName = ALLERGENS, dataType = DataType.SERIALIZABLE)
    private NewAllergen[] allergens;

    private Badge[] badges;

    @DatabaseField(columnName = BADGES)
    private String badgesAsString;

    @DatabaseField(columnName = RESTAURANT)
    private String restaurant;
    @DatabaseField(canBeNull = false, columnName = PRICE_TYPE)
    private PriceType pricetype;
    @DatabaseField
    private String image;
    @DatabaseField
    private String thumbnail;
    @DatabaseField(columnName = SORT_ORDER, defaultValue = "100")
    private int sortOrder = 100;


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

    public void setDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            this.date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            LOGGER.error("Could not parse date", e);
        }
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

    public double getPriceStudents() {
        return priceStudents;
    }

    @SuppressWarnings("SameParameterValue")
    public void setPriceStudents(double priceStudents) {
        this.priceStudents = priceStudents;
    }

    public double getPriceWorkers() {
        return priceWorkers;
    }

    @SuppressWarnings("SameParameterValue")
    public void setPriceWorkers(double priceWorkers) {
        this.priceWorkers = priceWorkers;
    }

    public double getPriceGuests() {
        return priceGuests;
    }

    @SuppressWarnings("SameParameterValue")
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
        if (badges == null && badgesAsString != null) {
            badges = BadgesStringConverter.convert(this.badgesAsString);
        }
        return badges;
    }

    public void setBadges(Badge[] badges) {
        this.badges = badges;
        buildBadgesAsString();
    }

    private void buildBadgesAsString() {
        badgesAsString = BadgesStringConverter.convert(badges.clone());
    }

    public String getBadgesAsString() {
        buildBadgesAsString();
        return badgesAsString;
    }

    public void setBadgesAsString(String badgesAsString) {
        this.badgesAsString = badgesAsString;
        badges = BadgesStringConverter.convert(this.badgesAsString);
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
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

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "RawMenu{" +
                "date=" + sdf.format(date) + " -- " + date +
                ", name_de='" + name_de + '\'' +
                ", restaurant='" + restaurant + '\'' +
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

    @JsonAnySetter
    public void setAny(String key, Object o) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Any setter invoked: {} -> {}", key, o);
    }

    public String getCategoryIdentifier() {
        return categoryIdentifier;
    }

    public void setCategoryIdentifier(String categoryIdentifier) {
        this.categoryIdentifier = categoryIdentifier;
        updateSortOrder();
    }

    private void updateSortOrder() {
        sortOrder = SortOrder.getSortOrder(name_de, categoryIdentifier);
    }

    public RawMenu copy() {
        try {
            return (RawMenu) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
