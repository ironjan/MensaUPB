package de.najidev.mensaupb.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.entity.Menu;


public class MenuAdapter extends ArrayAdapter<Menu>
{
	public MenuAdapter(Context context, int textViewResourceId, List<Menu> items) 
	{
		super(context, textViewResourceId, items);
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