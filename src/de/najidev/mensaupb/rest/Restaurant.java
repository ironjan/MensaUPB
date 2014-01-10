package de.najidev.mensaupb.rest;

import com.fasterxml.jackson.annotation.*;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.*;

/**
 * Represents a restaurant
 */
@DatabaseTable(tableName = "restaurants")
public class Restaurant {
    @DatabaseField
    private String name;
    @DatabaseField(generatedId = true)
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
