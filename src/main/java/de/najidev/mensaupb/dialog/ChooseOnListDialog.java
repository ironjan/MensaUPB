package de.najidev.mensaupb.dialog;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;

import org.slf4j.*;

import de.najidev.mensaupb.activities.*;

public class ChooseOnListDialog extends ListFragment {

	public static final String EXTRA_KEY_LIST = "list",
			EXTRA_KEY_TITLE = "title";

	Logger LOGGER = LoggerFactory.getLogger(ChooseOnListDialog.class
			.getSimpleName());

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onCreate({})", savedInstanceState);
		}

        if(getArguments() != null && getArguments().containsKey(EXTRA_KEY_TITLE)){
            getActivity().setTitle(getArguments().getString(EXTRA_KEY_TITLE));
        }

		if(getArguments() != null && getArguments().containsKey(EXTRA_KEY_LIST)){
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_2, android.R.id.text1,
                    getArguments().getStringArray(EXTRA_KEY_LIST)));
        }

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onCreate({}) -> VOID", savedInstanceState);
		}
	}

	@Override
    public void onListItemClick(final ListView l, final View v,
                                final int position, final long id) {
		Object[] params = { l, v, position, id };
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onListItemClick({},{},{},{})", params);
		}
		super.onListItemClick(l, v, position, id);

		getActivity().setResult(1, new Intent().putExtra(
				MainActivity.EXTRA_KEY_CHOSEN_LOCATION, position));
        getActivity().finish();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("onListItemClick({},{},{},{}) -> VOID", params);
		}
	}

//	@Override
//	public void onBackPressed() {
//		if (LOGGER.isDebugEnabled()) {
//			LOGGER.debug("onBackPressed()");
//		}
//
//        getActivity().setResult(0);
//
//		if (LOGGER.isDebugEnabled()) {
//			LOGGER.debug("onBackPressed() -> VOID");
//		}
//
//        getActivity().onBackPressed();
//	}

	@Override
	public String toString() {
		return "ChooseOnListDialog []";
	}
}
