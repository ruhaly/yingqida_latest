package com.yingqida.richplay.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;

import com.yingqida.richplay.R;

/**
 * Notification��չ��
 * 
 * @Description: Notification��չ��
 * 
 * @File: NotificationExtend.java
 * 
 * @Package com.test.background
 * 
 * @Author Hanyonglu
 * 
 * @Date 2012-4-13 ����02:00:44
 * 
 * @Version V1.0
 */
public class NotificationExtend {
	private Activity context;

	public NotificationExtend(Activity context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	// ��ʾNotification
	public void showNotification() {
		// ����һ��NotificationManager������
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);

		// ����Notification�ĸ�������
		Notification notification = new Notification(R.drawable.ic_launcher,
				"�Ķ���", System.currentTimeMillis());
		// ����֪ͨ�ŵ�֪ͨ����"Ongoing"��"��������"����
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		// �����ڵ����֪ͨ���е�"���֪ͨ"�󣬴�֪ͨ��������FLAG_ONGOING_EVENTһ��ʹ��
		// notification.flags |= Notification.FLAG_NO_CLEAR;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.defaults = Notification.DEFAULT_LIGHTS;
		notification.ledARGB = Color.BLUE;
		notification.ledOnMS = 5000;

		// ����֪ͨ���¼���Ϣ
		CharSequence contentTitle = "�����̳���ʾ��Ϣ"; // ֪ͨ������
		CharSequence contentText = "�����̳ǣ���鿴����"; // ֪ͨ������

		Intent notificationIntent = new Intent(context, context.getClass());
		notificationIntent.setAction(Intent.ACTION_MAIN);
		notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		// ��Notification���ݸ�NotificationManager
		notificationManager.notify(0, notification);

		// ����Ӧ����Activity
		// try {
		// Intent notificationIntent = new
		// Intent(context,Class.forName("com.test.background.MainActivity"));
		// notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		// //PendingIntentΪһ�������Intent,ͨ��getBroadcast����getActivity����getService�õ�.
		// PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
		// notificationIntent, 0);
		// notification.setLatestEventInfo(context, contentTitle, contentText,
		// pendingIntent);
		//
		// // ��Notification���ݸ�NotificationManager
		// notificationManager.notify(0, notification);
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }
	}

	// ȡ��֪ͨ
	public void cancelNotification() {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
	}
}
