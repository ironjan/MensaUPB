package de.najidev.mensaupb.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.helper.ServiceContainer;

public class DayPagerAdapter extends PagerAdapter
{	
	private Context context;

	ArrayList<MenuListAdapter> adapters = new ArrayList<MenuListAdapter>();

	public DayPagerAdapter(Context context)
	{
		this.context =  context;
	}

	@Override
	public int getCount()
	{
		return 5;
	}

	public Object instantiateItem( View pager, int position )
	{
		ListView v = new ListView( context );

		adapters.add(position, new MenuListAdapter(
				this.context,
				R.layout.menu_list,
				ServiceContainer.getInstance().getContext().getAvailableDates()[position]
				));

		v.setAdapter(adapters.get(position));
		((ViewPager)pager ).addView(v, 0);
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
	public Parcelable saveState()
	{
		return null;
	}

	@Override
	public void startUpdate( View view ) {}

	/**
	 * This method is a workaround for notifyDataSetChanged()
	 * 
	 * @see http://stackoverflow.com/questions/7263291/viewpager-pageradapter-not-updating-the-view
	 */
	public int getItemPosition(Object object)
	{
		return POSITION_NONE;
	}
}
