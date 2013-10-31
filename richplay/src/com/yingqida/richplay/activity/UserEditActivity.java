package com.yingqida.richplay.activity;


import java.io.InputStream;

import android.os.Bundle;

import com.lidroid.xutils.exception.HttpException;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;

public class UserEditActivity extends SuperActivityForFragment {

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
	public void handleHttpResponse(String response, int requestId, InputStream is) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleHttpException(HttpException error, String msg) {
		// TODO Auto-generated method stub
		
	}

}
