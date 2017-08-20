package com.cjh.cat.svg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class SvgBitmap1
{
	private static final int COLOR_ORIGINAL_PICTURE = 0xFF333333;
	private Bitmap bitmap = null;
	private Canvas canvas = null;
	private String pathString = null;

	public SvgBitmap1(String pathString, int width, int height)
	{
		this(pathString, COLOR_ORIGINAL_PICTURE, width, height);
	}

	public SvgBitmap1(String pathString, int color, int width, int height)
	{
		this.pathString = pathString;
		bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);

		initPicutre(color);
	}

	private void initPicutre(int color)
	{
		Path path = SVGParser.parsePath(pathString);
		Paint paint = new Paint();
		paint.setColor(color);
		canvas.drawPath(path, paint);
	}

	public void changeColor(int color)
	{
		initPicutre(color);
	}

	public Bitmap getBitmap()
	{
		return bitmap;
	}

	public void recycle()
	{
		bitmap.recycle();
	}
}
