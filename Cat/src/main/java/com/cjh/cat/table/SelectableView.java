package com.cjh.cat.table;

import com.cjh.cat.R;
import com.cjh.cat.R.color;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SelectableView extends ImageView {
	private Paint paint = new Paint();
	private int defaultColor = 0;
	private int selectedColor = 0;
	private String id = null;

	public SelectableView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public SelectableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SelectableView(Context context, String id) {
		super(context);
		this.id = id;
		init();
	}

	private void init() {
		defaultColor = getResources().getColor(R.color.color_picture_border);
		selectedColor = getResources().getColor(R.color.color_picture_selected);
		paint.setColor(defaultColor);
		paint.setStrokeWidth(1);
		paint.setStyle(Style.STROKE);
	}

	public void selected(boolean selected) {
		if (selected) {
			paint.setColor(selectedColor);
			paint.setStrokeWidth(6);
		} else {
			paint.setColor(defaultColor);
			paint.setStrokeWidth(1);
		}
		invalidate();
	}

	public String getGraphId() {
		return id;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
	}

}
