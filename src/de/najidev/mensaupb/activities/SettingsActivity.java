package de.najidev.mensaupb.activities;

import org.slf4j.*;

import android.content.*;
import android.content.Context;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.actionbarsherlock.app.*;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.res.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.dialog.*;
import de.najidev.mensaupb.helper.*;

@EActivity
public class SettingsActivity extends SherlockListActivity {

	private final class SettingsActivityListAdapter extends
			ArrayAdapter<String> {

		private SettingsActivityListAdapter(Context context, int resource,
				String[] objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(final int position, final View convertView,
				final ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				final LayoutInflater vi = (LayoutInflater) getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.settings_list, null);
			}

			final String m = getItem(position);

			if (m != null) {
				final TextView name = (TextView) v
						.findViewById(R.id.setting_name);
				final TextView value = (TextView) v
						.findViewById(R.id.setting_value);

				if (name != null) {
					name.setText(name.getText() + " " + m);
				}
				if (value != null) {
					String optionValue;
					if (0 == position) {
						optionValue = config.getMondayLocation();
					}
					else if (1 == position) {
						optionValue = config.getTuesdayLocation();
					}
					else if (2 == position) {
						optionValue = config.getWednesdayLocation();
					}
					else if (3 == position) {
						optionValue = config.getThursdayLocation();
					}
					else {
						optionValue = config.getFridayLocation();
					}

					value.setText(optionValue);
				}
			}
			return v;
		}
	}

	de.najidev.mensaupb.helper.Context context;
	Configuration config;

	@StringArrayRes
	String[] days;

	@StringRes
	String settings;

	Logger LOGGER = LoggerFactory.getLogger(SettingsActivity.class
			.getSimpleName());

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		Object[] params = { savedInstanceState };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onCreate({})", params);
		}

		getListView().setPadding(8, 0, 8, 0);

		super.onCreate(savedInstanceState);

		context = ServiceContainer.getInstance().getContext();
		config = ServiceContainer.getInstance().getConfiguration();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onCreate({}) -> {}", params, "VOID");
		}
	}

	@AfterInject
	void afterInject() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("afterInject()");
		}
		getSupportActionBar().setTitle(settings);
		setListAdapter(new SettingsActivityListAdapter(this,
				R.layout.settings_list, days));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onListItemClick() -> {}", "VOID");
		}
	}

	@Override
	protected void onListItemClick(final android.widget.ListView l,
			final View v, final int position, final long id) {
		Object[] params = { l, v, position, id };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onListItemClick({},{},{},{})", params);
		}
		super.onListItemClick(l, v, position, id);

		final Intent i = new Intent(this, ChooseOnListDialog.class);

		i.putExtra("title", "Ort wählen");
		i.putExtra("list", context.getLocationTitle());

		this.startActivityForResult(i, position);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onListItemClick({},{},{},{}) -> {}", params, "VOID");
		}
	};

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		Object[] params = { requestCode, resultCode, data };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onActivityResult({},{},{},{})", params);
		}

		if (0 == resultCode) {
			return;
		}

		final String chosen = context.getLocationTitle()[data.getIntExtra(
				"chosen", 0)];

		if (0 == requestCode) {
			config.setMondayLocation(chosen);
		}
		else if (1 == requestCode) {
			config.setTuesdayLocation(chosen);
		}
		else if (2 == requestCode) {
			config.setWednesdayLocation(chosen);
		}
		else if (3 == requestCode) {
			config.setThursdayLocation(chosen);
		}
		else if (4 == requestCode) {
			config.setFridayLocation(chosen);
		}

		setListAdapter(getListAdapter());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onActivityResult({},{},{},{}) -> {}", params, "VOID");
		}
	}
}