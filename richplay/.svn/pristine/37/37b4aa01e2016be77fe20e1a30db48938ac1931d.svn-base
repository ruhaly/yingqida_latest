package com.yingqida.richplay;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.ImageUtil;
import com.yingqida.richplay.baseapi.common.ActivityStack;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.baseapi.map.LocManager;
import com.yingqida.richplay.service.NetWorkProxy;

public class RichPlayApplication extends Application {
	public static boolean LOGIN = false;

	public static boolean CHECKVERSION = false;

	private static final String MY_TAG = "RichPlayApplication";
	private static RichPlayApplication ins;
	private NetWorkProxy netProxy = NetWorkProxy.newInstance();

	public static RichPlayApplication getIns() {
		return ins;
	}

	public void exitApp() {
		AppLog.out(MY_TAG, "exitApp()", AppLog.LEVEL_INFO);
		if (null != netProxy) {
			netProxy.stopNetListener();
		}
		LocManager.instance.stopLocationListener();

		ActivityStack.getIns().popupAllActivity();
		ImageUtil.delPhotoDir(RichResource.PIC_PATH);
		ImageUtil.clearImgs();
		int sdkVersion = android.os.Build.VERSION.SDK_INT;
		if (sdkVersion <= 7) {
			String name = getPackageName();
			ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			manager.restartPackage(name);
		} else {
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	public NetWorkProxy getNetProxy() {
		return this.netProxy;
	}

	public SharedPreferences getRimiShare() {
		return getSharedPreferences(RichResource.CONFIG_NAME, 0);
	}

	public void onCreate() {
		AppLog.out(MY_TAG, "onCreate()", AppLog.LEVEL_INFO);
		ins = this;
		super.onCreate();
	}

	public SharedPreferences getAppShare() {
		return getSharedPreferences(RichResource.CONFIG_NAME, 0);
	}

}