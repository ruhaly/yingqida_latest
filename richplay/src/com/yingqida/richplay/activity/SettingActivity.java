package com.yingqida.richplay.activity;


import java.io.InputStream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;

public class SettingActivity extends SuperActivityForFragment {

	@ViewInject(R.id.btnLogout)
	private Button btnLogout;

	@Override
	public void initData() {

	}

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.setting_layout);
		ViewUtils.inject(this);
	}

	@OnClick(R.id.btnLogout)
	public void logoutClick(View view) {
		showLogoutDialog();
	}

	@Override
	public void clearData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	/**
	 * 
	 * btnEditUser(编辑个人资料界面)
	 * 
	 * @param view
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void btnEditUser(View view) {
		startActivity(new Intent(getBaseContext(), UserEditActivity.class));
	}

	@Override
	public void handleHttpResponse(String response, int requestId, InputStream is) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {
		// TODO Auto-generated method stub

	}
}
