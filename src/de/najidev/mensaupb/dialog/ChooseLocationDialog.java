package de.najidev.mensaupb.dialog;

import java.util.Set;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;

import de.najidev.mensaupb.helper.ServiceContainer;

public class ChooseLocationDialog extends SherlockListActivity
{
	ArrayAdapter<String> adapter;
	String[] locations;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setTitle("Ort wählen");

		Set<String> locationSet = ServiceContainer.getInstance().getContext().getAvailableLocations().keySet();
		locations = locationSet.toArray(new String[locationSet.size()]);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, locations);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		ServiceContainer.getInstance().getContext().setCurrentLocation(locations[position]);

		this.setResult(1);
		this.finish();
	}
}
