package de.najidev.mensaupb.dialogs;

import de.najidev.mensaupb.R;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

public class OpeningTimeDialog extends Dialog 
{
	
	public OpeningTimeDialog(Context context) 
	{
		super(context);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.openingtime_dialog);
	}

}
