package de.ironjan.mensaupb.adapters;

import android.database.*;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.*;
import android.widget.*;

import java.util.*;

import de.ironjan.mensaupb.*;

/**
 * Created by ljan on 5/21/14.
 */
public class MenuListItemViewBinder implements SimpleCursorAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (view.getId() == R.id.textPrice) {
            bindPrice((TextView) view, cursor, columnIndex);
            return true;
        }
        return false;
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
