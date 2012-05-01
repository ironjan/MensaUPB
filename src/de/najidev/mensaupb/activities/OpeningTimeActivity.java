package de.najidev.mensaupb.activities;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

import de.najidev.mensaupb.R;

public class OpeningTimeActivity extends SherlockActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setTitle("Öffnungszeiten");
        
        setContentView(R.layout.openingtime_dialog);
	}
}