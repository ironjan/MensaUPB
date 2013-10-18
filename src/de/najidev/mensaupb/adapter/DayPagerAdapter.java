package de.najidev.mensaupb.adapter;

import java.util.*;

import android.content.Context;
import android.os.*;
import android.support.v4.view.*;
import android.view.*;
import android.widget.*;
import de.najidev.mensaupb.*;
import de.najidev.mensaupb.helper.*;

public class DayPagerAdapter extends PagerAdapter {

	private final Context context;

	ArrayList<MenuListAdapter> adapters = new ArrayList<MenuListAdapter>();

	public DayPagerAdapter(final Context context) {
		super();

		this.context = context;
		for (int i = 0; i < 5; i++) {
			adapters.add(null);
		}
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public Object instantiateItem(final View pager, final int position) {
		final ListView v = new ListView(context);

		adapters.add(position,
				new MenuListAdapter(context, R.layout.menu_list,
						ServiceContainer.getInstance().getContext()
								.getAvailableDates()[position]));

		v.setAdapter(adapters.get(position));
		((ViewPager) pager).addView(v, 0);
		return v;
	}

	@Override
	public void destroyItem(final View pager, final int position,
			final Object view) {
		((ViewPager) pager).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(final View view, final Object object) {
		return view.equals(object);
	}

	@Override
	public void finishUpdate(final View view) {
	}

	@Override
	public void restoreState(final Parcelable p, final ClassLoader c) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(final View view) {
	}

	/**
	 * This method is a workaround for notifyDataSetChanged()
	 * 
	 * @see http
	 *      ://stackoverflow.com/questions/7263291/viewpager-pageradapter-not
	 *      -updating-the-view
	 */
	@Override
	public int getItemPosition(final Object object) {
		return POSITION_NONE;
	}
}
