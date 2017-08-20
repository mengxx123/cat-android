package com.cjh.cat.table;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.cjh.cat.common.Utils;
import com.cjh.cat.part.PortraitPart;
import com.cjh.cat.util.TableResource;

/** 表格选择项 */
public abstract class ViewForm extends TableLayout implements OnClickListener {
	
	private SelectableView lastView;
	private final static int COLUMN_COUNT = 3;
	protected TableResource tableResource;
	private int columns = COLUMN_COUNT;
	private OnViewSelectedListener[] viewSelectedListeners;
	protected Map<String, SelectableView> viewMap = new HashMap<String, SelectableView>();
	protected TableRow.LayoutParams viewParams;

	public ViewForm(Context context) {
		super(context);
	}

	public ViewForm(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void init(TableResource tableResource, OnViewSelectedListener... listeners) {
		this.tableResource = tableResource;
		this.viewSelectedListeners = listeners;
		init();
	}

	public void setSelected() {
		String id = tableResource.getPortraitPart().getId();
		SelectableView view = viewMap.get(id);
		setSelected(view);
	}

	@Override
	public void onClick(View v) {
		setSelected(v);
	}

	private void setSelected(View view) {
		if (lastView != null) {
			lastView.selected(false);
		}
		lastView = (SelectableView) view;
		lastView.selected(true);

		PortraitPart part = tableResource.getPortraitPart();
		part.setId(lastView.getGraphId());

		notifyListeners(part);
	}

	protected void notifyListeners(PortraitPart part) {
		for (OnViewSelectedListener listener : viewSelectedListeners) {
			listener.onViewSelected(part);
		}
	}

	private void init() {
		setStretchAllColumns(true);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		int h = Utils.dip2px(getContext(), 100);
		//viewParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, h); // TODO
		viewParams = new TableRow.LayoutParams(h, h);

		addRows();
	}

	protected void addRows() {
		int rows = tableResource.getResourceIds().length / columns;
		// 可能不能被整除，所以需要加一行
		for (int row = 0; row <= rows; row++) {
			addRow(row, viewParams);
		}
	}

	private void addRow(int row, TableRow.LayoutParams viewParams) {
		Context context = getContext();
		TableRow tableRow = new TableRow(context);

		int[] resIds = tableResource.getResourceIds();
		// 最后一行可能超出长度，所以需要判断是否超过长度
		for (int index = row * columns; index < resIds.length && index < (row + 1) * columns; index++) {
			SelectableView view = createView(resIds[index]);
			viewMap.put(view.getGraphId(), view);
			tableRow.addView(view);
		}

		addView(tableRow);
	}

	protected abstract SelectableView createView(int resId);

	public void loadImage() {
	}

	public static interface OnViewSelectedListener {
		public void onViewSelected(PortraitPart part);
	}

}
