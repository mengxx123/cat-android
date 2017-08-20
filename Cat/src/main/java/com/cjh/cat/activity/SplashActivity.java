package com.cjh.cat.activity;

import com.cjh.cat.manager.DataManager;
import com.cjh.cat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

/** 欢迎页 */
public class SplashActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		AsyncTask.execute(new Runnable() {

			@Override
			public void run() {
				DataManager.instance().init(SplashActivity.this);
			}
		});

		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(mainIntent);
				finish();
			}

		}, SPLASH_DISPLAY_LENGHT);

	}
}