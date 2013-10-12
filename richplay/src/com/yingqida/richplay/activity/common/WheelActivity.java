package com.yingqida.richplay.activity.common;

import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;

import com.yingqida.richplay.R;
import com.yingqida.richplay.widget.ArrayWheelAdapter;
import com.yingqida.richplay.widget.OnWheelChangedListener;
import com.yingqida.richplay.widget.WheelView;

public abstract class WheelActivity extends SuperActivity {
	/**
	 * 单滚轮布局
	 */
	public View singleWheel;

	/**
	 * 双滚轮布局
	 */
	public View doubleWheel;

	/**
	 * 三滚轮布局
	 */
	public View threeWheel;

	/**
	 * 滚轮1
	 */
	public WheelView wheel1;

	/**
	 * 滚轮2
	 */
	public WheelView wheel2;

	/**
	 * 滚轮3
	 */
	public WheelView wheel3;

	/**
	 * 显示单滚轮
	 * 
	 * @param adapter
	 * @param curItem
	 * @param title
	 * @param click
	 * @param hascancel
	 *            TODO
	 */
	public void showSingleWheel(ArrayWheelAdapter<String> adapter, int curItem,
			String title, OnClickListener click, boolean hascancel) {
		singleWheel = LayoutInflater.from(getBaseContext()).inflate(
				R.layout.single_wheel, null);
		wheel1 = (WheelView) singleWheel.findViewById(R.id.wheel);
		wheel1.setAdapter(adapter);
		wheel1.setCurrentItem(curItem);

		if (hascancel) {
			showAlertDialog(0, title, null, singleWheel, click, DEFAULT_BTN,
					null, true);
		} else {
			showAlertDialog(0, title, null, singleWheel, click, null, null,
					false);
		}
	}

	/**
	 * 显示双滚轮
	 * 
	 * @param adapter
	 * @param curItem
	 * @param adapter2
	 * @param curItem2
	 * @param wheelChange
	 * @param title
	 * @param click
	 * @param hascancel
	 *            TODO
	 */
	public void showDoubleWheel(ArrayWheelAdapter<String> adapter, int curItem,
			ArrayWheelAdapter<String> adapter2, int curItem2,
			OnWheelChangedListener wheelChange, String title,
			OnClickListener click, boolean hascancel) {
		doubleWheel = LayoutInflater.from(getBaseContext()).inflate(
				R.layout.double_wheel, null);
		wheel2 = (WheelView) doubleWheel.findViewById(R.id.wheel_2);
		wheel2.setAdapter(adapter2);
		wheel2.setCurrentItem(curItem2);

		wheel1 = (WheelView) doubleWheel.findViewById(R.id.wheel);
		wheel1.setAdapter(adapter);
		wheel1.setCurrentItem(curItem);
		wheel1.addChangingListener(wheelChange);
		if (hascancel) {
			showAlertDialog(0, title, null, doubleWheel, click, DEFAULT_BTN,
					null, true);
		} else {
			showAlertDialog(0, title, null, doubleWheel, click, null, null,
					false);
		}
	}

	/**
	 * 显示三滚轮
	 * 
	 * @param adapter
	 * @param curItem
	 * @param adapter2
	 * @param curItem2
	 * @param adapter3
	 * @param curItem3
	 * @param wheelChange
	 * @param wheelChange2
	 * @param title
	 * @param click
	 */
	public void showThreeWheel(ArrayWheelAdapter<String> adapter, int curItem,
			ArrayWheelAdapter<String> adapter2, int curItem2,
			ArrayWheelAdapter<String> adapter3, int curItem3,
			OnWheelChangedListener wheelChange,
			OnWheelChangedListener wheelChange2, String title,
			OnClickListener click) {
		threeWheel = LayoutInflater.from(getBaseContext()).inflate(
				R.layout.three_wheel, null);
		wheel3 = (WheelView) threeWheel.findViewById(R.id.wheel_3);
		wheel3.setAdapter(adapter3);
		wheel3.setCurrentItem(curItem3);
		wheel2 = (WheelView) threeWheel.findViewById(R.id.wheel_2);
		wheel2.setAdapter(adapter2);
		wheel2.setCurrentItem(curItem2);
		wheel2.addChangingListener(wheelChange2);

		wheel1 = (WheelView) threeWheel.findViewById(R.id.wheel);
		wheel1.setAdapter(adapter);
		wheel1.setCurrentItem(curItem);

		if (null != wheelChange) {
			wheel1.addChangingListener(wheelChange);
		}

		showAlertDialog(0, title, null, threeWheel, click, DEFAULT_BTN, null,
				true);
	}
}
