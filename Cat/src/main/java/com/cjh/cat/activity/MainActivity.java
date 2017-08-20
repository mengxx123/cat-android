package com.cjh.cat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cjh.cat.R;
import com.cjh.cat.util.LogUtil;

/** 主界面 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initMaleButton();
	}

	private void initMaleButton() {
		Button maleButton = (Button) findViewById(R.id.maleButton);
		maleButton.setOnClickListener(new PortraitModeClickListener());
	}

	private class PortraitModeClickListener implements OnClickListener {

		public PortraitModeClickListener() {
			super();
		}

		@Override
		public void onClick(View v) {
			
			LogUtil.d("点击");
			
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, LoadingActivity.class); // 描述起点和目标
			startActivity(intent);
		}

	}
}
