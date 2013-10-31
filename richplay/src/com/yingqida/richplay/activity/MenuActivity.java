package com.yingqida.richplay.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.exception.HttpException;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.fragment.MenuFragment;
import com.yingqida.richplay.fragment.PageHomeFragment;
import com.yingqida.richplay.fragment.PersonFragment;
import com.yingqida.richplay.fragment.SearchFragment;
import com.yingqida.richplay.fragment.SuperFragment;
import com.yingqida.richplay.util.ImageTools;

public class MenuActivity extends SuperActivityForFragment {

	private SuperFragment fragment;

	HomeReceiver homeReceiver;

	@Override
	public void handleHttpResponse(String response, int requestId, InputStream is) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	/**
	 * 
	 * Function:后台运行
	 * 
	 * @author ruhaly DateTime 2013-10-17 下午1:56:26
	 */
	private void registerReceiver() {
		homeReceiver = new HomeReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(RichResource.ACTION_BACKGROUND);
		registerReceiver(homeReceiver, filter);
	}

	@Override
	public void initData() {
		registerReceiver();
	}

	@Override
	public void initLayout(Bundle paramBundle) {
		// set the Above View
		if (paramBundle != null)
			fragment = (SuperFragment) getSupportFragmentManager().getFragment(
					paramBundle, "mContent");
		if (fragment == null)
			fragment = PageHomeFragment.getIns();

		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuFragment()).commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		// sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(null);// (R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public void clearData() {
		unregisterReceiver();
	}

	private void unregisterReceiver() {
		if (null != homeReceiver) {
			unregisterReceiver(homeReceiver);
		}
	}

	public void switchContent(final Fragment to) {
		if (fragment != to) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.hide(fragment).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(fragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
			this.fragment = (SuperFragment) to;
		}
		// getSupportFragmentManager().beginTransaction()
		// .replace(R.id.content_frame, fragment).commit();
		showContent();
		if (to instanceof SearchFragment) {
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					((SearchFragment) to)
							.reqeustDate(((SearchFragment) to).keyword);
				}
			}, 100);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			imitateEvent();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class HomeReceiver extends BroadcastReceiver {

		/*
		 * (non-Javadoc) (覆盖方法) 方法名称： onReceive 作者： wjd 方法描述：
		 * 
		 * @see
		 * android.content.BroadcastReceiver#onReceive(android.content.Context,
		 * android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (RichResource.ACTION_BACKGROUND.equals(action)) {
				moveTaskToBack(intent.getBooleanExtra("nonRoot", false));
			}
		}
	}
}
