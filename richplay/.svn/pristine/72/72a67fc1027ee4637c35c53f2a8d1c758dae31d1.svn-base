package com.yingqida.richplay.baseapi.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;

public class ActivityStack {

	private static ActivityStack ins = new ActivityStack();

	public Map<String, Activity> mStack = new LinkedHashMap<String, Activity>();

	private ActivityStack() {

	}

	public static ActivityStack getIns() {
		return ins;
	}

	/**
	 * 方法名称： removeView 作者： wjd 方法描述： 结束界面 输入参数： @param name 输入参数： @return 返回类型：
	 * boolean
	 */
	public boolean removeActivity(String name) {
		if (null == name) {
			return false;
		}
		if (mStack.containsKey(name)) {
			mStack.get(name).finish();
			return true;
		}
		return false;
	}

	/**
	 * 方法名称： popupView 作者： wjd 方法描述： 界面出栈 输入参数： @param name 输入参数： @return 返回类型：
	 * boolean
	 */
	public boolean popupActivity(String name) {
		if (null == name) {
			return false;
		}
		if (mStack.containsKey(name)) {
			mStack.remove(name);
			return true;
		}
		return false;
	}

	/**
	 * 方法名称： pushView 作者： wjd 方法描述： 界面入站 输入参数： @param view 返回类型： void
	 */
	public void pushActivity(Activity view) {
		mStack.remove(view.getClass().getName());
		mStack.put(view.getClass().getName(), view);
	}

	/**
	 * 方法名称： popupAllView 作者： wjd 方法描述： 结束所有界面 输入参数： 返回类型： void
	 */
	public void popupAllActivity() {
		Collection<Activity> views = mStack.values();
		int size = views.size();
		Activity[] viewArray = new Activity[size];
		views.toArray(viewArray);
		for (int i = size - 1; i >= 0; --i) {
			viewArray[i].finish();
		}
		viewArray = null;
		mStack.clear();
	}

	/**
	 * 方法名称： popupUnRelatedViews 作者： wjd 方法描述： 结束所有不相关的界面 输入参数： @param name
	 * 返回类型： void
	 */
	public void popupUnRelatedActivitys(String name) {
		Collection<Activity> views = mStack.values();
		int size = views.size();
		Activity[] viewArray = new Activity[size];
		views.toArray(viewArray);
		for (int i = size - 1; i >= 0; --i) {
			if (!viewArray[i].getClass().getName().equals(name)) {
				viewArray[i].finish();
				mStack.remove(viewArray[i].getClass().getName());
			}
		}
		viewArray = null;
	}

	/**
	 * 方法名称： popupAllActivityUpHome 作者： wjd 方法描述： 将主界面之上的所有界面结束 输入参数： 返回类型： void
	 */
	public void popupAllActivityUpHome() {
		Collection<Activity> views = mStack.values();
		int size = views.size();
		Activity[] viewArray = new Activity[size];
		views.toArray(viewArray);
		for (int i = size - 1; i >= 0; --i) {
			if (!isCertain(viewArray[i].getClass().getName())) {
				viewArray[i].finish();
				mStack.remove(viewArray[i].getClass().getName());
			}
		}
		viewArray = null;
	}

	/**
	 * 方法名称： remain 作者： wjd 方法描述： if the activity name equals the certain
	 * activity 输入参数： @param name The acvitity name 输入参数： @return 返回类型： boolean
	 */
	public boolean isCertain(String name) {
		// return LoginActivity.class.getName().equals(name)
		// || HomeActivity.class.getName().equals(name)
		// || GroupsActivity.class.getName().equals(name)
		// || PDetailActivity.class.getName().equals(name)
		// || SearchActivity.class.getName().equals(name)
		// || SettingActivity.class.getName().equals(name);
		return true;
	}

	/**
	 * 方法名称： getTopView 作者： wjd 方法描述： 获得栈顶界面 输入参数： @return 返回类型： Activity
	 */
	public Activity getTopActivity() {
		if (mStack.isEmpty()) {
			return null;
		}
		String name = "";
		Iterator<String> keys = mStack.keySet().iterator();
		while (keys.hasNext()) {
			name = keys.next();
		}
		return mStack.get(name);
	}

	/**
	 * 方法名称： containsView 作者： wjd 方法描述： 判断界面栈是否包含界面 输入参数： @param name 输入参数： @return
	 * 返回类型： boolean
	 */
	public boolean containsActivity(String name) {
		return mStack.containsKey(name);
	}

	/**
	 * 方法名称： getView 作者： wjd 方法描述： 获得指定界面 输入参数： @param name 输入参数： @return 返回类型：
	 * Activity
	 */
	public Activity getActivity(String name) {
		return mStack.get(name);
	}

	public String topActivityName() {
		if (mStack.isEmpty()) {
			return null;
		}
		String name = "";
		Iterator<String> keys = mStack.keySet().iterator();
		while (keys.hasNext()) {
			name = keys.next();
		}
		return name;
	}
}