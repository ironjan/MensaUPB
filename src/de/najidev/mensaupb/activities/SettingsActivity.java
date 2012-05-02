package de.najidev.mensaupb.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.dialog.ChooseOnListDialog;
import de.najidev.mensaupb.helper.Configuration;
import de.najidev.mensaupb.helper.ServiceContainer;

public class SettingsActivity extends SherlockListActivity
{
	de.najidev.mensaupb.helper.Context context;
	Configuration config;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setTheme(com.actionbarsherlock.R.style.Theme_Sherlock);
		getSupportActionBar().setIcon(R.drawable.action_settings_dark_holo);
		getSupportActionBar().setTitle("Einstellungen");
		this.getListView().setPadding(10, 0, 10, 0);

		super.onCreate(savedInstanceState);

		context = ServiceContainer.getInstance().getContext();
		config  = ServiceContainer.getInstance().getConfiguration();

		String[] days = new String[] {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag" };

		this.setListAdapter(new ArrayAdapter<String>(this, R.layout.settings_list, days)
		{
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				View v = convertView;
				if (v == null)
				{
					LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = vi.inflate(R.layout.settings_list, null);
				}

				String m = this.getItem(position);

				if (m != null)
				{
					TextView name = (TextView) v.findViewById(R.id.setting_name);
					TextView value = (TextView) v.findViewById(R.id.setting_value);

					if (name != null)
						name.setText(name.getText() + " " + m);
					if (value != null)
					{
						String optionValue;
						if (0 == position)
							optionValue = config.getMondayLocation();
						else if (1 == position)
							optionValue = config.getTuesdayLocation();
						else if (2 == position)
							optionValue = config.getWednesdayLocation();
						else if (3 == position)
							optionValue = config.getThursdayLocation();
						else
							optionValue = config.getFridayLocation();

						value.setText(optionValue);	
					}
				}
				return v;
			}
		});
	}

	protected void onListItemClick(android.widget.ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		
		Intent i = new Intent(this, ChooseOnListDialog.class);
		
		i.putExtra("title", "Ort wählen");
		i.putExtra("list", context.getLocationTitle());

		this.startActivityForResult(i, position);
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		String chosen = context.getLocationTitle()[data.getIntExtra("chosen", 0)];
		
		if (0 == requestCode)
			config.setMondayLocation(chosen);
		else if (1 == requestCode)
			config.setTuesdayLocation(chosen);
		else if (2 == requestCode)
			config.setWednesdayLocation(chosen);
		else if (3 == requestCode)
			config.setThursdayLocation(chosen);
		else if (4 == requestCode)
			config.setFridayLocation(chosen);
		
		setListAdapter(getListAdapter());
	}
}