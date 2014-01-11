package de.najidev.mensaupb.adapter;

import android.content.Context;
import android.text.*;
import android.view.*;
import android.widget.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.dao.*;

import org.slf4j.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.entity.Menu;
import de.najidev.mensaupb.persistence.*;
import de.najidev.mensaupb.rest.*;

public class MenuListAdapter extends ArrayAdapter<MenuContent> {

    Logger LOGGER = LoggerFactory.getLogger(MenuListAdapter.class
            .getSimpleName());

    public MenuListAdapter(final Context context, final int textViewResourceId,
                           final Date date, final String location) {
        super(context, textViewResourceId);

        Object[] params = {context, textViewResourceId, date};
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("MenuListAdapter({},{},{})", params);
        }

        clear();

        DatabaseHelper databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);

        List<MenuContent> existingMenuContents;
        try {
            Dao<MenuContent, Long> menuContentDao = databaseHelper.getMenuContentDao();
            existingMenuContents = menuContentDao.queryForAll();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            existingMenuContents = new ArrayList<MenuContent>();
        }

        for (MenuContent existingMenuContent : existingMenuContents) {
            add(existingMenuContent);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Created MenuListAdapter({},{},{})", params);
        }

    }

    @Override
    public View getView(final int position, final View convertView,
                        final ViewGroup parent) {
        Object[] params = {position, convertView, parent};
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getView({},{},{})", params);
        }
        View v = convertView;
        if (v == null) {
            final LayoutInflater vi = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.menu_list, null);
        }

        final MenuContent m = getItem(position);

        if (m != null) {
            final TextView title = (TextView) v.findViewById(R.id.title);
            final TextView name = (TextView) v.findViewById(R.id.name);
            final TextView sides = (TextView) v.findViewById(R.id.sides);

            if (title != null) {
                title.setText(m.getDescription());
            }

            if (name != null) {
                name.setText(m.getName());
            }

            if (sides != null && m.getSide_dishes() != null) {
                final String text = Arrays.toString(m.getSide_dishes());
                if(!TextUtils.isEmpty(text)){
                    sides.setText(text);
                }
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getView({},{},{}) -> v", params);
        }
        return v;
    }

    @Override
    public String toString() {
        return "MenuListAdapter []";
    }
}