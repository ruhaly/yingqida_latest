package com.yingqida.richplay.baseapi.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import android.content.Context;

import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.net.ReturnCode;
import com.yingqida.richplay.packet.UdpCommond;

public class UdpChannel extends Thread {

	private static final int VIDEO_CACHE_SIZE = 30;

	private static final int AUDIO_CACHE_SIZE = 30;

	/**
	 * 消息头长度
	 */
	private static final int HEAD_LENGTH = 11;

	/**
	 * 媒体类型
	 */
	public static final int TYPE_AUDIO = 1;
	public static final int TYPE_VIDEO = 2;

	public static final byte MODULE_AA = 0;
	public static final byte MODULE_CA = 2;
	public static final byte MODULE_PDU = 3;

	private static final String MY_TAG = "UdpChannel";

	/**
	 * 消息发送队列
	 */
	private Map<String, UdpRequest> mRequestQueue = new HashMap<String, UdpRequest>();

	private DatagramSocket mSocket;

	private UdpMessageHandler pushMessageHandler;

	private boolean running = false;

	private byte sequence = 1;

	/**
	 * 接收视频数据缓冲队列
	 */
	private List<byte[]> videoData = new LinkedList<byte[]>();

	private List<byte[]> audioData = new LinkedList<byte[]>();

	private static List<byte[]> primaryData = new LinkedList<byte[]>();

	private static Map<String, Boolean> threadKey = new HashMap<String, Boolean>(
			1);
	private Context context;

	public void registerContext(Context context) {
		this.context = context;
	}

	public UdpChannel() {
		restartSocket();
	}

	private byte[] createSendData(UdpMessage message) {
		int length = message.getBody().getBytes().length;
		ByteBuffer buffer = ByteBuffer.allocate(length + HEAD_LENGTH);
		buffer.putShort((short) (length + HEAD_LENGTH - 2));
		buffer.put(message.getModuleId());
		buffer.put(message.getCommond());
		buffer.put(-1 == message.getStatus() ? (byte) 0xFF : message
				.getStatus());
		buffer.put(message.getSequence());
		buffer.put((byte) ((message.getSession() >> 32) & 0xFF));
		buffer.putInt((int) (message.getSession() & 0xFFFFFFFF));
		buffer.put(message.getBody().getBytes());
		return buffer.array();
	}

	private void handleNonMediaMessage(byte[] src) {
		ByteBuffer buffer = ByteBuffer.wrap(src);
		UdpMessage message = new UdpMessage();
		message.setLength((short) (buffer.getShort() - HEAD_LENGTH + 2));
		message.setModuleId(buffer.get());
		message.setCommond(buffer.get());
		message.setStatus(buffer.get());
		message.setSequence(buffer.get());
		message.setSession((buffer.get() << 32) | buffer.getInt());
		byte[] body = null;
		if (message.getLength() <= src.length - HEAD_LENGTH) {
			body = new byte[message.getLength()];
			buffer.get(body, 0, message.getLength());
		}
		UdpRequest request = mRequestQueue.remove(convertKey(
				message.getSequence(), message.getCommond()));
		if (request != null) {
			message.setBody(new String(body), request.key, true);
			request.setResponsed(true);
			message.spId = request.spId;
			if (null != request.getRspHandler()) {
				request.getRspHandler().handleMessage(message);
			}
			request.cancel();
			// special handle(relogin)
			if (message.getStatus() == ReturnCode.RETURN_CODE_01
					|| message.getStatus() == ReturnCode.RETURN_CODE_03) {
				if (null != context
						&& !(message.getCommond() == UdpCommond.AA_06_HEART_BEAT && message
								.getModuleId() == UdpCommond.AA_MODULE_ID)) {
					// System.out.println("重新登录---------------------！！！");
				}
			}
		} else {
			if (null != pushMessageHandler) {
				// System.out.println("响应b:RealmeResource.key"
				// + RealmeResource.key);
				message.setBody(new String(body), "1111111", true);
				pushMessageHandler.handleMessage(message);
			}
		}
		// AppLog.out(MY_TAG, "received " + message.getBody(),
		// AppLog.LEVEL_INFO);
		AppLog.out(MY_TAG, "received " + message.toString(), AppLog.LEVEL_INFO);
	}

