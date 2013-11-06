package com.yingqida.richplay.entity;

public class Comment {
	// 评论id
	private String cid = "";
	// 发表评论的用户id
	private String uid = "";
	private String uname = "";
	private String headerUrl = "";
	private String time = "";
	// 评论内容
	private String content = "";

	/**
	 * 是否有头像
	 */
	private String is_avatar;

	public String getIs_avatar() {
		return is_avatar;
	}

	public void setIs_avatar(String is_avatar) {
		this.is_avatar = is_avatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getHeaderUrl() {
		return headerUrl;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}

}
