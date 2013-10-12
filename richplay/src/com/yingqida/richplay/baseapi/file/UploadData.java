package com.yingqida.richplay.baseapi.file;

public class UploadData {

	public static final int TYPE_HEAD = 1;

	public static final int TYPE_PHOTO = 2;

	public static final int TYPE_VIDEO = 3;

	public static final int TYPE_VIDEO_MSG = 4;

	public static final int TYPE_AUDIO_MSG = 3;

	public static final int TYPE_FILE = 6;

	public static final int TYPE_ZOOM = 7;

	/**
	 * �û�id
	 */
	private String uid;

	/**
	 * ͼƬ���� 1��ͷ�� 2������� 3����Ƶ
	 */
	private int type;

	private byte[] data;

	private String content_type;

	private String file_name;

	private String url;

	public UploadData(String uid, int type, byte[] data, String content_type,
			String file_name, String url) {
		super();
		this.uid = uid;
		this.type = type;
		this.data = data;
		this.content_type = content_type;
		this.file_name = file_name;
		this.url = url;
	}

	public String getUid() {
		return uid;
	}

	public int getType() {
		return type;
	}

	public byte[] getData() {
		return data;
	}

	public String getContent_type() {
		return content_type;
	}

	public String getFile_name() {
		return file_name;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "UploadData [uid=" + uid + ", type=" + type + ", data="
				+ data.length + ", content_type=" + content_type
				+ ", file_name=" + file_name + ", url=" + url + "]";
	}
}
