package com.yingqida.richplay.activity;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import com.yingqida.richplay.fragment.SearchFragment;
import com.yingqida.richplay.fragment.SuperFragment;
import com.yingqida.richplay.service.DownloadService;

public class MenuActivity extends SuperActivityForFragment {

	private SuperFragment fragment;

	HomeReceiver homeReceiver;

	@Override
	public void handleHttpResponse(String response, int requestId,
			InputStream is) {

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
		sm.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
		sm.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果
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

	public OnClickListener ok = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			loadApk();
		}
	};

	public void showUpdateVersionDialog() {
		showAlertDialog(0, getString(R.string.tip),
				getString(R.string.load_latest_version), null, ok, DEFAULT_BTN,
				null, true, true);
	}

	// 本方法判断自己些的一个Service-->com.android.controlAddFunctions.PhoneService是否已经运行
	public boolean isWorked() {
		ActivityManager myManager = (ActivityManager) getBaseContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString()
					.equals("com.yingqida.richplay.service.DownloadService")) {
				return true;
			}
		}
		return false;
	}

	public void loadApk() {
		if (!isWorked()) {
			Intent intent = new Intent(MenuActivity.this, DownloadService.class);
			// 由intent启动service，后台运行下载进程，在服务中调用notifycation状态栏显示进度条
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startService(intent);
		}
	}// 从IP地址下载文件到本地

}
