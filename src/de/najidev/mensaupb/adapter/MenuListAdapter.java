package de.najidev.mensaupb.adapter;

import java.util.Date;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.entity.Menu;
import de.najidev.mensaupb.helper.ServiceContainer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuListAdapter extends ArrayAdapter<Menu>
{	
	public MenuListAdapter(Context context, int textViewResourceId, Date date)
	{
		super(context, textViewResourceId);

		// fill list
		String location = ServiceContainer.getInstance().getContext().getCurrentLocation();
		this.clear();
		for (Menu menu : ServiceContainer.getInstance().getMenuRepository().getMenus(location, date))
			this.add(menu);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.menu_list, null);
		}

		Menu m = this.getItem(position);

		if (m != null)
		{
			TextView title = (TextView) v.findViewById(R.id.title);
			TextView name  = (TextView) v.findViewById(R.id.name);
			TextView sides = (TextView) v.findViewById(R.id.sides);

			if (title != null)
				title.setText(m.getTitle());

			if(name != null)
				name.setText(m.getName());

			if(sides != null)
				sides.setText(m.getSides());
		}
		return v;
	}
}