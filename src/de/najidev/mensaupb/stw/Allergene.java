package de.najidev.mensaupb.stw;


import android.provider.*;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = Allergene.TABLE)
public class Allergene {
    public static final String TABLE = "allergenes";
    public static final String ID = BaseColumns._ID;
    public static final String NAME = "name";
    public static final String KEY = "key";

    @DatabaseField(columnName = NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = KEY, canBeNull = false)
    private String key;

    @DatabaseField(columnName = ID, generatedId = true)
    private long _id;

    public Allergene() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
