package de.najidev.mensaupb.rest;

import com.fasterxml.jackson.annotation.*;

import org.slf4j.*;

import java.text.*;
import java.util.*;

/**
 * TODO document
 */
public class MenuContent  {
    private static final SimpleDateFormat sSDF= new SimpleDateFormat("yyyy-MM-dd");

    private long _id;
    private long _restaurantId;

    @JsonIgnore
    public long get_restaurantId() {
        return _restaurantId;
    }

    @JsonIgnore
    public void set_restaurantId(long _restaurantId) {
        this._restaurantId = _restaurantId;
    }

    @JsonIgnore
    public long get_id() {
        return _id;
    }

    @JsonIgnore
    public void set_id(long _id) {
        this._id = _id;
    }

    private Date date;
    private String description, name, type, price, counter;
    String[] side_dishes;

    private final Logger LOGGER = LoggerFactory.getLogger(MenuContent.class.getSimpleName());

    @JsonProperty("date")
    public String getDateAsString(){
        if(date == null){
            return "";
        }

    return sSDF.format(date);
    }

    @JsonProperty("date")
    public void setDate(String date){
        try {
            this.date = sSDF.parse(date);
        } catch (ParseException e) {
        LOGGER.error(e.getMessage(),  e);
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }


    public String[] getSide_dishes() {
        return side_dishes;
    }

    public void setSide_dishes(String[] side_dishes) {
        this.side_dishes = side_dishes;
    }

    public MenuContent() {

    }

    @Override
    public String toString() {
        return "MenuContent{" +
                "side_dishes=" + Arrays.toString(side_dishes) +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", counter='" + counter + '\'' +
                '}';
    }
}
