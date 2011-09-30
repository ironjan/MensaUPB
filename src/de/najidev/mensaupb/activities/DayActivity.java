package de.najidev.mensaupb.activities;

import com.google.inject.Inject;

import roboguice.activity.RoboListActivity;
import de.najidev.mensaupb.R;
import de.najidev.mensaupb.adapter.MenuAdapter;
import de.najidev.mensaupb.entity.MenuRepository;
import de.najidev.mensaupb.helper.DateHelper;
import android.app.TabActivity;
import android.os.Bundle;

public class DayActivity extends RoboListActivity
{
	@Inject
	MenuRepository menuRepository;

	@Inject
	DateHelper dateHelper;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		TabActivity act = (TabActivity) getParent();
		setListAdapter(
				new MenuAdapter(
						this,
						R.layout.menu_list,
						menuRepository.getMenus("mensa", dateHelper.getDates().get(
								act.getTabHost().getCurrentTab()
								))
						)
				);
	}
}