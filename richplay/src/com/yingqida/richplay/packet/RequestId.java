package com.yingqida.richplay.packet;

public class RequestId {
	public static final int GET_REMARKTOKEN = 10000;
	public static final int LOGIN_REQUESTID = 10001;
	public static final int REGISTER_REQUESTID = 10002;
	public static final int EXIT_REQUESTID = 10003;
	public static final int USER_TIMELINE = 10004;
	public static final int USER_TIMELINE2 = 100041;
	public static final int HOME_TIMELINE = 10005;
	public static final int MODIFY_PWD = 10005;
	public static final int GET_YUANSU = 10006;
	public static final int GET_USER = 10007;
	public static final int FOLLOW_USER = 10008;
	public static final int UNFOLLOW_USER = 10009;
	public static final int FOLLOW_YUANSU = 100080;
	public static final int UNFOLLOW_YUANSU = 100090;
	public static final int YUANSU_COMMENT = 100010;
	public static final int SHARE = 100011;
	public static final int COMMENT_YUANSU = 100012;

	public static final int PCENTER_FAYAN = 100013;

	public static final int PCENTER_FOLLOWER = 100014;
	public static final int PCENTER_GUANZHU_YUANSU = 100015;
	public static final int PCENTER_GUANZHU_YONGHU = 100016;

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
