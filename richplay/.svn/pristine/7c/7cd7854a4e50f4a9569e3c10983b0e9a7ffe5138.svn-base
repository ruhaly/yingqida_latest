package com.yingqida.richplay.activity.common;

import java.io.IOException;
import java.io.InputStream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.yingqida.richplay.R;
import com.yingqida.richplay.RichPlayApplication;
import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.AppUtil;
import com.yingqida.richplay.baseapi.Constant;
import com.yingqida.richplay.baseapi.ImageUtil;
import com.yingqida.richplay.baseapi.ResponseCode;
import com.yingqida.richplay.baseapi.common.ActivityStack;
import com.yingqida.richplay.baseapi.common.FileUtil;
import com.yingqida.richplay.baseapi.common.RealmeUtil;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.baseapi.http.HttpResponseHanlder;
import com.yingqida.richplay.baseapi.http.HttpTimeoutHandler;
import com.yingqida.richplay.pubuliu.ImageCache;
import com.yingqida.richplay.pubuliu.ImageFetcher;
import com.yingqida.richplay.pubuliu.ImageWorker.ICallBack;
import com.yingqida.richplay.service.NetWorkProxy;

public abstract class SuperActivity extends HandleActivity implements
		HttpResponseHanlder, HttpTimeoutHandler, View.OnClickListener {
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {

		AppLog.out(MY_TAG, "onRestart", AppLog.LEVEL_INFO);
		super.onRestart();
	}

	static final String ACCOUNT_SPLITER = "Q";
	private static final int EVENT_BACK_TO_LOGIN = 3000;
	private static final int EVENT_HTTP_ERROR = 2000;
	public PopupWindow menuPopupWindow;
	private static final String MY_TAG = "SuperActivity";
	private AAMessageReceiver aaReceiver;
	private static ToneGenerator tonePlayer = new ToneGenerator(
			AudioManager.STREAM_MUSIC, 100);

	public static String TAG;

	// 是否清除播放界面的数据
	public static boolean isClearDate = false;

	public SuperActivity() {
	}

	public abstract void initData();

	public abstract void initLayout();

	private void sendBackGroundNotify() {
		Intent intent = null;
		try {
			intent = new Intent(getApplicationContext(), Class.forName(this
					.getClass().getName()));
		} catch (ClassNotFoundException e) {
			intent = null;
		}
		if (null == intent)
			return;

		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		showNotification(null, null, intent, getString(R.string.app_running),
				ID_BACKGROUND, getString(R.string.app_name),
				getString(R.string.app_running));
	}

	public void backToLoginView(int resId) {
		if (getService() != null) {
			getService().stopHeartBeat();
		}
		Message localMessage = new Message();
		localMessage.what = EVENT_BACK_TO_LOGIN;
		localMessage.arg1 = resId;
		mHandler.sendMessage(localMessage);
	}

	public abstract void clearData();

	public void finish() {
		ActivityStack.getIns().popupActivity(this.getClass().getName());
		unRegister();
		super.finish();
	}

	public int getScreenH() {
		return getWindowManager().getDefaultDisplay().getHeight();
	}

	public int getScreenW() {
		return getWindowManager().getDefaultDisplay().getWidth();
	}

	public NetWorkProxy getService() {
		return RichPlayApplication.getIns().getNetProxy();
	}

	@Override
	public void handleHttpResponse(String response, int netErrorCode,
			int requestId) {
		switch (netErrorCode) {
		case RichResource.ERROR_NET:
			mHandler.sendEmptyMessage(RichResource.ERROR_NET);
			break;
		case RichResource.ERROR_FWQ:
			mHandler.sendEmptyMessage(RichResource.ERROR_FWQ);
			break;
		default:
			break;
		}
	}

	@Override
	public void handleHttpTimeout(int paramInt) {
		showToast(getString(R.string.timeOut));
	}

	public void showToast(final String str) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dismissProgress();
				Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	public void handleHttpResponseCode(int code) {
		switch (code) {
		case ResponseCode.VALUE_NULL_ERROR: {
			break;
		}
		}
	}

	public void handleMsg(Message msg) {
		switch (msg.what) {
		case RichResource.ERROR_NET: {
			showToast(getString(R.string.error_net));
			break;
		}
		case RichResource.ERROR_FWQ: {
			showToast(getString(R.string.error_fwq));
			break;
		}
		default:
			break;
		}
		dismissProgress();
	}

	public boolean moveTaskToBack(boolean paramBoolean) {
		if (paramBoolean)
			sendBackGroundNotify();
		return super.moveTaskToBack(paramBoolean);

	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		initData();
		initLayout();
		TAG = this.getClass().getName();
		ActivityStack.getIns().pushActivity(this);

		if (RichPlayApplication.LOGIN) {
			register();
		}
	}

	protected void onDestroy() {
		clearData();
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	protected void onResume() {
		clearBackGroundNotification();
		super.onResume();
	}

	protected void onUserLeaveHint() {
		if (!RealmeUtil.isTopApp(getBaseContext()))
			sendBackGroundNotify();
		super.onUserLeaveHint();
	}

	public void sendHttpErrorMessage(int paramInt) {
		Message localMessage = new Message();
		localMessage.what = EVENT_HTTP_ERROR;
		localMessage.arg1 = paramInt;
		mHandler.sendMessage(localMessage);
	}

	/**
	 * 模拟事件
	 * 
	 * @param event
	 */
	public void imitateEvent() {
		Intent intent = new Intent(RichResource.ACTION_BACKGROUND);
		intent.putExtra("nonRoot", true);
		sendBroadcast(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.app.Activity#onConfigurationChanged(android.content.res.Configuration
	 * )
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

	}

	public void fullScreen() {
		WindowManager.LayoutParams attrs = getParent().getWindow()
				.getAttributes();
		attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setAttributes(attrs);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	public void cancelFullScreen() {
		WindowManager.LayoutParams attrs = getParent().getWindow()
				.getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow()
				.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	/**
	 * 播放按键音
	 */
	public void playButtonTone() {
		// 默认播放按键音，用户设置以后根据用户的设置选择是否播放按键音
		if (getSettingShare().getBoolean("custome_button_tone", true))
			tonePlayer.startTone(ToneGenerator.TONE_DTMF_0, 100);
	}

	private SharedPreferences getSettingShare() {
		return getSharedPreferences(getPackageName() + "_preferences",
				MODE_PRIVATE);
	}

	/**
	 * 注册监听UDP推送消息的广播接收器
	 */
	private void register() {
		aaReceiver = new AAMessageReceiver();
		IntentFilter filter = new IntentFilter();
		registerReceiver(aaReceiver, filter);
	}

	private void unRegister() {

		try {
			if (null != aaReceiver)
				unregisterReceiver(aaReceiver);
		} catch (Exception e) {
		}
		aaReceiver = null;
	}

	class AAMessageReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (!SuperActivity.this.getClass().getName()
					.equals(ActivityStack.getIns().topActivityName()))
				return;
		}
	}

	public void showProcessDialog(DialogInterface.OnDismissListener dismiss) {
		showProgressDialog("", getString(R.string.loading), true, dismiss);
	}

	/**
	 * 判断是否是3G 或者wifi状态 方法名称： checkNetworkConnection 作者： fengrun 方法描述： 输入参数： @param
	 * context 输入参数： @return 返回类型： boolean
	 */
	public boolean checkNetworkConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (wifi == State.CONNECTED) {
			return true;
		}
		State mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		if (mobile == State.CONNECTED) {
			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			int type = tm.getNetworkType();
			if (type != TelephonyManager.NETWORK_TYPE_CDMA
					&& type != TelephonyManager.NETWORK_TYPE_EDGE
					&& type != TelephonyManager.NETWORK_TYPE_GPRS) {
				return true;
			}
		}
		return false;
	}

	public int getHeadWidth() {
		return AppUtil.dip2px(getBaseContext(), 80);
	}

	public int getProductWidth() {
		return AppUtil.dip2px(getBaseContext(), 100);
	}

	public static int convertDipOrPx(Context context, int dip) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}

	// 转换px为dip
	public static int convertPxOrDip(Context context, int px) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
	}

	public void clearImageCache() {
		ImageUtil.delPhotoDir(RichResource.PIC_PATH);
		ImageUtil.clearImgs();
		FileUtil.removeFile(FileUtil.getFile(RichResource.PIC_PATH));
	}

	public SharedPreferences getAppShare() {
		return getSharedPreferences(RichResource.CONFIG_NAME, 0);
	}

	public String getSessionString() {
		return "12345";
	}

	public String parseXML(String xmlPath) {
		StringBuffer sb = new StringBuffer();

		ClassLoader cl = SuperActivity.class.getClassLoader();
		InputStream stream = cl.getResourceAsStream(xmlPath);
		int length = 0;
		byte[] b = new byte[1024];
		try {
			while ((length = stream.read(b)) != -1) {
				sb.append(new String(b, 0, length));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();

	}

	public Bitmap getImage(String path) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		// 这个isjustdecodebounds很重要
		opt.inJustDecodeBounds = true;//
		Bitmap bmp = BitmapFactory.decodeFile(path, opt);

		// 获取到这个图片的原始宽度和高度
		int picWidth = opt.outWidth;
		int picHeight = opt.outHeight;

		// 获取屏的宽度和高度
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();

		// isSampleSize是表示对图片的缩放程度，比如值为2图片的宽度和高度都变为以前的1/2
		opt.inSampleSize = 1;
		// // 根据屏的大小和图片大小计算出缩放比例
		if (picWidth > picHeight) {
			if (picWidth > screenWidth)
				opt.inSampleSize = picWidth / screenWidth;
		} else {
			if (picHeight > screenHeight)

				opt.inSampleSize = picHeight / screenHeight;
		}

		// 这次再真正地生成一个有像素的，经过缩放了的bitmap
		opt.inJustDecodeBounds = false;
		bmp = BitmapFactory.decodeFile(path, opt);
		return bmp;
	}

	/**
	 * 
	 * isSuccessLogin(是否成功登录过)
	 * 
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public boolean isSuccessLogin() {
		return getAppShare().getBoolean(Constant.LOGGED_ON, false);
	}

	public ImageFetcher initFetcher(ImageFetcher mImageFetcher,
			ICallBack callback) {
		mImageFetcher = new ImageFetcher(this, 240, callback);
		mImageFetcher.setImageCache(new ImageCache(getBaseContext(),
				RichResource.PIC_PATH));
		mImageFetcher.setLoadingImage(R.drawable.empty_photo);
		mImageFetcher.setExitTasksEarly(false);
		return mImageFetcher;
	}
}