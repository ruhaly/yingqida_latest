package com.yingqida.richplay.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.logic.LoginLogic;
import com.yingqida.richplay.logic.SuperLogic;

public class ModifyPWDActivity extends SuperActivity {

	private LoginLogic logic;

	private HttpUtils httpUtils;

	@ViewInject(R.id.etOldPwd)
	private EditText etOldPwd;

	@ViewInject(R.id.etNewPwd)
	private EditText etNewPwd;

	@ViewInject(R.id.btnConfirm)
	private Button btnConfirm;

	@ViewInject(R.id.btnCancel)
	private Button btnCancel;

	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	@OnClick(R.id.btnConfirm)
	public void btnConfirmClick(View view) {
		if (checkDataIsEmpty()) {
			showToast(getString(R.string.pwd_not_empty));
			return;
		}
		logic.setDate(mHandler, httpUtils);
		logic.sendModPWDRequest(getUser().getRemarkToken(), etOldPwd.getText()
				.toString().trim(), etNewPwd.getText().toString().trim());
	}

	@OnClick(R.id.btnCancel)
	public void btnCancelClick(View view) {
		finish();
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {

	}

	@Override
	public void handleHttpResponse(String response, int requestId) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void handleHttpTimeout(int paramInt) {

	}

	@Override
	public void initData() {
		logic = LoginLogic.getInstance();
		httpUtils = new HttpUtils();
	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.MODIFY_PWD_SUCCESS_MSGWHAT: {
			showToast(getString(R.string.mod_pwd_success));
			finish();
			break;
		}
		case SuperLogic.MODIFY_PWD_ERROR_MSGWHAT: {
			showToast(getString(R.string.mod_pwd_failed));
			break;
		}
		}
		super.handleMsg(msg);
	}

	@SuppressLint("NewApi")
	public boolean checkDataIsEmpty() {
		return etOldPwd.getText().toString().isEmpty()
				|| etNewPwd.getText().toString().isEmpty();
	}

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.modify_pwd_layout);
		ViewUtils.inject(this);
		btnToggle.setVisibility(View.GONE);

	}

	@Override
	public void clearData() {

	}
}
