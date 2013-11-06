package com.yingqida.richplay.entity;

import java.util.ArrayList;
import java.util.List;

import com.yingqida.richplay.baseapi.common.User;

public class Yuansu {

	private String id;

	private String name;

	private String pUrl;

	private String shareCount;

	private User user;

	private String remarkContent;

	private String time;

	// 关注状态1:已关注2:未关注
	private String followState;

	private String label;

	public List<Comment> commentList = new ArrayList<Comment>();

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFollowState() {
		return followState;
	}

	public void setFollowState(String followState) {
		this.followState = followState;
	}

	public String getRemarkContent() {
		return remarkContent;
	}

	public void setRemarkContent(String remarkContent) {
		this.remarkContent = remarkContent;
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

	public static enum Label {
		img, p, a
	}
}
