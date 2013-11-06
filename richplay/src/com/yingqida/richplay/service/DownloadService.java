package com.yingqida.richplay.service;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.http.client.ResponseStream;
import com.yingqida.richplay.R;
import com.yingqida.richplay.util.ImageTools;

public class DownloadService extends Service {
	private NotificationManager notificationMrg;
	// 状态栏实例对象
	private int old_process = 0;
	private boolean isFirstStart = false;
	public  int loading_process;

	public void onCreate() {
		super.onCreate();
		isFirstStart = true;
		notificationMrg = (NotificationManager) this
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		// 获得系统后台运行的NotificationManager服务
		new Thread() {
			public void run() {
				loadFile("http://dn.dl.wdjcdn.com/files/phoenix/3.44.1.4513/wandoujia-qrbinded_com.tencent.mobileqq_3.44.1.4513.apk?pos=www/sem/bdpmt_essentialapp");
			}
		}.start();
		mHandler.sendMessage(new Message());
	}

	public void loadFile(String url) {
		loading_process = 0;
		HttpUtils http = new HttpUtils();
		ResponseStream is;
		try {
			is = http.sendSync(HttpMethod.GET, url);
			float length = is.getBaseResponse().getEntity().getContentLength();
			FileOutputStream fileOutputStream = null;
			if (is != null) {
				File file = new File(
						ImageTools.getDiskCacheDir(getBaseContext()),
						"richplay.apk");
				fileOutputStream = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int ch = -1;
				float count = 0;
				while ((ch = is.read(buf)) != -1) {
					fileOutputStream.write(buf, 0, ch);
					count += ch;
					loading_process = (int) (count * 100 / length);
				}
			}
			mHandler.sendEmptyMessage(2);
			fileOutputStream.flush();
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		} catch (Exception e) {
		}

	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 2:
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setDataAndType(Uri.fromFile(new File(ImageTools
						.getDiskCacheDir(getBaseContext()), "richplay.apk")),
						"application/vnd.android.package-archive");
				startActivity(intent);
				break;

			default:
				// 1为出现，2为隐藏
				if (loading_process > 99) {
					notificationMrg.cancel(0);
					// 下载完成后状态栏取消
					stopSelf();
					// 服务终止
					return;
				}
				if (loading_process > old_process) {
					displayNotificationMessage(loading_process);
					// 定义具体的标题栏视图显示
				}

				isFirstStart = false;
				Message msg1 = mHandler.obtainMessage();
				mHandler.sendMessage(msg1);
				old_process = loading_process;
				break;
			}

		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	// 状态栏具体视图显示要求设置
	private void displayNotificationMessage(int count) {

		// Notification的Intent，即点击后转向的Activity
		Intent notificationIntent1 = new Intent(this, this.getClass());
		notificationIntent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		// addflag设置跳转类型
		PendingIntent contentIntent1 = PendingIntent.getActivity(this, 0,
				notificationIntent1, 0);

		// 创建Notifcation对象，设置图标，提示文字
		Notification notification = new Notification(R.drawable.ic_launcher,
				getResources().getString(R.string.isloading),
				System.currentTimeMillis());// 设定Notification出现时的声音，一般不建议自定义
		if (isFirstStart || loading_process > 97) {
			notification.defaults |= Notification.DEFAULT_SOUND;// 设定是否振动
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
		notification.flags |= Notification.FLAG_ONGOING_EVENT;

		// 创建一个自定义的Notification，可以使用RemoteViews
		// 要定义自己的扩展消息，首先要初始化一个RemoteViews对象，然后将它传递给Notification的contentView字段，再把PendingIntent传递给contentIntent字段
		RemoteViews contentView = new RemoteViews(getPackageName(),
				R.layout.notification_version);
		contentView.setTextViewText(R.id.n_title,
				getResources().getString(R.string.loadingtip));
		contentView.setTextViewText(R.id.n_text,
				getResources().getString(R.string.current_progress) + count
						+ "% ");
		contentView.setProgressBar(R.id.n_progress, 100, count, false);

		notification.contentView = contentView;
		notification.contentIntent = contentIntent1;

		notificationMrg.notify(0, notification);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
