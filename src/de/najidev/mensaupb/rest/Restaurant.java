package de.najidev.mensaupb.rest;

import android.provider.*;

import com.fasterxml.jackson.annotation.*;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

import de.najidev.mensaupb.persistence.*;

/**
 * Represents a restaurant
 */
@DatabaseTable(tableName = "restaurants")
public class Restaurant {
    @DatabaseField
    private String name;
    @DatabaseField(generatedId = true, columnName = BaseColumns._ID)
    private long _id;

    public Restaurant() {
    }

    @JsonIgnore
    public long get_id() {
        return _id;
    }

    @JsonIgnore
    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
