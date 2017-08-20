package com.cjh.cat.table;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;

import com.cjh.cat.R;
import com.cjh.cat.manager.MementoManager;
import com.cjh.cat.part.PortraitPart;
import com.cjh.cat.portrait.PortraitsView.GenderSeletedListener;
import com.cjh.cat.table.ViewForm.OnViewSelectedListener;
import com.cjh.cat.util.TableResource;

/** 部件选择控件 */
public class ScrollTable extends FrameLayout implements OnViewSelectedListener, GenderSeletedListener {
	
	private RadioGroup titleButtonGroup; 
	private Map<PortraitPart, ViewForm> viewFormMap = new HashMap<PortraitPart, ViewForm>();

	public ScrollTable(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollTable(Context context) {
		super(context);
	}

	public void setDefaultSelectedTitle(int titleIndex) {
		titleButtonGroup.getChildAt(titleIndex).performClick();
	}

	public void init(List<TableResource> tableResources, OnViewSelectedListener listener) {
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		layoutInflater.inflate(R.layout.scrolltable, this);
		titleButtonGroup = (RadioGroup) findViewById(R.id.titleGroup);

		ViewPager viewPager = getViewPager(tableResources, this, listener);
		initTileButtonGroup(tableResources, layoutInflater, viewPager);

	}

	@Override
	public void onGenderSelected() {
		// 
		setViewSelected(EAR, FACE, WHISKERS, EYE, MOUTH, NOSE, FEATURE, OTHER, FOOT, BACKGROUND,
				EXPRESSION, BUBBLE);
	}

	@Override
	public void onViewSelected(PortraitPart part) {
		MementoManager.instance().addMemento(part);
		switch (part) {
		case WHISKERS:
		case EYE:
		case NOSE:
		case MOUTH:
			if (!EXPRESSION.isOrignal()) {
				EXPRESSION.restore();
				setViewSelected(EXPRESSION);
			}
			break;
		default:
			break;
		}
	}

	private void setViewSelected(PortraitPart... parts) {
		for (PortraitPart part : parts) {
			viewFormMap.get(part).setSelected();
		}
	}

	private ViewPager getViewPager(List<TableResource> tableResources, OnViewSelectedListener... listeners) {
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		List<View> viewList = createViewList(tableResources, listeners);
		viewPager.setAdapter(new ViewPagerAdapter(viewList));
		viewPager.setOnPageChangeListener(new ViewPageChangeListener(titleButtonGroup));
		return viewPager;
	}

	private List<View> createViewList(List<TableResource> tableResources, OnViewSelectedListener... listeners) {
		List<View> viewList = new ArrayList<View>();
		for (TableResource tableResource : tableResources) {
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			ScrollView scrollView = new ScrollView(getContext());
			scrollView.setFillViewport(true);
			scrollView.setLayoutParams(params);
			ViewForm viewForm = createViewForm(tableResource);
			viewForm.init(tableResource, listeners);
			scrollView.addView(viewForm);
			viewList.add(scrollView);

			viewFormMap.put(tableResource.getPortraitPart(), viewForm);
		}
		return viewList;
	}

	private ViewForm createViewForm(TableResource tableResource) {
		ViewForm viewForm = null;
		PortraitPart part = tableResource.getPortraitPart();
		if (part.equals(PortraitPart.HAIR_COLOR) || part.equals(PortraitPart.SKIN_COLOR)
				|| part.equals(PortraitPart.EYE_COLOR)) {
			viewForm = new ColorForm(getContext());
		} else if (part.equals(PortraitPart.BUBBLE)) {
			viewForm = new EditableViewForm(getContext());
		} else {
			viewForm = new ImageForm(getContext());
		}
		return viewForm;
	}

	private void initTileButtonGroup(List<TableResource> tableResources, LayoutInflater layoutInflater,
			ViewPager viewPager) {
		for (TableResource tableResource : tableResources) {
			titleButtonGroup.addView(createTitleButton(layoutInflater, tableResource));
		}

		ImageView selectedImageView = (ImageView) findViewById(R.id.selectedImageView);
		HorizontalScrollView titleHSV = (HorizontalScrollView) findViewById(R.id.titleHSV);
		TitleCheckedChangeListener listener = new TitleCheckedChangeListener(titleHSV, viewPager, selectedImageView);
		titleButtonGroup.setOnCheckedChangeListener(listener);
	}

	private RadioButton createTitleButton(LayoutInflater layoutInflater, TableResource tableResource) {
		RadioButton titleButton = (RadioButton) layoutInflater.inflate(R.layout.title_button, null);
		titleButton.setText(tableResource.getTitle());
		return titleButton;
	}

	private void loadImage(ViewForm form) {
		form.loadImage();
	}

	private class TitleCheckedChangeListener implements OnCheckedChangeListener {
		private float lastXFromLeft = 0.0f; // 当前被选中的RadioButton距离左侧的距离
		private int currentButtonIndex = -1;
		private HorizontalScrollView titleHSV = null;
		private ViewPager viewPager = null;
		private ImageView imageView = null;

		public TitleCheckedChangeListener(HorizontalScrollView titleHSV, ViewPager viewPager, ImageView imageView) {
			this.titleHSV = titleHSV;
			this.viewPager = viewPager;
			this.imageView = imageView;
		}

		@Override
		public void onCheckedChanged(RadioGroup paramRadioGroup, int checkedId) {
			int index = getCurrentButtonIndex(checkedId);
			if (index == currentButtonIndex) {
				return;
			}

			currentButtonIndex = index;
			View view = findViewById(checkedId);
			view.measure(0, 0);
			int width = view.getMeasuredWidth();
			int currentXFromLeft = width * currentButtonIndex;
			AnimationSet animationSet = new AnimationSet(true);
			TranslateAnimation translateAnimation = new TranslateAnimation(lastXFromLeft, currentXFromLeft, 0f, 0f);
			animationSet.addAnimation(translateAnimation);
			animationSet.setFillBefore(false);
			animationSet.setFillAfter(true);
			animationSet.setDuration(100);
			imageView.startAnimation(animationSet);

			lastXFromLeft = currentXFromLeft;
			viewPager.setCurrentItem(currentButtonIndex);

			titleHSV.smoothScrollTo(currentXFromLeft, 0);
		}

		private int getCurrentButtonIndex(int checkedId) {
			int titleCount = titleButtonGroup.getChildCount();
			int i = 0;
			for (i = 0; i < titleCount; i++) {
				if (titleButtonGroup.getChildAt(i) == findViewById(checkedId)) {
					break;
				}
			}
			return i;
		}
	};

	private class ViewPagerAdapter extends PagerAdapter {
		private List<View> viewList = null;

		public ViewPagerAdapter(List<View> viewList) {
			this.viewList = viewList;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(viewList.get(position));
		}

		@Override
		public Object instantiateItem(View v, int position) {
			loadImage(viewFormMap.get(PortraitPart.values()[position]));
			((ViewPager) v).addView(viewList.get(position));
			return viewList.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return viewList.size();
		}
	}

	private class ViewPageChangeListener implements OnPageChangeListener {
		private RadioGroup radioGroup = null;

		public ViewPageChangeListener(RadioGroup radioGroup) {
			this.radioGroup = radioGroup;
		}

		@Override
		public void onPageSelected(int position) {
			radioGroup.getChildAt(position).performClick();
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	}
}
