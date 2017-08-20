package com.cjh.cat.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class PicturePagerAdapter extends PagerAdapter {
	
	private List<View> picViewList = null;

	public PicturePagerAdapter(List<View> picViewList) {
		this.picViewList = picViewList;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView(picViewList.get(position));
	}

	@Override
	public Object instantiateItem(View v, int position) {
		((ViewPager) v).addView(picViewList.get(position));
		return picViewList.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getCount() {
		return picViewList.size();
	}
}
