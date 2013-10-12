package com.yingqida.richplay.activity;

import android.os.Bundle;

import com.lidroid.xutils.exception.HttpException;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;

public class UserEditActivity extends SuperActivity {

	@Override
	public void initData() {

	}

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.edituser_layout);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub

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
