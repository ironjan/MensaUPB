package de.najidev.mensaupb.rest;

import com.fasterxml.jackson.annotation.*;

/**
 * Represents a restaurant
 */
public class Restaurant {
    private String name;
    private long _id;

    @JsonIgnore
    public long get_id() {
        return _id;
    }

    @JsonIgnore
    public void set_id(long _id) {
        this._id = _id;
    }

    public Restaurant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
