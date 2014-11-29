package de.ironjan.mensaupb.library.stw;

import android.provider.*;
import android.text.*;

import com.fasterxml.jackson.annotation.*;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

import org.slf4j.*;

import java.text.*;
import java.util.*;

import de.ironjan.mensaupb.library.*;

/**
 * A class representing a raw menu with all possible information
 */
@DatabaseTable(tableName = RawMenu.TABLE)
public class RawMenu {
    public static final String TABLE = "menus";
    public static final String NAME_GERMAN = "name_de";
    public static final String CATEGORY = "category_de";
    public static final String STUDENTS_PRICE = "priceStudents";
    public static final String PRICE_TYPE = "pricetype";
    public static final String DATE = "date";
    public static final String RESTAURANT = "restaurant";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String ALLERGENS = "allergens";
    public static final Logger LOGGER = LoggerFactory.getLogger(RawMenu.class);
    public static final String SORT_ORDER = "sortOrder";
    public static final String BADGES = "badges";
    @DatabaseField(generatedId = true, columnName = BaseColumns._ID)
    long _id;
    @DatabaseField
    int order_info;
    @DatabaseField(canBeNull = false, columnName = DATE, dataType = DataType.DATE_STRING, format = DATE_FORMAT)
    private Date date;
    @DatabaseField(canBeNull = false, columnName = "name_de")
    private String name_de;
    @DatabaseField(canBeNull = false)
    private String name_en;
    @DatabaseField
    private String description_de;
    @DatabaseField
    private String description_en;
    @DatabaseField
    @JsonProperty("category")
    private String categoryIdentifier;
    @DatabaseField
    private String category_de;
    @DatabaseField
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
        cleanNames();
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
        cleanNames();
    }

    private void cleanNames() {
        name_de = cleanName(name_de);
        name_en = cleanName(name_en);
    }

    private String cleanName(String name) {
        String potentialName = null;
        if (!TextUtils.isEmpty(name) && name.contains("|")) {
            String[] split = name.split("\\|");
            potentialName = split[0].trim();
        }

        if (TextUtils.isEmpty(potentialName)) {
            return name;
        }
        return potentialName;
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
        updateCategories();
    }

    public String getCategory_en() {
        return category_en;
    }

    public void setCategory_en(String category_en) {
        this.category_en = category_en;
        updateCategories();
    }

    public String getSubcategory_de() {
        return subcategory_de;
    }

    public void setSubcategory_de(String subcategory_de) {
        this.subcategory_de = subcategory_de;
        updateCategories();
    }

    public String getSubcategory_en() {
        return subcategory_en;
    }

    public void setSubcategory_en(String subcategory_en) {
        this.subcategory_en = subcategory_en;
        updateCategories();
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
        cleanAllergens();
    }

    private synchronized void cleanAllergens() {
        Vector<NewAllergen> copy = new Vector<NewAllergen>(allergens.length);
        for (NewAllergen allergen : allergens) {
            if (allergen != NewAllergen.UNKNOWN) {
                copy.add(allergen);
            }
        }
        allergens = new NewAllergen[copy.size()];
        copy.copyInto(allergens);
        System.currentTimeMillis();
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

    public void setBadgesAsString(String badgesAsString) {
        this.badgesAsString = badgesAsString;
        badges = BadgesStringConverter.convert(this.badgesAsString);
    }

    public String getBadgesAsString() {
        buildBadgesAsString();
        return badgesAsString;
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


    public void updateCategories() {
        updateDeCategory();
        updateEnCategory();
    }

    private void updateDeCategory() {
        if (subcategory_de != null && TextUtils.isEmpty(subcategory_de.trim())) {
            return;
        }
        category_de = subcategory_de;
    }

    private void updateEnCategory() {
        if (subcategory_en != null && TextUtils.isEmpty(subcategory_en.trim())) {
            return;
        }
        category_en = subcategory_en;
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
        sortOrder = SortOrder.getSortOrder(categoryIdentifier);
    }
}
