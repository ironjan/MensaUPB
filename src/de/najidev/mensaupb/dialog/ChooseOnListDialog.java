package de.najidev.mensaupb.dialog;

import org.slf4j.*;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.actionbarsherlock.app.*;

import de.najidev.mensaupb.activities.*;

public class ChooseOnListDialog extends SherlockListActivity {

	public static final String EXTRA_KEY_LIST = "list",
			EXTRA_KEY_TITLE = "title";

	Logger LOGGER = LoggerFactory.getLogger(ChooseOnListDialog.class
			.getSimpleName());

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onCreate({})", savedInstanceState);
		}

		final Intent intent = getIntent();
		this.setTitle(intent.getStringExtra(EXTRA_KEY_TITLE));

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_2, android.R.id.text1,
				intent.getStringArrayExtra(EXTRA_KEY_LIST)));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onCreate({}) -> VOID", savedInstanceState);
		}
	}

	@Override
	protected void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		Object[] params = { l, v, position, id };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onListItemClick({},{},{},{})", params);
		}
		super.onListItemClick(l, v, position, id);

		this.setResult(1, new Intent().putExtra(
				MainActivity.EXTRA_KEY_CHOSEN_LOCATION, position));
		finish();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onListItemClick({},{},{},{}) -> VOID", params);
		}
	}

	@Override
	public void onBackPressed() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onBackPressed()");
		}

		this.setResult(0);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onBackPressed() -> VOID");
		}

		super.onBackPressed();
	}

	@Override
	public String toString() {
		return "ChooseOnListDialog []";
	}
}
