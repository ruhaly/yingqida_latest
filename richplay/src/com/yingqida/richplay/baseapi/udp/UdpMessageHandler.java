package com.yingqida.richplay.baseapi.udp;

public abstract interface UdpMessageHandler {
	public abstract void handleMessage(UdpMessage message);
}