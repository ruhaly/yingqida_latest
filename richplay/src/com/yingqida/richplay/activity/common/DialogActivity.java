package com.yingqida.richplay.activity.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yingqida.richplay.R;
import com.yingqida.richplay.baseapi.AppLog;

public class DialogActivity extends NotificationActivity {

	/**
	 * 默认对话框按钮
	 */
	public static final OnClickListener DEFAULT_BTN = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
		}
	};

	/**
	 * 弹出框
	 */
	private Dialog alertDialog;

	/**
	 * 弹出框
	 */
	private Dialog helpDialog;

	/**
	 * 进度框
	 */
	private ProgressDialog progressDialog;

	/**
	 * 确定按钮文字
	 */
	private String okTitle;

	/**
	 * 取消按钮文字
	 */
	private String cancelTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		initTitle();
	}

	private void initTitle() {
		okTitle = getString(R.string.confirm);
		cancelTitle = getString(R.string.cancel);
	}

	/**
	 * 
	 * @param icon
	 *            对话框图标 当参数值为0时使用默认图标
	 * @param title
	 *            对话框标题
	 * @param msg
	 *            对话框消息
	 * @param view
	 *            对话框视图
	 * @param ok
	 *            确认按钮事件
	 * @param cancel
	 *            取消按钮事件
	 * @param dismiss
	 *            对话框取消事件
	 * @param canback
	 *            TODO
	 */
	public void showAlertDialog(int icon, String title, String msg, View view,
			OnClickListener ok, OnClickListener cancel,
			OnDismissListener dismiss, final boolean canback) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if (0 != icon) {
			builder.setIcon(icon);
		}

		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setView(view);

		if (null != ok) {
			builder.setPositiveButton(okTitle, ok);
		}

		if (null != cancel) {
			builder.setNegativeButton(cancelTitle, cancel);
		}

		try {
			// alertDialog = builder.create();
			// alertDialog.setOnDismissListener(dismiss);
			// alertDialog.show();
			alertDialog = builder.create();
			alertDialog.show();
			Window window = alertDialog.getWindow();
			window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			window.setLayout(WindowManager.LayoutParams.FILL_PARENT,
					WindowManager.LayoutParams.WRAP_CONTENT);
			window.setGravity(Gravity.CLIP_HORIZONTAL);
			alertDialog.setOnDismissListener(dismiss);
			alertDialog.setCanceledOnTouchOutside(canback);
			alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (!canback) {
						return true;
					}
					return false;
				}
			});
		} catch (Exception e) {
			AppLog.out("DialogActivity", "118:" + e.getMessage(),
					AppLog.LEVEL_ERROR);
		}
	}

	/**
	 * 显示进度条
	 */
	public void showProgressDialog(String title, String msg,
			boolean isCancelable, OnDismissListener dismiss) {
		dismissProgress();

		progressDialog = ProgressDialog.show(this, title, msg);
		progressDialog.setCancelable(isCancelable);
		if (null != dismiss) {
			progressDialog.setOnDismissListener(dismiss);
		}
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (KeyEvent.KEYCODE_SEARCH == keyCode) {
					return true;
				}
				return false;
			}
		});
	}

	/**
	 * 设置对话框确定按钮标题
	 * 
	 * @param okTitle
	 */
	public void setOkTitle(String okTitle) {
		this.okTitle = okTitle;
	}

	/**
	 * 设置对话框取消按钮标题
	 * 
	 * @param cancelTitle
	 */
	public void setCancelTitle(String cancelTitle) {
		this.cancelTitle = cancelTitle;
	}

	/**
	 * 关闭弹出对话框
	 */
	public void closeDialog() {
		if (null != alertDialog) {
			alertDialog.dismiss();
			alertDialog = null;
		}
		clearHelpDialog();
	}

	/**
	 * 关闭进度条
	 */
	public void dismissProgress() {
		if (null != progressDialog) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	@Override
	protected void onDestroy() {
		clear();
		clearHelpDialog();
		super.onDestroy();
	}

	/**
	 * 清除资源
	 */
	public void clear() {
		closeDialog();
		dismissProgress();
		okTitle = null;
		cancelTitle = null;
	}

	public void clearHelpDialog() {

		if (null != helpDialog) {
			helpDialog.dismiss();
			helpDialog = null;
		}
	}

	public void setProgressDialogMessage(String msg) {
		if (null != progressDialog)
			progressDialog.setMessage(msg);
	}

}

/*
 * Location: E:\apk\ Qualified Name:
 * com.pengrun.realme.activity.common.DialogActivity JD-Core Version: 0.6.0
 */