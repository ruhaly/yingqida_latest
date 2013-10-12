/**
 * 项目名：     realmestore
 * 文件名：     UGallery.java
 * 文件描述： 
 * 作者：         hlr
 * 创建时间：  2012-7-24
 * 版权声明 ： Copyright (C) 2008-2010 RichPeak
 *
 */
package com.yingqida.richplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * 类名称： UGallery 作者： hlr 创建时间： 2012-7-24 下午5:53:45 类描述： 版权声明 ： Copyright (C)
 * 2008-2010 RichPeak 修改时间： 2012-7-24 下午5:53:45
 * 
 */
public class UGallery extends Gallery {

	/**
	 * 构造方法 输入参数： @param context
	 */
	public UGallery(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造方法 输入参数： @param context 输入参数： @param attrs 输入参数： @param defStyle
	 */
	public UGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造方法 输入参数： @param context 输入参数： @param attrs
	 */
	public UGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc) (覆盖方法) 方法名称： onFling 作者： wjd 方法描述：
	 * 
	 * @see android.widget.Gallery#onFling(android.view.MotionEvent,
	 * android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int keyCode;
		if (isScrollingLeft(e1, e2)) {
			keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(keyCode, null);
		return true;

	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}

}
