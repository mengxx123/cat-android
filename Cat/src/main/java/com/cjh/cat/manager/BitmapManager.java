package com.cjh.cat.manager;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class BitmapManager {

	private static final BitmapManager instance = new BitmapManager();

	private LruCache<String, Bitmap> mMemoryCache;

	private BitmapManager() {
		createMemoryCache();
	}

	public static BitmapManager instance() {
		return instance;
	}

	private void createMemoryCache() {
		// 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
		// LruCache通过构造函数传入缓存值，以KB为单位。
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		int cacheSize = maxMemory / 2;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// 重写此方法来衡量每张图片的大小，默认返回图片数量。
				return bitmap.getByteCount() / 1024;
			}
		};
	}

	public void addBitmapToMemoryCache(String id, Bitmap bitmap) {
		if (getBitmapFromMemCache(id) == null) {
			mMemoryCache.put(id, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String id) {
		return mMemoryCache.get(id);
	}
}
