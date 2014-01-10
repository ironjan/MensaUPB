package de.najidev.mensaupb.adapter;

import android.content.Context;
import android.os.*;
import android.support.v4.view.*;
import android.view.*;
import android.widget.*;

import org.slf4j.*;

import java.util.*;

import de.najidev.mensaupb.*;
import de.najidev.mensaupb.helper.*;

public class DayPagerAdapter extends PagerAdapter {

	private final Context context;
	Logger LOGGER = LoggerFactory.getLogger(DayPagerAdapter.class
			.getSimpleName());

	ArrayList<MenuListAdapter> adapters = new ArrayList<MenuListAdapter>();

	public DayPagerAdapter(final Context context) {
		super();
		Object[] params = { context };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("DayPagerAdapter({})", params);
		}
		this.context = context;
		for (int i = 0; i < 5; i++) {
			adapters.add(null);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Created DayPagerAdapter({})", params);
		}
	}

	@Override
	public int getCount() {
		int result = 5;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getCount() -> {}", result);
		}
		return result;
	}

	@Override
	public Object instantiateItem(final View pager, final int position) {
		Object[] params = { pager, position };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("instantiateItem({},{})", params);
		}
		final ListView v = new ListView(context);

		adapters.add(position,
				new MenuListAdapter(context, R.layout.menu_list,
						ServiceContainer.getInstance().getContext()
								.getAvailableDates()[position]));

		v.setAdapter(adapters.get(position));
		((ViewPager) pager).addView(v, 0);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("instantiateItem({},{}) -> {}", params, v);
		}
		return v;
	}

	@Override
	public void destroyItem(final View pager, final int position,
			final Object view) {
		Object[] params = { pager, position, view };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("destroyItem({},{},{})", params);
		}
		((ViewPager) pager).removeView((View) view);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("destroyItem({},{},{}) -> VOID", params);
		}
	}

	@Override
	public boolean isViewFromObject(final View view, final Object object) {
		Object[] params = { view, object };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("isViewFromObject({},{})", params);
		}
		boolean result = view.equals(object);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("isViewFromObject({},{}) -> {}", params, result);
		}
		return result;
	}

	@Override
	public void finishUpdate(final View view) {
		Object[] params = { view };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("finishUpdate({}) -> VOID", params);
		}
	}

	@Override
	public void restoreState(final Parcelable p, final ClassLoader c) {
		Object[] params = { p, c };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("restoreState({},{}) -> VOID", params);
		}
	}

	@Override
	public Parcelable saveState() {
		Object[] params = {};
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("saveState() -> {}", params, null);
		}
		return null;
	}

	@Override
	public void startUpdate(final View view) {
		Object[] params = { view };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("startUpdate({}) -> VOID", params);
		}
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
		Object[] params = { object };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getItemPosition({}) -> {}", params, POSITION_NONE);
		}
		return POSITION_NONE;
	}

	@Override
	public String toString() {
		return "DayPagerAdapter [context=" + context + ", adapters=" + adapters
				+ "]";
	}
}
