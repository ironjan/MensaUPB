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


public class MenuAdapter extends ArrayAdapter<Menu> {

	private List<Menu> items;

	public MenuAdapter(Context context, int textViewResourceId, List<Menu> items) 
	{
		super(context, textViewResourceId, items);
		this.items = items;
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

		Menu m = items.get(position);

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
			{
				StringBuilder sb = new StringBuilder();
				for (String side : m.getSides())
				    sb.append(side).append(", ");

				sides.setText(sb.toString().replaceAll(", $", ""));
			}
		}
		return v;
	}
}