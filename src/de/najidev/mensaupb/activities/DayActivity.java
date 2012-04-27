package de.najidev.mensaupb.activities;

import com.google.inject.Inject;

import roboguice.activity.RoboListActivity;
import de.najidev.mensaupb.ApplicationContext;
import de.najidev.mensaupb.R;
import de.najidev.mensaupb.adapter.MenuAdapter;
import de.najidev.mensaupb.entity.MenuRepository;
import android.os.Bundle;

public class DayActivity extends RoboListActivity
{
	@Inject
	MenuRepository menuRepository;
	
	@Inject
	ApplicationContext applicationContext;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.drawUserInterface();
	}
	
	public void drawUserInterface()
	{
		this.setListAdapter(
			new MenuAdapter(
				this,
				R.layout.menu_list,
				menuRepository.getMenus(
					applicationContext.getCurrentLocation(),
					applicationContext.getCurrentDate()
				)
			)
		);
	}
}