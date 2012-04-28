package de.najidev.mensaupb.activities;

import java.util.List;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.entity.Menu;
import de.najidev.mensaupb.entity.MenuRepository;
import de.najidev.mensaupb.helper.ServiceContainer;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DayActivity extends ListActivity
{
	MenuRepository menuRepository = ServiceContainer.getInstance().getMenuRepository();
	List<Menu> menuList;
	
	private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
	{	
		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent)
		{			
			menuList.clear();
			
			for (Menu menu : menuRepository.getMenusBasedOnContext())
				menuList.add(menu);

			((ArrayAdapter<Menu>) getListAdapter()).notifyDataSetChanged();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.menuList = menuRepository.getMenusBasedOnContext();
		
		this.setListAdapter(
				new ArrayAdapter<Menu>(this, R.layout.menu_list, this.menuList)
				{
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
			);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		this.registerReceiver(broadcastReceiver, new IntentFilter("de.najidev.mensaupb.UPDATE_TAB"));
	}

	protected void onPause()
	{
		super.onPause();
		this.unregisterReceiver(broadcastReceiver);
	}
}