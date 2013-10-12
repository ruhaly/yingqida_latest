package com.yingqida.richplay.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.udp.UdpProxy;

public class RichPlayService extends Service {

	private static String TAG = "AppService";

	@Override
	public void onCreate() {
		AppLog.out(TAG, "onCreate ------------------------ ", AppLog.LEVEL_INFO);
		UdpProxy.newInstance();
		super.onCreate();
	}

	class NetChangeListener extends BroadcastReceiver {
		public void onReceive(Context paramContext, Intent intent) {
			if (ConnectivityManager.CONNECTIVITY_ACTION.endsWith(intent
					.getAction())) {
				boolean noConnection = intent.getBooleanExtra(
						ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
				if (noConnection) {
					return;
				}
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
