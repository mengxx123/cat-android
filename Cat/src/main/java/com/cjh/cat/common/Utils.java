package com.cjh.cat.common;

import org.dom4j.Document;

import com.cjh.cat.svg.SVGParser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class Utils {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static Bitmap saveViewBitmap(View view) {
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache(true);
		Bitmap cacheBitmap = view.getDrawingCache(true);

		Bitmap destBitmap = Bitmap.createBitmap(cacheBitmap);
		cacheBitmap.recycle();
		view.setDrawingCacheEnabled(false);
		return destBitmap;
	}

	/**
	 * 从svg文档中创建bitmap
	 * @param document
	 * @return
	 */
	public static Bitmap createBitmap(Document document) {
		Bitmap bitmap = Bitmap.createBitmap(640, 640, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawPicture(SVGParser.getSVGFromString(document.asXML(), 0, 0).getPicture());
		return bitmap;
	}
}