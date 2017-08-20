package com.cjh.cat.svg;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SvgBitmap {

	private static final int COLOR_ORIGINAL_PICTURE = 0xFF333333;
	private Bitmap bitmap = null;
	private Canvas canvas = null;
	private Context context = null;
	private int rawResourceId = 0;

	public SvgBitmap(Context context, int rawResourceId, int width, int height) {
		this(context, rawResourceId, COLOR_ORIGINAL_PICTURE, width, height);
	}

	public SvgBitmap(Context context, int rawResourceId, int color, int width, int height) {
		this.context = context;
		this.rawResourceId = rawResourceId;
		bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);

		initPicutre(color);
	}

	private void initPicutre(int color) {
		InputStream svgResource = context.getResources().openRawResource(rawResourceId);
		SVG svg = SVGParser.getSVGFromInputStream(svgResource, COLOR_ORIGINAL_PICTURE, color, bitmap.getWidth(),
				bitmap.getHeight());
		try {
			svgResource.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		canvas.drawPicture(svg.getPicture());
	}

	public void changeColor(int color) {
		initPicutre(color);
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void recycle() {
		bitmap.recycle();
	}
}
