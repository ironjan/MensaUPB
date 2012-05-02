package de.najidev.mensaupb.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;

public class ChooseOnListDialog extends SherlockListActivity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		this.setTitle(intent.getStringExtra("title"));

		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, intent.getStringArrayExtra("list")));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);

		this.setResult(1, new Intent().putExtra("chosen", position));
		this.finish();
	}
	
	@Override
	public void onBackPressed()
	{
		this.setResult(0);
		
		super.onBackPressed();
	}
}
