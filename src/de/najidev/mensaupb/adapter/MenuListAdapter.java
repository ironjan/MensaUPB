package de.najidev.mensaupb.adapter;

import android.content.Context;
import android.view.*;
import android.widget.*;

import org.slf4j.*;

import java.util.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.entity.Menu;
import de.najidev.mensaupb.helper.*;

public class MenuListAdapter extends ArrayAdapter<Menu> {

	Logger LOGGER = LoggerFactory.getLogger(MenuListAdapter.class
			.getSimpleName());

	public MenuListAdapter(final Context context, final int textViewResourceId,
			final Date date) {
		super(context, textViewResourceId);

		Object[] params = { context, textViewResourceId, date };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("MenuListAdapter({},{},{})", params);
		}
		
		// fill list
		final String location = ServiceContainer.getInstance().getContext()
				.getCurrentLocation();
		clear();
		for (final Menu menu : ServiceContainer.getInstance()
				.getMenuRepository().getMenus(location, date)) {
			add(menu);
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Created MenuListAdapter({},{},{})", params);
		}
		
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		Object[] params = { position, convertView, parent };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getView({},{},{})", params);
		}
		View v = convertView;
		if (v == null) {
			final LayoutInflater vi = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.menu_list, null);
		}

		final Menu m = getItem(position);

		if (m != null) {
			final TextView title = (TextView) v.findViewById(R.id.title);
			final TextView name = (TextView) v.findViewById(R.id.name);
			final TextView sides = (TextView) v.findViewById(R.id.sides);

			if (title != null) {
				title.setText(m.getTitle());
			}

			if (name != null) {
				name.setText(m.getName());
			}

			if (sides != null) {
				sides.setText(m.getSides());
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