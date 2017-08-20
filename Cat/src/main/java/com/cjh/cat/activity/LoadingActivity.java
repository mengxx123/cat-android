package com.cjh.cat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cjh.cat.R;

/** 加载界面 */
public class LoadingActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(LoadingActivity.this, MakeupActivity.class);
				startActivity(intent);
				finish();
			}

		}, SPLASH_DISPLAY_LENGHT);

	}

}