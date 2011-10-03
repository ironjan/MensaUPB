package de.najidev.mensaupb.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import de.najidev.mensaupb.entity.MenuRepository;

import roboguice.util.RoboAsyncTask;

public class PrepareDatabaseTask extends RoboAsyncTask<Void>
{
	protected MenuRepository menuRepository;
	protected Context context;
	protected ProgressDialog dialog;
	protected Handler onFinishHandler;

	public PrepareDatabaseTask(Handler onFinishHandler, Context context, MenuRepository menuRepository)
	{
		this.onFinishHandler = onFinishHandler;
		this.context         = context;
		this.menuRepository  = menuRepository;
	}

	public Void call() throws Exception
	{
		menuRepository.fetchActualData();
		return null;
	}

	@Override
	protected void onPreExecute() throws Exception
	{
		dialog = ProgressDialog.show(context, "", "Download des Mensaplans...", true);
	}

	@Override
	protected void onFinally() throws RuntimeException
	{
		onFinishHandler.sendEmptyMessage(0);

		if (dialog.isShowing())
			dialog.dismiss();
	}
}
