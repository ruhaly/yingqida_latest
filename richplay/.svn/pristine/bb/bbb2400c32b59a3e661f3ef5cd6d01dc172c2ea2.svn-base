/**
 * 项目名：     rimi
 * 文件名：     DibbleActivity.java
 * 文件描述： 
 * 作者：         wjd
 * 创建时间：  2011-12-13
 * 版权声明 ： Copyright (C) 2008-2010 RichPeak
 *
 */
package com.yingqida.richplay.activity.common;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.yingqida.richplay.R;

/**
 * 类名称： DibbleActivity 作者： wjd 创建时间： 2011-12-13 上午11:07:21 类描述： 版权声明 ： Copyright
 * (C) 2008-2010 RichPeak 修改时间： 2011-12-13 上午11:07:21
 * 
 */
public abstract class TabSuperActivity extends SuperActivity {

	private static final String STATES_KEY = "android:states";

	private LocalActivityManager mLocalActivityManager;

	public TabHost mTabHost;

	/**
	 * 构造方法 输入参数：
	 */
	public TabSuperActivity() {
		mLocalActivityManager = new LocalActivityManager(this, true);
	}

	/*
	 * (non-Javadoc) (覆盖方法) 方法名称： onCreate 作者： wjd 方法描述：
	 * 
	 * @see com.richpeak.rimi.ui.SuperActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Bundle states = savedInstanceState != null ? (Bundle) savedInstanceState
				.getBundle(STATES_KEY) : null;
		mLocalActivityManager.dispatchCreate(states);
		super.onCreate(savedInstanceState);
	}

	private void ensureTabHost() {
		if (mTabHost == null) {
			this.setContentView(R.layout.dibble_tab);
		}
	}

	public Activity getCurrentActivity() {
		return mLocalActivityManager.getCurrentActivity();
	}

	public final LocalActivityManager getLocalActivityManager() {
		return mLocalActivityManager;
	}

	/**
	 * Returns the {@link TabHost} the activity is using to host its tabs.
	 * 
	 * @return the {@link TabHost} the activity is using to host its tabs.
	 */
	public TabHost getTabHost() {
		ensureTabHost();
		return mTabHost;
	}

	/**
	 * Returns the {@link TabWidget} the activity is using to draw the actual
	 * tabs.
	 * 
	 * @return the {@link TabWidget} the activity is using to draw the actual
	 *         tabs.
	 */
	public TabWidget getTabWidget() {
		return mTabHost.getTabWidget();
	}

	/**
	 * 方法名称：invoke 作者：liming 方法描述：通过反射把方法调到框架中的activity当中去 输入参数：@param obj 实例对象
	 * 输入参数：@param method 对象中方法名称 输入参数：@param classParas 方法中的参数类型 输入参数：@param
	 * objParas 参数变量值 输入参数：@return 返回类型：Object： 备注：
	 */
	public Object invoke(String method, Class<?>[] classParas,
			Object... objParas) {
		if (null != classParas && null != objParas
				&& classParas.length != objParas.length) {
			throw new IllegalArgumentException();
		}
		try {
			Method m = getCurrentActivity().getClass().getDeclaredMethod(
					method, classParas);
			m.setAccessible(true);
			return m.invoke(getCurrentActivity(), objParas);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Updates the screen state (current list and other views) when the content
	 * changes.
	 * 
	 * @see Activity#onContentChanged()
	 */
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		if (mTabHost == null) {
			throw new RuntimeException(
					"Your content must have a TabHost whose id attribute is "
							+ "'android.R.id.tabhost'");
		}
		mTabHost.setup(mLocalActivityManager);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mLocalActivityManager.dispatchDestroy(isFinishing());
	}

	/**
	 * (non-Javadoc) 方法名称：onNewIntent 作者：liming 方法描述： 输入参数：@param intent 返回类型：
	 * 
	 * @see android.app.Activity#onNewIntent(android.content.Intent) 备注：
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		invoke("onNewIntent", new Class<?>[] { Intent.class }, intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fengrun.realmestore.activity.common.SuperActivity#onConfigurationChanged
	 * (android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		invoke("onConfigurationChanged",
				new Class<?>[] { Configuration.class }, newConfig);
		super.onConfigurationChanged(newConfig);
	}

	/*
	 * (non-Javadoc) (覆盖方法) 方法名称： onTouchEvent 作者： wjd 方法描述：
	 * 
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		invoke("onTouchEvent", new Class<?>[] { MotionEvent.class }, event);
		return false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		mLocalActivityManager.dispatchPause(isFinishing());
	}

	/**
	 * (non-Javadoc) 方法名称：onPostCreate 作者：liming 方法描述： 输入参数：@param icicle 返回类型：
	 * 
	 * @see android.app.Activity#onPostCreate(android.os.Bundle) 备注：
	 */
	@Override
	protected void onPostCreate(Bundle icicle) {
		super.onPostCreate(icicle);
		ensureTabHost();
		if (mTabHost.getCurrentTab() == -1) {
			mTabHost.setCurrentTab(0);
		}
		if (getIntent().getBooleanExtra("tag", false)) {
			getCurrentActivity().setIntent(getIntent());
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mLocalActivityManager.dispatchResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Bundle state = mLocalActivityManager.saveInstanceState();
		if (state != null) {
			outState.putBundle(STATES_KEY, state);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mLocalActivityManager.dispatchStop();
	}

	/*
	 * (non-Javadoc) (覆盖方法) 方法名称： initLayout 作者： wjd 方法描述：
	 * 
	 * @see com.richpeak.rimi.ui.SuperActivity#initLayout()
	 */
	@Override
	public void initLayout() {
		ensureTabHost();
	}

	/*
	 * (non-Javadoc) (覆盖方法) 方法名称： initData 作者： wjd 方法描述：
	 * 
	 * @see com.richpeak.rimi.ui.SuperActivity#initData()
	 */
	@Override
	public void initData() {

	}
}
