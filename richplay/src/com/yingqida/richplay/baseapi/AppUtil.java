package com.yingqida.richplay.baseapi;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.util.TypedValue;

public class AppUtil {

	/**
	 * 判断应用程序是否是最上层的应用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isTopApp(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> TaskList = am.getRunningTasks(2);
		if (TaskList == null || TaskList.isEmpty()) {
			return false;
		}
		RunningTaskInfo rti = TaskList.get(0);
		String tmp = rti.topActivity.getPackageName();
		return tmp.equals(context.getPackageName());
	}

	/**
	 * 获取本机IP
	 * 
	 * @return
	 */
	public static String getLocalIpAddress() {
		try {
			Enumeration<NetworkInterface> networkInfo = NetworkInterface
					.getNetworkInterfaces();
			for (Enumeration<NetworkInterface> en = networkInfo; en
					.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				Enumeration<InetAddress> intfAddress = intf.getInetAddresses();
				for (Enumeration<InetAddress> enumIpAddr = intfAddress; enumIpAddr
						.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {

		}
		return null;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * SDCARD 是否可用
	 * 
	 * @return
	 */
	public static boolean isSdcardAviable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	public static float getRawSize(Resources resources, int unit, float size) {
		return TypedValue.applyDimension(unit, size,
				resources.getDisplayMetrics());
	}
}
