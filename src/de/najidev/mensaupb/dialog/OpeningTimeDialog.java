package de.najidev.mensaupb.dialog;

import android.os.*;

import com.actionbarsherlock.app.*;

import de.najidev.mensaupb.*;

public class OpeningTimeDialog extends SherlockActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Öffnungszeiten");

		setContentView(R.layout.openingtime_dialog);
	}
}