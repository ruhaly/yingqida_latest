package com.yingqida.richplay.activity.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.widget.RemoteViews;

import com.yingqida.richplay.R;
import com.yingqida.richplay.baseapi.common.RichResource;

public class NotificationActivity extends FragmentActivity {

	/**
	 * 消息通知ID
	 */
	public static final int ID_MSG = 1;

	/**
	 * 程序切后台ID
	 */
	public static final int ID_BACKGROUND = 2;

	/**
	 * 通知管理器
	 */
	private static NotificationManager mNotificationManager;

	/**
	 * 默认通知图标
	 */
	public static int icon = R.drawable.ic_launcher;

	/**
	 * 默认通知显示方式
	 */
	public static int flag = Notification.FLAG_AUTO_CANCEL;

	/**
	 * 方法名称： showNotification 作者： wjd 方法描述： 显示通知 输入参数： @param view 通知界面 输入参数： @param
	 * title 通知界面标题 输入参数： @param msg 通知界面消息 输入参数： @param soundUri 铃声路径 输入参数： @param
	 * intent 通知意图 输入参数： @param tickerText 通知滚动显示文字 输入参数： @param id 通知id 返回类型：
	 * void
	 */
	public void showNotification(RemoteViews view, String soundUri,
			Intent intent, String tickerText, int id, String title, String msg) {
		// init notification manager
		initNotificationManager();

		// init notification
		Notification notification = new Notification(icon, tickerText,
				System.currentTimeMillis());
		notification.flags = flag;

		boolean playSound = getSharedPreferences(RichResource.CONFIG_NAME,
				Context.MODE_PRIVATE).getBoolean(
				RichResource.NOTIFYCATION_CHECK, true);
		if (playSound && null != soundUri && !"".equals(soundUri)) {
			notification.sound = Uri.parse(soundUri);
		}

		PendingIntent pendingIntent = null;
		if (null != intent) {
			pendingIntent = PendingIntent.getActivity(getBaseContext(), 0,
					intent, 0);
		} else {
			pendingIntent = PendingIntent.getActivity(getBaseContext(), 0,
					new Intent(getBaseContext(), this.getClass()), 0);
		}
		if (null != view) {
			notification.contentView = view;
			notification.contentIntent = pendingIntent;
		} else {
			notification.setLatestEventInfo(getBaseContext(), title, msg,
					pendingIntent);
		}

		if (ID_MSG == id) {
			clearMsgNotification();
		}
		mNotificationManager.cancelAll();
		mNotificationManager.notify(id, notification);
		// mNotificationManager.cancel(id);
	}

	/**
	 * 方法名称： clearMsgNotification 作者： wjd 方法描述： 清除消息通知 输入参数： 返回类型： void
	 */
	public void clearMsgNotification() {
		initNotificationManager();
		mNotificationManager.cancel(ID_MSG);
	}

	/**
	 * 
	 * 方法名称： clearBackGroundNotification 作者： wjd 方法描述： 清除程序后台通知 输入参数： 返回类型： void
	 */
	public void clearBackGroundNotification() {
		initNotificationManager();
		mNotificationManager.cancel(ID_BACKGROUND);
	}

	/**
	 * 
	 * 方法名称： clearAllNotifications 作者： wjd 方法描述： 清除所有通知 输入参数： 返回类型： void
	 */
	public void clearAllNotifications() {
		initNotificationManager();
		mNotificationManager.cancelAll();
	}

	/**
	 * 方法名称： initNotificationManager 作者： wjd 方法描述： 初始化通知管理器 输入参数： 返回类型： void
	 */
	private void initNotificationManager() {
		if (null == mNotificationManager) {
			mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		}
	}
}

/*
 * Location: E:\apk\ Qualified Name:
 * com.pengrun.realme.activity.common.NotificationActivity JD-Core Version:
 * 0.6.0
 */