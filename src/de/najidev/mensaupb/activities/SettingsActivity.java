package de.najidev.mensaupb.activities;

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

	de.najidev.mensaupb.helper.Context context;
	Configuration config;

	@StringArrayRes
	String[] days;

	@StringRes
	String settings;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		getListView().setPadding(8, 0, 8, 0);

		super.onCreate(savedInstanceState);

		context = ServiceContainer.getInstance().getContext();
		config = ServiceContainer.getInstance().getConfiguration();

	}

	@AfterInject
	void afterInject() {
		getSupportActionBar().setTitle(settings);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.settings_list,
				days) {

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
		});
	}

	@Override
	protected void onListItemClick(final android.widget.ListView l,
			final View v, final int position, final long id) {
		super.onListItemClick(l, v, position, id);

		final Intent i = new Intent(this, ChooseOnListDialog.class);

		i.putExtra("title", "Ort wählen");
		i.putExtra("list", context.getLocationTitle());

		this.startActivityForResult(i, position);
	};

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
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
	}
}