package com.yingqida.richplay.packet;

public abstract interface HttpAction {
	public static final String ACTION_RELEASE_PIC = "";
	public static final String ACTION_WALL_PIC_INFO = "index.php?c=index&a=get_pic_detail";
	public static final String ACTION_WALL_PIC = "index.php?c=index&a=get_pic";
	public static final String ACTION_LOGIN = "/auth/authorize";
	public static final String ACTION_REGISTER = "/account/register";
	public static final String ACTION_EXIT = "/account/end_session";
	public static final String ACTION_USER_TIMELINE = "/comment/user_timeline";// 获取用户评论Timeline(基本稳定)
}
