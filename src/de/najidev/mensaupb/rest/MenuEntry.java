package de.najidev.mensaupb.rest;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

/**
 * TODO document
 */
public class MenuEntry implements Menu {
    private MenuContent menuContent;

    @JsonIgnore
    @Override
    public Date getDate() {
        return menuContent.getDate();
    }

    @JsonIgnore
    @Override
    public String getDescription() {
        return menuContent.getDescription();
    }

    @JsonIgnore
    @Override
    public String getName() {
        return menuContent.getName();
    }

    @JsonIgnore
    @Override
    public String getType() {
        return menuContent.getType();
    }

    @JsonIgnore
    @Override
    public String getPrice() {
        return menuContent.getPrice();
    }

    @JsonIgnore
    @Override
    public String getCounter() {
        return menuContent.getCounter();
    }

    @JsonIgnore
    @Override
    public String[] getSide_dishes() {
        return new String[0];
    }

    @JsonProperty("menu")
    public MenuContent getMenuContent() {
        return menuContent;
    }

    @JsonProperty("menu")
    public void setMenuContent(MenuContent menuContent) {
        this.menuContent = menuContent;
    }

    @Override
    public String toString() {
        return "MenuEntry{" +
                "menuContent=" + menuContent +
                '}';
    }

    public MenuEntry() {
    }
}
