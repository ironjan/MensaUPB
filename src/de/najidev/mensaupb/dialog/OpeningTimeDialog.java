package de.najidev.mensaupb.dialog;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

import de.najidev.mensaupb.R;

public class OpeningTimeDialog extends SherlockActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setTitle("Öffnungszeiten");
        
        setContentView(R.layout.openingtime_dialog);
	}
}