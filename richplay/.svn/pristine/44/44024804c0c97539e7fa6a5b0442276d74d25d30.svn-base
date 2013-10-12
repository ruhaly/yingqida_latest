package com.yingqida.richplay;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.yingqida.richplay.activity.PCenterActivity;
import com.yingqida.richplay.activity.SearchActivity;
import com.yingqida.richplay.activity.WallPicActivity;
import com.yingqida.richplay.activity.common.TabSuperActivity;
import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.Constant;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.service.RichPlayService;

/**
 * 
 * 
 * 类名称： HomeActivity.java 作者： hlr 创建时间：2012-4-12 上午11:26:27 类描述： 主界面 版权声明 ：
 * Copyright(C) 2008-2010 RichPeak
 * 
 */
public class HomeActivity extends TabSuperActivity {

	public static final int PICK_NOTIFICATION = 1;

	/**
	 * 主界面广播接收器
	 */
	private HomeReceiver homeReceiver;

	public static HomeActivity ins;

	private static final String TAG = "HomeActivity";

	private static final int IDX_CART = 0;
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			AppLog.out(TAG, "onServiceConnected()", AppLog.LEVEL_INFO);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// netProxy = null;
		}
	};

	public static boolean isInsNull() {
		return ins == null;
	}

	public void bindService() {
		bindService(new Intent(getApplicationContext(), RichPlayService.class),
				conn, Context.BIND_AUTO_CREATE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fengrun.realmestore.activity.common.TabSuperActivity#initData()
	 */
	@Override
	public void initData() {
		ins = this;
		registerReceiver();

		bindService();

	}

	/*
	 * (non-Javadoc) (覆盖方法) 方法名称： initLayout 作者： wjd 方法描述：
	 * 
	 * @see com.richpeak.rimi.ui.TabSuperActivity#initLayout()
	 */
	@Override
	public void initLayout() {

		super.initLayout();
		List<Integer> icons = new ArrayList<Integer>();
		icons.add(R.drawable.tab_home_drawable);
		icons.add(R.drawable.tab_search_drawable);
		icons.add(R.drawable.tab_center_drawable);
		List<Intent> acts = new ArrayList<Intent>();
		acts.add(new Intent(this, WallPicActivity.class));
		acts.add(new Intent(this, SearchActivity.class));
		acts.add(new Intent(this, PCenterActivity.class));
		String[] tabTags = getResources().getStringArray(R.array.home_tab_tag);
		for (int i = 0; i < icons.size(); i++) {
			mTabHost.addTab(mTabHost.newTabSpec(String.valueOf(i))
					.setIndicator(getIndicator(icons.get(i), tabTags[i], i))
					.setContent(acts.get(i)));
		}

		// setLoginCheckListener(mTabHost.getTabWidget().getChildAt(IDX_CART),
		// IDX_CART);

		mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {

				if (!Constant.STR_ONE.equals(tabId)) {
				} else {
				}

			}
		});
	}

	private void setLoginCheckListener(View view, final int index) {
		// if (!RealmeStoreApplication.LOGIN)
		view.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkLoginStatus(index);
			}
		});
	}

	private void checkLoginStatus(int index) {
		if (!RichPlayApplication.LOGIN) {
		} else {
			mTabHost.setCurrentTab(index);
		}
	}

	private LinearLayout getIndicator(int iconBgResource, String title, int id) {
		LinearLayout indicator = (LinearLayout) LayoutInflater.from(this)
				.inflate(R.layout.home_tab_idc, null);
		ImageView imageView = (ImageView) indicator
				.findViewById(R.id.ImageView01);
		imageView.setBackgroundResource(iconBgResource);
		// TextView textView = (TextView)
		// indicator.findViewById(R.id.TextView01);
		// textView.setText(title);
		// textView.setTextColor(getResources().getColorStateList(
		// R.color.color_list));
		return indicator;
	}

	private void registerReceiver() {
		homeReceiver = new HomeReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(RichResource.ACTION_BACKGROUND);
		registerReceiver(homeReceiver, filter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
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

			} else {
				setNoitification(intent);
			}
		}
	}

	private void setNoitification(Intent intent) {

		// Log.i("setNoitification", "HomeActivity.java");

		String action = intent.getAction();
		if (RichResource.ACTION_BACKGROUND.equals(action)) {
			moveTaskToBack(intent.getBooleanExtra("nonRoot", false));

		}

	}

	public static void startAcForResult(Intent intent, int requestCode) {
		ins.startActivityForResult(intent, requestCode);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		invoke("onActivityResult", new Class<?>[] { int.class, int.class,
				Intent.class }, requestCode, resultCode, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fengrun.realmestore.activity.common.TabSuperActivity#clearData()
	 */
	@Override
	public void clearData() {
		unregisterReceiver();
		unbindService(conn);
	}

	private void unregisterReceiver() {
		if (null != homeReceiver) {
			unregisterReceiver(homeReceiver);
		}
	}

	/*
	 * (non-Javadoc) (覆盖方法) 方法名称： handleHttpResponse 作者： fengrun 方法描述：
	 * 
	 * @see
	 * com.fengrun.realmestore.activity.common.SuperActivity#handleHttpResponse
	 * (java.lang.String, int, int)
	 */
	@Override
	public void handleHttpResponse(String response, int netErrorCode,
			int requestId) {
		if (0 != netErrorCode) {
			handleHttpResponseCode(netErrorCode);
			dismissProgress();
			return;
		}
		dismissProgress();
	}

	/*
	 * (non-Javadoc) (覆盖方法) 方法名称： handleMsg 作者： fengrun 方法描述：
	 * 
	 * @see
	 * com.fengrun.realmestore.activity.common.SuperActivity#handleMsg(android
	 * .os.Message)
	 */
	@Override
	public void handleMsg(Message msg) {
		super.handleMsg(msg);
		switch (msg.what) {
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc) (覆盖方法) 方法名称： onResume 作者： wjd 方法描述：
	 * 
	 * @see com.fengrun.realmestore.activity.common.TabSuperActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();

	}

	public static final int TAB_HOME = 0;
	public static final int TAB_SEARCHE = 1;
	public static final int TAB_CENTER = 2;

	public void tabClick(View view) {
		switch (view.getId()) {
		case R.id.img_home: {
			mTabHost.setCurrentTab(TAB_HOME);
			setTabBg(TAB_HOME);
			break;
		}
		case R.id.img_search: {
			mTabHost.setCurrentTab(TAB_SEARCHE);
			setTabBg(TAB_SEARCHE);
			break;
		}
		case R.id.img_center: {
			mTabHost.setCurrentTab(TAB_CENTER);
			setTabBg(TAB_CENTER);
			break;
		}
		}
	}

	public void setTabBg(int viewId) {
		switch (viewId) {
		case TAB_HOME:
			findViewById(R.id.img_home).setBackgroundResource(
					R.drawable.tab_home_g);
			findViewById(R.id.img_search).setBackgroundResource(
					R.drawable.tab_search_p);
			findViewById(R.id.img_center).setBackgroundResource(
					R.drawable.tab_center_p);
			break;
		case TAB_SEARCHE:
			findViewById(R.id.img_home).setBackgroundResource(
					R.drawable.tab_home_p);
			findViewById(R.id.img_search).setBackgroundResource(
					R.drawable.tab_search_g);
			findViewById(R.id.img_center).setBackgroundResource(
					R.drawable.tab_center_p);
			break;
		case TAB_CENTER:
			findViewById(R.id.img_home).setBackgroundResource(
					R.drawable.tab_home_p);
			findViewById(R.id.img_search).setBackgroundResource(
					R.drawable.tab_search_p);
			findViewById(R.id.img_center).setBackgroundResource(
					R.drawable.tab_center_g);
			break;
		default:
			break;
		}
	}
}
