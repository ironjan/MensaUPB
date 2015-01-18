package de.ironjan.mensaupb.adapters;

import android.content.*;
import android.database.*;
import android.view.*;
import android.widget.*;

import java.util.*;

import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.library.*;
import de.ironjan.mensaupb.library.stw.*;

/**
 * Binds raw menus to de.ironjan.mensaupb.R.layout.view_menu_list_item
 */
public class MenuDetailViewBinder implements android.support.v4.widget.SimpleCursorAdapter.ViewBinder {

    private final Context mContext;
    private final String keyMensaAcademica, keyMensaForum, shortMensaAcademica, shortMensaForum;

    public MenuDetailViewBinder(Context context) {
        mContext = context;
        keyMensaAcademica = getString(context, R.string.mensa_academica_paderborn);
        keyMensaForum = getString(context, R.string.mensa_forum_paderborn);
        shortMensaAcademica = getString(context, R.string.shortMensaAcademica);
        shortMensaForum = getString(context, R.string.shortMensaForum);
    }

    private String getString(Context context, int id) {
        return mContext.getResources().getString(id);
    }

    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (view == null || cursor == null || columnIndex < 0) {
            return false;
        }

        if (!(view instanceof TextView)) {
            return false;
        }


        int id = view.getId();
        switch (id) {
            case R.id.textPrice:
                bindPrice((TextView) view, cursor, columnIndex);
                return true;
            case R.id.textPricePer100g:
                bindPricePer100g((TextView) view, cursor, columnIndex);
                return true;
            case R.id.textBadges:
                bindBadges((TextView) view, cursor, columnIndex);
                return true;
            case R.id.textRestaurant:
                bindRestaurant((TextView) view, cursor, columnIndex);
                return true;
            default:
                ((TextView) view).setText(cursor.getString(columnIndex));
                return true;
        }
    }

    private void bindRestaurant(TextView view, Cursor cursor, int columnIndex) {
        final String key = cursor.getString(columnIndex);
        if (keyMensaAcademica.equals(key)) {
            view.setText(shortMensaAcademica);
        } else if (keyMensaForum.equals(key)) {
            view.setText(shortMensaForum);
        } else {
            view.setText("");
        }
    }

    private void bindPrice(TextView view, Cursor cursor, int columnIndex) {
        Double price = cursor.getDouble(columnIndex);
        if (price != 0) {
            // it can be 0 when syncing and not yet set
            String priceAsString = String.format(Locale.GERMAN, "%.2f €", price);
            view.setText(priceAsString);
        }
    }

    private void bindPricePer100g(TextView view, Cursor cursor, int columnIndex) {
        double price = cursor.getDouble(columnIndex - 1);
        if (price == 0) {
            return;
        }

        String string = cursor.getString(columnIndex);
        if (PriceType.WEIGHT.toString().equals(string)) {
            view.setText("/100g");
        } else {
            view.setText("");
        }
    }

    private void bindBadges(TextView textView, Cursor cursor, int columnIndex) {
        String badgesAsString = cursor.getString(columnIndex);
        Badge[] badges = BadgesStringConverter.convert(badgesAsString);
        Context context = textView.getContext();

        if (badges == null || badges.length < 1) {
            textView.setText("");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder(context.getString(badges[0].getStringId()));
        for (int i = 1; i < badges.length; i++) {
            String badgeString = context.getString(badges[i].getStringId());
            stringBuilder.append(", ")
                    .append(badgeString);
        }
        textView.setText(stringBuilder.toString());
    }
}