	private boolean initSocket() {
		try {
			mSocket = new DatagramSocket();
			mSocket.setReceiveBufferSize(1024 * 128);
			mSocket.setSendBufferSize(1024 * 128 - 1);
		} catch (SocketException e) {
			AppLog.out(MY_TAG, "212" + e.getMessage(), AppLog.LEVEL_ERROR);
			return false;
		}
		return true;
	}

	private void primaryHandle(byte[] src) {
		if (src == null) {
			return;
		}
		ByteBuffer buffer = ByteBuffer.wrap(src);
		byte firstByte = buffer.get();
		byte msgIndicator = (byte) ((firstByte >> 7) & 0x1);
		if (msgIndicator == 0) {
			if (src.length < 10) {
				return;
			}
			synchronized (primaryData) {
				primaryData.add(src);
			}
			synchronized (lock) {
				lock.notify();
			}
		} else {
		}
	}

	private synchronized void startTimer(final UdpRequest request) {
		synchronized (request) {
			if (null == request.getmTimer()) {
				return;
			}
			request.getmTimer().schedule(new TimerTask() {
				@Override
				public void run() {
					UdpNetHandler timerHandler = request.getNetHandler();
					if (null != timerHandler)
						timerHandler.handleNet(request.getMessage());
					mRequestQueue.remove(convertKey(request.getMessage()
							.getSequence(), request.getMessage().getCommond()));
				}
			}, request.getTimeout());
		}

	}

	public void run() {
		while (running) {
			// 接收消息
			byte[] src = new byte[10240];
			DatagramPacket packet = new DatagramPacket(src, src.length);
			try {
				mSocket.receive(packet);
				byte[] res = new byte[packet.getLength()];
				System.arraycopy(src, 0, res, 0, res.length);
				primaryHandle(res);
			} catch (IOException e) {
				AppLog.out(MY_TAG, "223" + e.getMessage(), AppLog.LEVEL_ERROR);
			}
		}
	}

	protected void sendMediaMessage(byte[] src, InetAddress address, int port) {
		if (src == null || null == address) {
			return;
		}
		DatagramPacket localDatagramPacket = new DatagramPacket(src,
				src.length, address, port);
		try {
			this.mSocket.send(localDatagramPacket);
		} catch (IOException e) {
			AppLog.out(MY_TAG, "350" + e.getMessage(), AppLog.LEVEL_ERROR);
		}
	}

	// protected void sendMessage(UdpRequest_new request)
	// {
	// if (null == request.getAddress())
	// {
	// return;
	// }
	// startReceiveMsg();
	// UdpMessage_new message = request.getMessage();
	// message.setSequence(sequence);
	// sequence++;
	// if (sequence == Byte.MAX_VALUE)
	// {
	// sequence = 1;
	// }
	// AppLog.out(MY_TAG, request.getAddress().getHostAddress() + " "
	// + request.getPort(), AppLog.LEVEL_INFO);
	// AppLog.out(MY_TAG, request.getMessage().toString(), AppLog.LEVEL_INFO);
	//
	// byte[] src = createSendData(message);
	// int length = src.length;
	// DatagramPacket localDatagramPacket = new DatagramPacket(src, length,
	// request.getAddress(), request.getPort());
	// startTimer(request);
	// try
	// {
	// mSocket.send(localDatagramPacket);
	// }
	// catch (IOException e)
	// {
	// UdpNetHandler_new handler = request.getNetHandler();
	// if (null != handler)
	// handler.handleNet(request.getMessage());
	// AppLog.out(MY_TAG, "277" + e.getMessage(), AppLog.LEVEL_ERROR);
	// }
	//
	// if (null != request.getRspHandler() && null != request.getNetHandler())
	// mRequestQueue.put(
	// convertKey(message.getSequence(), message.getCommond()),
	// request);
	// }

	public void setPushMessageHandler(UdpMessageHandler handler) {
		this.pushMessageHandler = handler;
	}

	void startReceiveMsg() {
		String name = java.util.UUID.randomUUID().toString();
		synchronized (threadKey) {
			threadKey.clear();
			threadKey.put(name, true);
		}
		// synchronized (lock) {
		// lock.notify();
		// }
		new MessageReceiver(name).start();
		new PrimaryDataHandler(name).start();
	}

	int count;

	byte[] lock = new byte[0];

	class MessageReceiver extends Thread {

		public MessageReceiver(String threadName) {
			super(threadName);
		}

