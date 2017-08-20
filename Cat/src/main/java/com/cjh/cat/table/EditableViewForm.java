package com.cjh.cat.table;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.cjh.cat.common.Utils;
import com.cjh.cat.manager.DataManager;
import com.cjh.cat.part.PortraitPart;
import com.cjh.cat.util.TableResource;
import com.cjh.cat.R;

/** 可编辑的图像类型的表格选择项 */
public class EditableViewForm extends ImageForm {
	
	private static final int MAX_STRING_COUNT = 30;
	private Button button = null;
	private EditText editText = null;
	private PortraitPart part = null;
	private LinearLayout layout = null;

	public EditableViewForm(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EditableViewForm(Context context) {
		super(context);
	}

	@Override
	public void init(TableResource tableResource, OnViewSelectedListener... listeners) {
		super.init(tableResource, listeners);
	}

	@Override
	protected void addRows() {
		TableRow tableRow = createRow();
		addView(tableRow);
		super.addRows();
	}

	private TableRow createRow() {
		TableRow tableRow = new TableRow(getContext());
		TableRow.LayoutParams params = createRowParams();
		params.setMargins(5, 5, 5, 5);
		LinearLayout enterEdit = createLinearLayout();
		enterEdit.setLayoutParams(params);
		tableRow.addView(enterEdit);
		return tableRow;
	}

	private LinearLayout createLinearLayout() {
		layout = new LinearLayout(getContext());
		layout.setOrientation(LinearLayout.HORIZONTAL);
		editText = createEditText();
		layout.addView(editText);
		button = creatButton();
		layout.addView(button);
		return layout;
	}

	private Button creatButton() {
		Button button = new Button(getContext());
		int w = Utils.dip2px(getContext(), 70);
		int h = Utils.dip2px(getContext(), 40);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
		params.gravity = Gravity.CENTER_VERTICAL;
		button.setLayoutParams(params);
		button.setBackgroundResource(R.drawable.bt_default_selector);
		button.setText(R.string.enter);
		button.setTextSize(20);

		button.setOnClickListener(createButtonListerner());
		return button;
	}

	private OnClickListener createButtonListerner() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				notifyBubbleString();
			}

		};
	}

	private void notifyBubbleString() {
		String bubbleString = editText.getText().toString();
		if (bubbleString.length() > MAX_STRING_COUNT) {
			Toast.makeText(getContext(), R.string.tip_max_bubble, Toast.LENGTH_SHORT).show();
			return;
		}
		DataManager.instance().setBubbleEdit(bubbleString);
		super.notifyListeners(part);
	}

	private EditText createEditText() {
		EditText editText = new EditText(getContext());
		editText.setSingleLine();
		editText.setEms(MAX_STRING_COUNT);
		editText.setTextSize(25);
		int h = Utils.dip2px(getContext(), 40);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, h);
		params.gravity = Gravity.CENTER_VERTICAL;
		params.weight = 1;
		editText.setLayoutParams(params);
		return editText;
	}

	private TableRow.LayoutParams createRowParams() {
		TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		params.span = 3;
		return params;
	}

	@Override
	protected void notifyListeners(PortraitPart part) {
		if (part.isEditable()) {
			layout.setVisibility(View.VISIBLE);
		} else {
			layout.setVisibility(View.GONE);
		}
		this.part = part;

		super.notifyListeners(part);
	}
}
