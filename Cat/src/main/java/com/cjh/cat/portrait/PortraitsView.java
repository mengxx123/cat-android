package com.cjh.cat.portrait;

import static com.cjh.cat.part.PortraitPart.BACKGROUND;
import static com.cjh.cat.part.PortraitPart.BUBBLE;
import static com.cjh.cat.part.PortraitPart.EAR;
import static com.cjh.cat.part.PortraitPart.EXPRESSION;
import static com.cjh.cat.part.PortraitPart.EYE;
import static com.cjh.cat.part.PortraitPart.FACE;
import static com.cjh.cat.part.PortraitPart.FEATURE;
import static com.cjh.cat.part.PortraitPart.FOOT;
import static com.cjh.cat.part.PortraitPart.MOUTH;
import static com.cjh.cat.part.PortraitPart.NOSE;
import static com.cjh.cat.part.PortraitPart.OTHER;
import static com.cjh.cat.part.PortraitPart.WHISKERS;

import org.dom4j.Document;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.cjh.cat.common.Utils;
import com.cjh.cat.manager.DataManager;
import com.cjh.cat.manager.MementoManager;
import com.cjh.cat.part.PortraitPart;
import com.cjh.cat.table.ViewForm.OnViewSelectedListener;

/** 部件显示控件（预览效果） */
public class PortraitsView extends LinearLayout implements OnViewSelectedListener {
	
	private PortraitView portraitView;
	private GenderSeletedListener genderSeletedListener;
	private Bitmap bubbleBitmap;

	public PortraitsView(Context context) {
		super(context);
	}

	public PortraitsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PortraitsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void init(GenderSeletedListener listener) {
		this.genderSeletedListener = listener;

		setGravity(Gravity.CENTER_HORIZONTAL);

		initSingleModeView();
		initListener();
	}

	private void initListener() {

		post(new Runnable() {

			@Override
			public void run() {
				PortraitPart[] parts = { EAR, FACE, WHISKERS, EYE, MOUTH, NOSE, FEATURE, OTHER, FOOT, 
						EXPRESSION, BUBBLE };
				MementoManager.instance().restoreLeftPortraitMemento(parts);
				genderSeletedListener.onGenderSelected();
			}
		});

	}

	private void initSingleModeView() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		portraitView = new PortraitView(getContext());
		portraitView.setLayoutParams(params);
		addView(portraitView);
	}

	private void invaidateBackground(Document bgDocument) {
		Bitmap bgBitmap = Utils.createBitmap(bgDocument);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bgBitmap);
		setBackground(bitmapDrawable);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (bubbleBitmap!=null) {
			canvas.drawBitmap(bubbleBitmap, 0, 0, null);
		}
	}

	@Override
	public void onViewSelected(PortraitPart part) {
		DataManager dataManager = DataManager.instance();
		switch (part) {
		case BACKGROUND:
			invaidateBackground(dataManager.updateBackground(BACKGROUND));
			break;
		case BUBBLE:
			bubbleBitmap  = Utils.createBitmap(dataManager.updateBubble(BUBBLE));
			invalidate();
			break;

		default:
			portraitView.onViewSelected(part);
			break;
		}
	}

	public static interface GenderSeletedListener {
		public void onGenderSelected();
	}
}
