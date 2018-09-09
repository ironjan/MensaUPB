package de.ironjan.mensaupb.model;

import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.ironjan.mensaupb.stw.rest_api.PriceType;
import de.ironjan.mensaupb.stw.rest_api.StwMenu;

public class MenuSorter {
    private static final String SORT_ORDER = String.format("%s ASC, %s ASC, %s ASC",
            StwMenu.SORT_ORDER, StwMenu.PRICE_TYPE, StwMenu.STUDENTS_PRICE);

    public static List<LocalizedMenu> sort(List<LocalizedMenu> menus) {
        Log.e("MenuSorter", menus.toString());
        Collections.sort(menus, new PriceTypeComparator());
        Collections.sort(menus, new PriceComparator());
        Collections.sort(menus, new CategorySorter());
        Log.e("MenuSorter", menus.toString());
        return menus;
    }

    private static class PriceTypeComparator implements java.util.Comparator<LocalizedMenu> {
        @Override
        public int compare(LocalizedMenu o1, LocalizedMenu o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }

            PriceType pt1 = PriceType.fromString(o1.getPricetype());
            PriceType pt2 = PriceType.fromString(o2.getPricetype());

            if (pt1.getId() < (pt2).getId()) {
                return -1;
            }
            return 1;
        }
    }

    private static class PriceComparator implements java.util.Comparator<LocalizedMenu> {
        @Override
        public int compare(LocalizedMenu o1, LocalizedMenu o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }

            if (o1.getPrice() < o2.getPrice()) {
                return -1;
            }
            if (o1.getPrice() > o2.getPrice()) {
                return 1;
            }
            return 0;
        }
    }

    private static class CategorySorter implements Comparator<LocalizedMenu> {
        @Override
        public int compare(LocalizedMenu o1, LocalizedMenu o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }

            if (o1.getSortOrder() < o2.getSortOrder()) {
                return -1;
            }
            if (o1.getSortOrder() > o2.getSortOrder()) {
                return 1;
            }
            return 0;
        }
    }
}
