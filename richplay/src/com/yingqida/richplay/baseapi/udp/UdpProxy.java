package com.yingqida.richplay.baseapi.udp;

import java.net.InetAddress;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.yingqida.richplay.baseapi.common.GlobalVar;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.packet.UdpCommond;

public class UdpProxy implements UdpMessageHandler {

	private UdpChannel channel = new UdpChannel();

	private static UdpProxy ins = new UdpProxy();

	private Context context;

	private UdpProxy() {
		channel.setPushMessageHandler(this);
	}

	public static UdpProxy newInstance() {
		return ins;
	}

	public void startReceiveVideoData() {
	}

	public void stopReceiveVideoData() {
	}

	public void startReceiveAudioata() {
	}

	public void stopReceiveAudioData() {
		channel.stopReceiveAudioData();
	}

	public void sendUdpMediaMessage(byte[] src, InetAddress address, int port) {
		channel.sendMediaMessage(src, address, port);
	}

	public void sendUdpMessage(UdpRequest request, Byte req) {
		channel.sendMessage(request, req);
	}

	/**
	 * ע�������Ļ���
	 * 
	 * @param context
	 */
	public void registerContext(Context context) {
		this.context = context;
		channel.registerContext(context);
	}

	/**
	 * ֹͣ����UDP��Ϣ
	 */
	public void stopReceiveMsg() {
		channel.stopReceiveMsg();
	}

	@Override
	public void handleMessage(UdpMessage message) {
		if (null == context)
			return;
		sendUdpMessageUIN(message);
	}

	private void sendUdpMessageUI(UdpMessage message) {
		if (message.getModuleId() == UdpChannel.MODULE_PDU) {
		} else if (message.getModuleId() == UdpChannel.MODULE_CA) {
			// CA服务器推送的消息
		} else if (message.getModuleId() == UdpChannel.MODULE_AA) {
		}
	}

	private void sendUdpMessageUIN(UdpMessage message) {
		if (message.getModuleId() == UdpCommond.PDU_MODULE_ID) {
		} else if (message.getModuleId() == UdpCommond.PERSON_MODULE_ID) {

		}
	}

	public SharedPreferences getAppShare() {
		return context.getSharedPreferences(RichResource.CONFIG_NAME, 0);
	}

	public SQLiteDatabase getDb() {
		return GlobalVar.ins.getDbm(context).getWritableDatabase();
	}
}
