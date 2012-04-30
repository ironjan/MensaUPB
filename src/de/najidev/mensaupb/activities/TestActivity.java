package de.najidev.mensaupb.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockActivity;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.adapter.ViewPagerAdapter;
import de.najidev.mensaupb.helper.ServiceContainer;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;


public class TestActivity extends SherlockActivity implements OnPageChangeListener, TabListener
{
	ViewPager pager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setTheme(com.actionbarsherlock.R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		
		ServiceContainer container = ServiceContainer.getInstance();
		if (!container.isInitialized())
			try
			{
				container = ServiceContainer.getInstance().initialize(this.getApplicationContext());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.finish();
		}

		ViewPagerAdapter adapter = new ViewPagerAdapter(this);
		pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setOnPageChangeListener(this);
		pager.setAdapter(adapter);

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for (int i = 0; i < 5; i++)
		{
			ActionBar.Tab tab = this.getSupportActionBar().newTab();
			tab.setText("Page " + (i+1));
			tab.setTabListener(this);
			this.getSupportActionBar().addTab(tab);
		}
	}

	public void onPageScrollStateChanged(int arg0) { }

	public void onPageScrolled(int arg0, float arg1, int arg2) { }

	public void onPageSelected(int arg0)
	{
		getSupportActionBar().getTabAt(arg0).select();
		
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		pager.setCurrentItem(tab.getPosition());
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
