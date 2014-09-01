package de.ironjan.mensaupb.library.stw;

import android.provider.BaseColumns;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.IllegalFormatException;

/**
 * TODO javadoc
 */
@DatabaseTable(tableName = Menu.TABLE)
public class Menu {
    public static final String TABLE = "menus";
    public static final String NAME_GERMAN = "nameGerman";
    public static final String NAME_ENGLISH = "nameEnglish";
    public static final String ID = BaseColumns._ID;
    public static final String DATE = "date";
    public static final String LOCATION = "location";
    public static final String CATEGORY = "category";
    public static final String ALLERGENES = "allergenes";
    public static final String PRICE = "price";
    public static final String PRICE_PER_100G = "pricePer100g";
    public static final String SORT = "sort";

    private static final String DATABASE_DATE_FORMAT_STRING = "dd.MM.yyyy";
    public static final SimpleDateFormat DATABASE_DATE_FORMAT = new SimpleDateFormat(DATABASE_DATE_FORMAT_STRING);
    public static final String LAST_UPDATE_TIMESTAMP = "lastUpdateTimestamp";

    public static final String[] COLUMNS = {ID, NAME_GERMAN, NAME_GERMAN, DATE, LOCATION, CATEGORY, ALLERGENES, SORT, LAST_UPDATE_TIMESTAMP, PRICE, PRICE_PER_100G};

    @DatabaseField(columnName = ID, generatedId = true)
    private long _id;

    @DatabaseField(columnName = NAME_ENGLISH)
    private String nameEnglish;

    @DatabaseField(columnName = NAME_GERMAN)
    private String nameGerman;

    @DatabaseField(columnName = DATE)
    private String date;

    @DatabaseField(columnName = LOCATION)
    private String location;

    @DatabaseField(columnName = CATEGORY)
    private String category;

    @DatabaseField(columnName = ALLERGENES)
    private String allergenes;

    @DatabaseField(columnName = SORT)
    private int sort;

    @DatabaseField(columnName = LAST_UPDATE_TIMESTAMP)
    private long lastUpdateTimestamp;

    @DatabaseField(columnName = PRICE)
    private long price;

    @DatabaseField(columnName = PRICE_PER_100G)
    private boolean pricePer100g;

    public Menu() {
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getAllergenes() {
        return allergenes;
    }

    public void setAllergenes(String allergenes) {
        this.allergenes = allergenes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    /**
     * TODO javadoc
     */
    public void setDate(String date) throws IllegalFormatException {
        try {
            DATABASE_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date string must have the format " + DATABASE_DATE_FORMAT_STRING);
        }
        this.date = date;
    }

    public String getNameGerman() {
        return nameGerman;
    }

    public void setNameGerman(String nameGerman) {
        this.nameGerman = nameGerman;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isPricePer100g() {
        return pricePer100g;
    }

    public void setPricePer100g(boolean pricePer100g) {
        this.pricePer100g = pricePer100g;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "_id=" + _id +
                ", nameEnglish='" + nameEnglish + '\'' +
                ", nameGerman='" + nameGerman + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", category='" + category + '\'' +
                ", allergenes='" + allergenes + '\'' +
                ", sort=" + sort +
                ", lastUpdateTimestamp=" + lastUpdateTimestamp +
                ", price=" + price +
                ", pricePer100g=" + pricePer100g +
                '}';
    }
}
