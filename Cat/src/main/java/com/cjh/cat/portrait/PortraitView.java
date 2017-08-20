package com.cjh.cat.portrait;

import static com.cjh.cat.part.PortraitPart.EAR;
import static com.cjh.cat.part.PortraitPart.EXPRESSION;
import static com.cjh.cat.part.PortraitPart.EYE;
import static com.cjh.cat.part.PortraitPart.FACE;
import static com.cjh.cat.part.PortraitPart.HAIR_BG;
import static com.cjh.cat.part.PortraitPart.MOUTH;
import static com.cjh.cat.part.PortraitPart.NOSE;
import static com.cjh.cat.part.PortraitPart.WHISKERS;

import java.util.List;

import org.dom4j.Document;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.cjh.cat.common.Utils;
import com.cjh.cat.manager.DataManager;
import com.cjh.cat.manager.MementoManager;
import com.cjh.cat.part.PortraitPart;

public class PortraitView extends ImageView {

	public PortraitView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PortraitView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PortraitView(Context context) {
		super(context);
		init();
	}

	private void init() {
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	private void invaidatePortrait(Document portraitDocument) {
		Bitmap portraitBitmap = Utils.createBitmap(portraitDocument);
		drawBubble(portraitBitmap);
		setImageBitmap(portraitBitmap);
	}

	private void drawBubble(Bitmap bitmap) {
		Canvas canvas = new Canvas(bitmap);
		List<String> bubbleStrings = DataManager.instance().getBubbleStrings();
		if (bubbleStrings.size() == 0) {
			return;
		}
		if (PortraitPart.BUBBLE.isEditable()) {
			Paint paint = new Paint();
			paint.setTextSize(40);
			if (bubbleStrings.size() == 1) {
				canvas.drawText(bubbleStrings.get(0), 380, 140, paint);
			} else if (bubbleStrings.size() == 2) {
				canvas.drawText(bubbleStrings.get(0), 380, 120, paint);
				canvas.drawText(bubbleStrings.get(1), 380, 160, paint);
			} else {
				canvas.drawText(bubbleStrings.get(0), 380, 80, paint);
				canvas.drawText(bubbleStrings.get(1), 380, 140, paint);
				canvas.drawText(bubbleStrings.get(2), 380, 200, paint);
			}
		}
	}

	public void onViewSelected(PortraitPart part) {
		DataManager dataManager = DataManager.instance();
		switch (part) {
		case EYE_COLOR:
			invaidatePortrait(dataManager.updatePortrait(EYE));
			break;
		case HAIR_COLOR:
			invaidatePortrait(dataManager.updatePortrait(EAR, HAIR_BG));
			break;
		case SKIN_COLOR:
			invaidatePortrait(dataManager.updatePortrait(FACE, EAR));
			break;
		case EAR:
			HAIR_BG.setId(EAR.getId());
			invaidatePortrait(dataManager.updatePortrait(EAR, HAIR_BG));
			break;
		case EXPRESSION:
			PortraitPart[] parts = { WHISKERS, EYE, NOSE, MOUTH, EXPRESSION };
			if (EXPRESSION.isOrignal()) {
				invaidatePortrait(dataManager.updatePortrait(parts));
			} else {
				// 显示表情时，不显示眼睛、鼻子和嘴巴
				EYE.setId("eye00");
				NOSE.setId("nose00");
				MOUTH.setId("mouth00");
				
				invaidatePortrait(dataManager.updatePortrait(parts));
				MementoManager.instance().restoreMemento(parts);
				MementoManager.instance().addMemento(parts);
			}
			break;
		default:
			invaidatePortrait(dataManager.updatePortrait(part));
			break;
		}
	}
}
