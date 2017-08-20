package com.cjh.cat.table;

import android.content.Context;
import android.util.AttributeSet;

/** 颜色类型的表格选择项 */
public class ColorForm extends ViewForm {

	public ColorForm(Context context) {
		super(context);
	}

	public ColorForm(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected SelectableView createView(int resId) {
		SelectableView view = new SelectableView(getContext(), getId(resId));
		view.setLayoutParams(viewParams);
		view.setBackgroundColor(getResources().getColor(resId));
		view.setOnClickListener(this);
		return view;
	}

	private String getId(int resid) {
		int color = getResources().getColor(resid) + 0x1000000;
		return '#' + Integer.toHexString(color);
	}
}
