package de.ironjan.mensaupb.menus_ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.api.model.Badge;
import de.ironjan.mensaupb.api.model.LocalizedMenu;

/**
 * Binds raw menus to de.ironjan.mensaupb.R.layout.view_menu_list_item
 */
public class MenuDetailViewBinder  {

    public static boolean setViewValue(View view, LocalizedMenu menu) {
        if (view == null || menu == null) {
            return false;
        }

        if (!(view instanceof TextView)) {
            return false;
        }


        int id = view.getId();
        final TextView tv = (TextView) view;
        switch (id) {
            case R.id.textName:
                tv.setText(menu.getName());
                return true;
            case R.id.textCategory:
                tv.setText(menu.getCategory());
                return true;
            case R.id.textPrice:
                bindPrice(tv, menu);
                return true;
            case R.id.textPricePer100g:
                bindPricePer100g(tv, menu);
                return true;
            case R.id.textBadges:
                bindBadges(tv, menu);
                return true;
            default:
                tv.setText(menu.toString());
                return true;
        }
    }

    private static void bindPrice(TextView tv, LocalizedMenu menu) {
        Double price = menu.getPrice();
        if (price != 0) {
            // it can be 0 when syncing and not yet set
            String priceAsString = String.format(Locale.GERMAN, "%.2f €", price);
            tv.setText(priceAsString);
        }
    }


    private static void bindPricePer100g(TextView view, LocalizedMenu menu) {
        double price = menu.getPrice();
        if (price == 0) {
            return;
        }

        if (menu.isWeighted()) {
            view.setText("/100g");
        } else {
            view.setText("");
        }
    }

    private static void bindBadges(TextView textView, LocalizedMenu menu) {
        final String[] badgesArray = menu.getBadges();
        List<Badge> badges = new ArrayList<>(badgesArray.length);
        for (String s : badgesArray) {
            badges.add(Badge.Companion.fromString(s));
        }

        Context context = textView.getContext();
        Resources resources = context.getResources();

        if (badges.isEmpty()) {
            textView.setText("");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder(context.getString(badges.get(0).getStringId()));
        for (int i = 1; i < badges.size(); i++) {
            String badgeString = resources.getString(badges.get(i).getStringId());
            stringBuilder.append(", ")
                    .append(badgeString);
        }
        textView.setText(stringBuilder.toString());
    }

}
