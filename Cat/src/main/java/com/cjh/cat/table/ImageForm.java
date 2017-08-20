package com.cjh.cat.table;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.util.AttributeSet;

import com.cjh.cat.manager.BitmapManager;
import com.cjh.cat.util.TableResource;
import com.cjh.cat.R;

/** 图像类型的表格选择项 */
public class ImageForm extends ViewForm {

	public ImageForm(Context context) {
		super(context);
	}

	public ImageForm(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void init(TableResource tableResource, OnViewSelectedListener... listeners) {
		super.init(tableResource, listeners);

	}

	@Override
	protected SelectableView createView(int resId) {
		SelectableView view = new SelectableView(getContext(), getId(resId));
		view.setLayoutParams(viewParams);
		view.setImageResource(R.drawable.pic_loadrefresh);
		view.setOnClickListener(this);
		return view;
	}

	private String getId(int resid) {
		return getResources().getResourceEntryName(resid);
	}

	@Override
	public void loadImage() {
		BitmapWorkerTask task = new BitmapWorkerTask();
		task.execute();
	}

	private class BitmapWorkerTask extends AsyncTask<Void, String, Void> {
		private Options options = new Options();
		{
			options.inPreferredConfig = Bitmap.Config.ALPHA_8;
		}

		@Override
		protected Void doInBackground(Void... params) {
			for (int id : tableResource.getResourceIds()) {
				String idString = getId(id);
				Bitmap bmp = BitmapManager.instance().getBitmapFromMemCache(idString);
				if (bmp == null) {
					InputStream is = getResources().openRawResource(id);
					bmp = BitmapFactory.decodeStream(is, null, options);
					BitmapManager.instance().addBitmapToMemoryCache(idString, bmp);
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				publishProgress(idString);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			String id = values[0];
			viewMap.get(id).setImageBitmap(BitmapManager.instance().getBitmapFromMemCache(id));
		}
	}
}
