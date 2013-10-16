package com.yingqida.richplay.activity;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.exception.HttpException;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.fragment.MenuFragment;
import com.yingqida.richplay.fragment.SuperFragment;
import com.yingqida.richplay.fragment.YuansuFragment;

public class MenuActivity extends SuperActivity {

	private SuperFragment fragment;

	@Override
	public void handleHttpResponse(String response, int requestId) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initLayout(Bundle paramBundle) {
		// set the Above View
		if (paramBundle != null)
			fragment = (SuperFragment) getSupportFragmentManager().getFragment(
					paramBundle, "mContent");
		if (fragment == null)
			fragment = new YuansuFragment();

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
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

	}

	@Override
	public void clearData() {

	}
}
