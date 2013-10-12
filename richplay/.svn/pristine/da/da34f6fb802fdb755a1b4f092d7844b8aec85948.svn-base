package com.yingqida.richplay.baseapi.udp;

import java.io.Serializable;

import com.yingqida.richplay.baseapi.AppLog;

public class UdpMessage implements Serializable {
	private static final long serialVersionUID = -7092015440596614956L;

	private static final String TAG = "UdpMessage_new";

	/**
	 * 模块ID
	 */
	private byte moduleId;

	/**
	 * 命令码
	 */
	private byte commond;

	/**
	 * 消息状态
	 */
	private byte status = -1;

	/**
	 * 消息长度
	 */
	private short length;

	/**
	 * 消息序列号
	 */
	private byte sequence;

	/**
	 * session
	 */
	private long session;

	/**
	 * 消息体
	 */
	private String body = "";

	public int spId;

	public String getBody() {
		return this.body;
	}

	public byte getCommond() {
		return this.commond;
	}

	public short getLength() {
		return this.length;
	}

	public byte getModuleId() {
		return this.moduleId;
	}

	public byte getSequence() {
		return this.sequence;
	}

	public long getSession() {
		return this.session;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setBody(String param, String key, boolean receiveFlag) {

		AppLog.out(TAG, param, AppLog.LEVEL_INFO);

		if (null == param || "".equals(param))
			return;
		if (null != key && !"".equals(key)) {
			if (receiveFlag) {
				try {
					// this.body = new String(ZlibUtil.decompress(DataEncryption
					// .unEncryption(Base64.decode(param), key)));
				} catch (Exception e) {
					return;
				}
			} else {
				// this.body = Base64.encode(DataEncryption.encryption(
				// ZlibUtil.compress(param.getBytes()), key));
			}
		} else
			this.body = param;
	}

	public void setCommond(byte paramByte) {
		this.commond = paramByte;
	}

	public void setLength(short paramShort) {
		this.length = paramShort;
	}

	public void setModuleId(byte paramByte) {
		this.moduleId = paramByte;
	}

	public void setSequence(byte paramByte) {
		this.sequence = paramByte;
	}

	public void setSession(long session) {
		this.session = session;
	}

	public void setStatus(byte paramByte) {
		this.status = paramByte;
	}

	@Override
	public String toString() {
		return "UdpMessage " + "[ commond=" + commond + ", length=" + length
				+ ", moduleId=" + moduleId + ", sequence=" + sequence
				+ ", session=" + session + ", status=" + status + "]"
				+ "\n body = " + body;
	}
}
