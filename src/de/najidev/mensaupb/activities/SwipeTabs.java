package de.najidev.mensaupb.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;		

import com.actionbarsherlock.app.SherlockActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;

import de.najidev.mensaupb.R;
import de.najidev.mensaupb.entity.Menu;

public class SwipeTabs extends Activity
{

	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);

		MyPagerAdapter adapter = new MyPagerAdapter();
		ViewPager myPager = (ViewPager) findViewById(R.id.myfivepanelpager);
		myPager.setAdapter(adapter);
		myPager.setCurrentItem(2);
		
	}
	
	public class MyPagerAdapter extends PagerAdapter
	{
		List<Menu> menuList;

		@Override
		public Object instantiateItem(View collection, int position)
		{
            LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
            menuList = new ArrayList();
            Menu menu = new Menu();
            menu.setTitle(String.valueOf(position));
            
            this.menuList = menuList;
 
            ListView view = (ListView) inflater.inflate(R.layout.menu_list, null);
            view.setBackgroundColor(0x00ff00);
            view.setAdapter(new ArrayAdapter<Menu>(getApplicationContext(), R.layout.menu_list, this.menuList)
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
			});
 
            ((ViewPager) collection).addView(view, 0);
            
            Log.d("najidev", "instantiate");
 
            return view;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
		}
		
		@Override
		public void destroyItem(View container, int position, Object object)
		{
			((ViewPager) container).removeView((View) object);
		}
	}
	
	public static class Tab1 extends Fragment {
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return (LinearLayout)inflater.inflate(R.layout.fragment1, container, false);
		}
	}
}
