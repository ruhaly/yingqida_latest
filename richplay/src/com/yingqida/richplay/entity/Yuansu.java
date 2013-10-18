package com.yingqida.richplay.entity;

import com.yingqida.richplay.baseapi.common.User;

public class Yuansu {

	private String id;

	private String name;

	private String pUrl;

	// 1图片2文字
	private String type;

	private String shareCount;

	private User user;

	private String content;

	private String time;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpUrl() {
		return pUrl;
	}

	public void setpUrl(String pUrl) {
		this.pUrl = pUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShareCount() {
		return shareCount;
	}

	public void setShareCount(String shareCount) {
		this.shareCount = shareCount;
	}

	public User getUser() {
		if (null == user) {
			user = new User();
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
