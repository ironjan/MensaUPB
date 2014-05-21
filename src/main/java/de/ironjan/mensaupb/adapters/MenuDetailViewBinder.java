package de.ironjan.mensaupb.adapters;

import android.database.*;
import android.view.*;
import android.widget.*;

import java.util.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.stw.*;

/**
 * Extension of MenuListItemViewBinder to show explained allergens too
 */
public class MenuDetailViewBinder implements android.support.v4.widget.SimpleCursorAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (view == null || cursor == null || columnIndex < 0) {
            return false;
        }

        if (!(view instanceof TextView)) {
            return false;
        }

        TextView textView = (TextView) view;

        switch (view.getId()) {
            case R.id.textAllergens:
                String allergenes = cursor.getString(columnIndex);
                textView.setText(Allergene.getExplanation(allergenes));
                return true;
            case R.id.textPrice:
                bindPrice((TextView) view, cursor, columnIndex);
                return true;
            default:
                textView.setText(cursor.getString(columnIndex));
                return true;
        }
    }

    private void bindPrice(TextView view, Cursor cursor, int columnIndex) {
        double price = cursor.getDouble(columnIndex);
        if (price != 0) {
            // it can be 0 when syncing and not yet set
            String priceAsString = String.format(Locale.GERMAN, "%.2f €", price);
            view.setText(priceAsString);
        }
    }
}
