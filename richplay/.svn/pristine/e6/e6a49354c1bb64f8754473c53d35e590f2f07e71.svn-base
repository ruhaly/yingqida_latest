package com.yingqida.richplay.baseapi.udp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;

public class UdpRequest {

	private int timeout = 30000;

	private Timer mTimer = new Timer();

	private UdpMessage message;

	private boolean responsed = false;

	private InetAddress address;

	private int port;

	/**
	 * ��Ӧ�ص�
	 */
	private UdpMessageHandler rspHandler;

	/**
	 * �����쳣�ص�
	 */
	private UdpNetHandler netHandler;

	public String key;

	public int spId;

	public UdpRequest(UdpMessageHandler rspHandler, UdpNetHandler netHandler,
			String ip, int port, String key) {
		this.responsed = false;
		this.rspHandler = rspHandler;
		this.netHandler = netHandler;
		try {
			this.address = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			this.address = null;
		}
		this.key = key;
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Timer getmTimer() {
		return mTimer;
	}

	public void setmTimer(Timer mTimer) {
		this.mTimer = mTimer;
	}

	public UdpMessage getMessage() {
		return message;
	}

	public void setMessage(UdpMessage message) {
		this.message = message;
	}

	public UdpMessageHandler getRspHandler() {
		return rspHandler;
	}

	public void setRspHandler(UdpMessageHandler rspHandler) {
		this.rspHandler = rspHandler;
	}

	public boolean isResponsed() {
		return responsed;
	}

	public void setResponsed(boolean responsed) {
		this.responsed = responsed;
	}

	public UdpNetHandler getNetHandler() {
		return netHandler;
	}

	public void setNetHandler(UdpNetHandler netHandler) {
		this.netHandler = netHandler;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void cancel() {
		synchronized (this) {
			if (this.mTimer != null) {
				this.mTimer.cancel();
				this.rspHandler = null;
			}
			mTimer = null;
		}
	}
}