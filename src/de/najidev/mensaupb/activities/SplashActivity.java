package de.najidev.mensaupb.activities;

import com.google.inject.Inject;

import de.najidev.mensaupb.entity.MenuRepository;
import de.najidev.mensaupb.task.PrepareDatabaseTask;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends MainActivity
{
	@Inject
	protected MenuRepository menuRepository;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Handler handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
				finish();
				overridePendingTransition(0, 0);
			}
		};

		if (!menuRepository.hasActualData())
			new PrepareDatabaseTask(handler, this, menuRepository).execute();
		else
			handler.sendEmptyMessage(0);
	}
}
