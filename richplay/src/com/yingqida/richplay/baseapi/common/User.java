package com.yingqida.richplay.baseapi.common;

import com.yingqida.richplay.baseapi.Constant;

/**
 * 用户
 * 
 * @author hlr
 * 
 */
public class User {

	public String remarkToken;

	// 用户id
	public String uid;

	// 用户账号
	public String account;

	// 密码
	public String pwd;

	// 姓名
	public String name;

	// 昵称
	public String nickName;

	// 性别1男2女
	public int sex;

	public String address;

	public String headUrl;

	private String comment_content;

	// 正在关注
	private String isGuanzhu;
	// 被关注
	private String beiGuanzhu;

	// 关注状态 1:已经关注2:未关注
	private String stateGuanzhu = Constant.UN_FOLLOW;

	public String getIsGuanzhu() {
		return isGuanzhu;
	}

	public void setIsGuanzhu(String isGuanzhu) {
		this.isGuanzhu = isGuanzhu;
	}

	public String getBeiGuanzhu() {
		return beiGuanzhu;
	}

	public void setBeiGuanzhu(String beiGuanzhu) {
		this.beiGuanzhu = beiGuanzhu;
	}

	public String getStateGuanzhu() {
		return stateGuanzhu;
	}

	public void setStateGuanzhu(String stateGuanzhu) {
		this.stateGuanzhu = stateGuanzhu;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRemarkToken() {
		return remarkToken;
	}

	public void setRemarkToken(String remarkToken) {
		this.remarkToken = remarkToken;
	}

}