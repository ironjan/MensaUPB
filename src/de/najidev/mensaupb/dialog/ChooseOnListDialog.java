package de.najidev.mensaupb.dialog;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.actionbarsherlock.app.*;

public class ChooseOnListDialog extends SherlockListActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Intent intent = getIntent();
		this.setTitle(intent.getStringExtra("title"));

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_2, android.R.id.text1,
				intent.getStringArrayExtra("list")));
	}

	@Override
	protected void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		super.onListItemClick(l, v, position, id);

		this.setResult(1, new Intent().putExtra("chosen", position));
		finish();
	}

	@Override
	public void onBackPressed() {
		this.setResult(0);

		super.onBackPressed();
	}
}
