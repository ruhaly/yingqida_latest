package com.yingqida.richplay.packet;

public class RequestId {

	public static final int LOGIN_REQUESTID = 10001;
	public static final int REGISTER_REQUESTID = 10002;
	public static final int EXIT_REQUESTID = 10003;
	public static final int USER_TIMELINE = 10004;
	public static final int HOME_TIMELINE = 10005;
	public static final int MODIFY_PWD = 10005;
	public static final int GET_YUANSU = 10006;
	public static final int GET_USER = 10007;
	public static final int FOLLOW_USER = 10008;
	public static final int UNFOLLOW_USER = 10009;
	public static final int YUANSU_COMMENT = 100010;

	/**
	 * 发布图片
	 */
	public static final int RELEASE_PIC = 1001;
	/**
	 * 查看图片墙详情
	 */
	public static final int WALL_PIC_INFO = 1002;

	/**
	 * 获取图片墙
	 */
	public static final int WALL_PIC = 1003;
}
