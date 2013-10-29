package com.yingqida.richplay;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.yingqida.richplay.activity.LoginActivity;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.baseapi.AppUtil;
import com.yingqida.richplay.baseapi.common.CrashHandler;
import com.yingqida.richplay.baseapi.common.FileUtil;
import com.yingqida.richplay.baseapi.common.GlobalVar;
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.baseapi.map.LocManager;
import com.yingqida.richplay.logic.LoginLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.service.RichPlayService;

public class RichPlayActivity extends SuperActivity {

	HttpUtils httpUtils;

	LoginLogic logic;

	@Override
	public void initData() {
		LocManager.instance
				.setLocationManager((LocationManager) getSystemService(Context.LOCATION_SERVICE));
		LocManager.instance.startLocationListener();
		logic = LoginLogic.getInstance();
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
		}

		CrashHandler.handler.init(getString(R.string.version));
		// 启动服务
		startService(new Intent(getApplicationContext(), RichPlayService.class));

		getRemarkToken();

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void handleHttpResponse(String response, int requestId) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void clearData() {

	}

	/**
	 * 
	 * Function:所有请求依赖这个token 所以在获取到这个remarkToken之后才跳转到主界面
	 * 
	 * @author ruhaly DateTime 2013-10-28 上午10:00:58
	 */
	public void getRemarkToken() {
		httpUtils = new HttpUtils();
		logic.setDate(mHandler, httpUtils);
		logic.sendGetRemarkTokenRequest();
	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.GET_REMARKTOKEN_SUCCESS_MSGWHAT: {

			User user = getUser();
			user.setRemarkToken((String) msg.obj);
			GlobalVar.ins.storeUser(getAppShare(), user);
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					startActivity(new Intent(getBaseContext(),
							LoginActivity.class));
					finish();
				}
			}, 2000);

			break;
		}
		case SuperLogic.GET_REMARKTOKEN_ERROR_MSGWHAT: {
			showToast(getString(R.string.date_format_error));
			finish();
			break;
		}
		default:
			break;
		}
		super.handleMsg(msg);
	}
}