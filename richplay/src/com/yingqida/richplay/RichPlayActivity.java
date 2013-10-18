package com.yingqida.richplay;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.yingqida.richplay.activity.LoginActivity;
import com.yingqida.richplay.activity.MenuActivity;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;
import com.yingqida.richplay.baseapi.AppUtil;
import com.yingqida.richplay.baseapi.common.CrashHandler;
import com.yingqida.richplay.baseapi.common.FileUtil;
import com.yingqida.richplay.baseapi.map.LocManager;
import com.yingqida.richplay.service.RichPlayService;

public class RichPlayActivity extends SuperActivityForFragment {

	@Override
	public void onClick(View v) {

	}

	@Override
	public void clearData() {

	}

	@Override
	public void initData() {
		LocManager.instance
				.setLocationManager((LocationManager) getSystemService(Context.LOCATION_SERVICE));
		LocManager.instance.startLocationListener();
		FileUtil.initDir();
	}

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.main);
		if (!AppUtil.isSdcardAviable()) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getBaseContext(), R.string.no_sdcard_tip,
							Toast.LENGTH_LONG).show();
				}
			});
			// mHandler.postDelayed(new Runnable() {
			//
			// @Override
			// public void run() {
			// RichPlayApplication.getIns().exitApp();
			//
			// }
			// }, 2000);
			// return;
		}

		CrashHandler.handler.init(getString(R.string.version));
		// 启动服务
		startService(new Intent(getApplicationContext(), RichPlayService.class));

		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (isSuccessLogin()) {
					startActivity(new Intent(getBaseContext(),
							HomeActivity.class));
				} else {
					startActivity(new Intent(getBaseContext(),
							MenuActivity.class));
				}
				finish();
			}
		}, 2000);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void handleHttpResponse(String response, int requestId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {
		// TODO Auto-generated method stub

	}
}