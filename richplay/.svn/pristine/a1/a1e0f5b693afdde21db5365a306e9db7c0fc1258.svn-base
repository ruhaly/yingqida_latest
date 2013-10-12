package com.yingqida.richplay.service;

import java.util.Timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.yingqida.richplay.RichPlayApplication;
import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.baseapi.http.HttpChannel;
import com.yingqida.richplay.baseapi.http.HttpRequest;
import com.yingqida.richplay.baseapi.udp.UdpChannel;
import com.yingqida.richplay.baseapi.udp.UdpMessage;
import com.yingqida.richplay.baseapi.udp.UdpMessageHandler;
import com.yingqida.richplay.packet.HttpAction;

public class NetWorkProxy implements HttpAction, UdpMessageHandler {

	private static final String MY_TAG = "NetWorkProxy";

	private HttpChannel mHttpChannel = new HttpChannel();

	private static NetWorkProxy ins = new NetWorkProxy();

	private NetChangeListener mNetListener;

	private Timer mTimer;

	private UdpChannel mUdpChannel = new UdpChannel();

	public static NetWorkProxy newInstance() {
		return ins;
	}

	protected NetWorkProxy() {
		mUdpChannel.setPushMessageHandler(this);
	}

	public void sendHeartBeatRequest() {
		// TODO
	}

	/**
	 * 停止接收UDP消息
	 */
	public void stopReceiveMsg() {
	}

	public void startReceiveVideoData() {
	}

	public void stopReceiveVideoData() {
		mUdpChannel.stopReceiveVideoData();
	}

	public void startReceiveAudioata() {
	}

	public void stopReceiveAudioData() {
		mUdpChannel.stopReceiveAudioData();
	}

	public void handleNetBroken() {
		// stopHeartBeat();
		Intent localIntent = new Intent(RichResource.ACTION_NET_BROKEN);
		RichPlayApplication.getIns().sendBroadcast(localIntent);
	}

	public void handleNetConnected() {
		Intent localIntent = new Intent(RichResource.ACTION_NET_CONNECTED);
		RichPlayApplication.getIns().sendBroadcast(localIntent);
	}

	public void sendHttpRequest(String action, HttpRequest request,
			byte method, boolean isfile) {
		mHttpChannel.sendMessage(action, method, request, isfile);
	}

	public void startHeartBeat(int paramInt) {
		// TODO
	}

	public void startNetListener() {
		mNetListener = new NetChangeListener();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		RichPlayApplication.getIns().registerReceiver(mNetListener, filter);
	}

	public void stopHeartBeat() {
		if (this.mTimer != null) {
			this.mTimer.cancel();
			this.mTimer = null;
		}
	}

	public void stopNetListener() {
		if (mNetListener != null) {
			RichPlayApplication.getIns().unregisterReceiver(mNetListener);
			mNetListener = null;
		}
	}

	class NetChangeListener extends BroadcastReceiver {
		public void onReceive(Context paramContext, Intent intent) {
			String str = intent.getAction();
			if (ConnectivityManager.CONNECTIVITY_ACTION.equals(str)) {
				boolean noConnection = intent.getBooleanExtra(
						ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
				if (noConnection) {
					AppLog.out(MY_TAG, "net broken", AppLog.LEVEL_INFO);
					handleNetBroken();
				} else {
					AppLog.out(MY_TAG, "net connected", AppLog.LEVEL_INFO);
					handleNetConnected();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pengrun.realme.service.UdpMessageHandler#handleMessage(com.pengrun
	 * .realme.service.UdpMessage, int)
	 */
	@Override
	public void handleMessage(UdpMessage message) {
		switch (message.getModuleId()) {
		case UdpChannel.MODULE_CA: {
			break;
		}
		default: {
			break;
		}
		}
	}
}