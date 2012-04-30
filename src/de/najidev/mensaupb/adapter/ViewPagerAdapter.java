package de.najidev.mensaupb.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.entity.Menu;
import de.najidev.mensaupb.entity.MenuRepository;
import de.najidev.mensaupb.helper.ServiceContainer;

public class ViewPagerAdapter extends PagerAdapter
{	
	private Context context;

	List<Menu> mondayList;
	MenuListAdapter mondayAdapter;

	List<Menu> tuesdayList;
	MenuListAdapter tuesdayAdapter;

	List<Menu> wednesdayList;
	MenuListAdapter wednesdayAdapter;

	List<Menu> thursdayList;
	MenuListAdapter thursdayAdapter;
	
	List<Menu> fridayList;
	MenuListAdapter fridayAdapter;

	public ViewPagerAdapter(Context context)
	{
		this.context =  context;
		
		ServiceContainer container = ServiceContainer.getInstance();
		
		mondayList    = container.getMenuRepository().getMenusBasedOnContext();
	}

	@Override
	public int getCount()
	{
		return 5;
	}

	public Object instantiateItem( View pager, int position )
	{
		ListView v = new ListView( context );

		MenuListAdapter adapter = new MenuListAdapter(
			this.context,
			R.layout.menu_list,
			ServiceContainer.getInstance().getContext().getAvailableDates()[position],
			"mensa"
		);
		v.setAdapter( adapter );
		((ViewPager)pager ).addView( v, 0 );
		return v;
	}
 
    @Override
    public void destroyItem( View pager, int position, Object view )
    {
        ((ViewPager)pager).removeView( (View)view );
    }
 
    @Override
    public boolean isViewFromObject( View view, Object object )
    {
        return view.equals( object );
    }
 
    @Override
    public void finishUpdate( View view ) {}
 
    @Override
    public void restoreState( Parcelable p, ClassLoader c ) {}
 
    @Override
    public Parcelable saveState() {
        return null;
    }
 
    @Override
    public void startUpdate( View view ) {}
}
