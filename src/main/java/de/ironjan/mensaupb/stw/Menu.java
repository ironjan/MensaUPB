package de.ironjan.mensaupb.stw;

import android.provider.*;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

import java.text.*;
import java.util.*;

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
    public static final String SORT = "sort";
    private static final String DATABASE_DATE_FORMAT_STRING = "dd.MM.yyyy";
    public static final SimpleDateFormat DATABASE_DATE_FORMAT = new SimpleDateFormat(DATABASE_DATE_FORMAT_STRING);
    public static final String LAST_UPDATE_TIMESTAMP = "lastUpdateTimestamp";

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
                '}';
    }
}
