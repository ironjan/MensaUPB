package de.najidev.mensaupb.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import de.najidev.mensaupb.R;

public class OpeningTimeDialog extends Dialog
{
	public OpeningTimeDialog(Context context) 
	{
		super(context);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.openingtime_dialog);
	}
}
