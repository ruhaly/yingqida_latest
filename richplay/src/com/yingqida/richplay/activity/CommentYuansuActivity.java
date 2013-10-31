package com.yingqida.richplay.activity;


import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.logic.CommentYuansuLogic;
import com.yingqida.richplay.logic.SuperLogic;

public class CommentYuansuActivity extends SuperActivity {

	private String remark_id;

	private HttpUtils httpUtil;

	private CommentYuansuLogic logic;

	@ViewInject(R.id.etComment)
	private EditText etComment;

	@Override
	public void handleHttpResponse(String response, int requestId, InputStream is) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void initData() {
		logic = CommentYuansuLogic.getInstance();
	}

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.comment_yuansu_layout);
		ViewUtils.inject(this);
		remark_id = getIntent().getExtras().getString("remarkId");
	}

	@Override
	public void clearData() {

	}

	DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			logic.stopReqeust();
		}
	};

	@OnClick(R.id.btnConfirm)
	public void btnConfirmClick(View view) {
		if (checkDataIsEmptyp()) {
			showToast(getString(R.string.data_not_empty));
		}
		httpUtil = new HttpUtils();
		logic.setDate(mHandler, httpUtil);
		showProcessDialog(dismiss);
		logic.sendCommentYuanRequest(getUser().getRemarkToken(), remark_id,
				etComment.getText().toString().trim());
	}

	@SuppressLint("NewApi")
	public boolean checkDataIsEmptyp() {
		return etComment.getText().toString().trim().isEmpty();
	}

	@OnClick(R.id.btnCancel)
	public void btnCancelClick(View view) {
		finish();
	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.COMMENT_YUANSU_SUCCESS_MSGWHAT: {
			showToast(getString(R.string.comment_yuansu_success));
			finish();
			break;
		}

		default:
			break;
		}
		super.handleMsg(msg);
	}

	@Override
	public void finish() {
		hiddenSoft(etComment);
		super.finish();
	}
}
