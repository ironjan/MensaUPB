package de.najidev.mensaupb.rest;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

/**
 * TODO document
 */
public class MenuEntry {
    private MenuContent menuContent;

    @JsonIgnore
    public Date getDate() {
        return menuContent.getDate();
    }

    @JsonIgnore

    public String getDescription() {
        return menuContent.getDescription();
    }

    @JsonIgnore

    public String getName() {
        return menuContent.getName();
    }

    @JsonIgnore

    public String getType() {
        return menuContent.getType();
    }

    @JsonIgnore

    public String getPrice() {
        return menuContent.getPrice();
    }

    @JsonIgnore

    public String getCounter() {
        return menuContent.getCounter();
    }

    @JsonIgnore

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