		public void run() {
			while (checkRunning()) {
				byte[] src = new byte[10240];
				DatagramPacket packet = new DatagramPacket(src, src.length);
				try {
					mSocket.receive(packet);
					byte[] res = new byte[packet.getLength()];
					System.arraycopy(src, 0, res, 0, res.length);
					primaryHandle(res);
				} catch (Exception e) {
					AppLog.out(MY_TAG, "265" + e.getMessage(),
							AppLog.LEVEL_ERROR);
					try {
						Thread.sleep(1000l);
					} catch (InterruptedException e1) {
						AppLog.out(MY_TAG, "272" + e1.getMessage(),
								AppLog.LEVEL_ERROR);
					}
				}
			}
		}

		private boolean checkRunning() {
			synchronized (threadKey) {
				return null != threadKey.get(getName())
						&& threadKey.get(getName());
			}
		}
	}

	class PrimaryDataHandler extends Thread {

		public PrimaryDataHandler(String threadName) {
			super(threadName);
		}

		@Override
		public void run() {
			while (checkRunning()) {
				byte[] data = null;
				synchronized (primaryData) {
					if (!primaryData.isEmpty()) {
						data = primaryData.remove(0);
					}
				}
				if (null != data) {
					handleNonMediaMessage(data);
				} else {
					try {
						synchronized (lock) {
							lock.wait();
						}
					} catch (Exception e) {
					}
				}
			}
		}

		private boolean checkRunning() {
			synchronized (threadKey) {
				return null != threadKey.get(getName())
						&& threadKey.get(getName());
			}
		}
	}

	void stopReceiveMsg() {
		running = false;
	}

	/**
	 * 是否接收视频数据
	 */
	private boolean receiveVideoData = false;

	/**
	 * 是否接受音频数据
	 */
	private boolean receiveAudioData = false;

	public void stopReceiveVideoData() {
		receiveVideoData = false;
	}

	public void stopReceiveAudioData() {
		receiveAudioData = false;
	}

	/**
	 * 
	 * 处理视频媒体数据线程
	 * 
	 * @author wjd
	 * 
	 */

	private String convertKey(byte seq, byte cmd) {
		return seq + "+" + cmd;
	}

	protected void sendMessage(final UdpRequest request, final Byte seq) {
		new Thread(new Runnable() {
			public void run() {
				sendMessageImpl(request, seq);
			}
		}).start();
	}

	byte[] slock = new byte[0];

	protected void sendMessageImpl(UdpRequest request, Byte seq) {
		if (null == request.getAddress()) {
			return;
		}
		UdpMessage message = request.getMessage();
		if (seq == null) {
			synchronized (slock) {
				message.setSequence(sequence);
				sequence++;
				if (sequence == Byte.MAX_VALUE) {
					sequence = 1;
				}
			}
		} else {
			message.setSequence(seq);
		}
		AppLog.out(MY_TAG, request.getMessage().toString(), AppLog.LEVEL_INFO);

		byte[] src = createSendData(message);
		int length = src.length;
		DatagramPacket localDatagramPacket = new DatagramPacket(src, length,
				request.getAddress(), request.getPort());
		try {
			if (null == seq)
				startTimer(request);
			Thread.sleep(100l);
			mSocket.send(localDatagramPacket);
		} catch (InterruptedException e) {
			AppLog.out(MY_TAG, "321" + e.getMessage(), AppLog.LEVEL_ERROR);
		} catch (IOException e) {
			UdpNetHandler handler = request.getNetHandler();
			if (null != handler)
				handler.handleNet(request.getMessage());
		} catch (NullPointerException e) {
			UdpNetHandler handler = request.getNetHandler();
			if (null != handler)
				handler.handleNet(request.getMessage());
			restartSocket();
		}

		if (null != request.getRspHandler() && null != request.getNetHandler()
				&& null == seq) {
			mRequestQueue.put(
					convertKey(message.getSequence(), message.getCommond()),
					request);
		}

	}

	protected void closeSocket() {
		if (mSocket != null) {
			mSocket.close();
		}
		stopReceiveAudioData();
		stopReceiveVideoData();
		mSocket = null;
	}

	protected void initializeSocket() {
		do {
			AppLog.out("UdpChannel", "INIT SOCKET", AppLog.LEVEL_INFO);
		} while (!initSocket());
		startReceiveMsg();
	}

	protected void restartSocket() {
		closeSocket();
		initializeSocket();
	}
}