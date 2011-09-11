package de.najidev.mensaupb.activities;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.adapter.MenuAdapter;
import de.najidev.mensaupb.entity.MenuRepository;
import de.najidev.mensaupb.helper.DateHelper;
import android.app.ListActivity;
import android.app.TabActivity;
import android.os.Bundle;

public class DayActivity extends ListActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
        TabActivity act = (TabActivity) getParent();

		setListAdapter(
				new MenuAdapter(
						this,
						R.layout.menu_list,
						MenuRepository.getInstance().getMenus(DateHelper.getInstance().getDates().get(
								act.getTabHost().getCurrentTab()
						))
				)
		);
	}
}