package com.yingqida.richplay.activity.common;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.view.Window;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.exception.HttpException;
import com.yingqida.richplay.R;
import com.yingqida.richplay.fragment.MenuFragment;
import com.yingqida.richplay.fragment.SuperFragment;

public class BaseActivity extends SuperActivity {

	private int mTitleRes;
	protected SuperFragment mFrag;

	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void handleHttpResponse(String response, int requestId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initLayout(Bundle paramBundle) {
		// set the Behind View
//		setBehindContentView(R.layout.menu_frame);
		if (paramBundle == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			mFrag = new MenuFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (SuperFragment) this.getSupportFragmentManager()
					.findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public void clearData() {

	}
}
