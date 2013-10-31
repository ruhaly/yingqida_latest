package com.yingqida.richplay.packet;

public abstract interface HttpAction {
	public static final String ACTION_RELEASE_PIC = "";
	public static final String ACTION_WALL_PIC_INFO = "index.php?c=index&a=get_pic_detail";
	public static final String ACTION_WALL_PIC = "index.php?c=index&a=get_pic";
	public static final String ACTION_LOGIN = "/auth/authorize";
	public static final String ACTION_REGISTER = "/account/register";
	public static final String ACTION_LOGIN_CAPTCHA = "/captcha/authorize";
	public static final String ACTION_REGISTER_CAPTCHA = "/captcha/register";
	public static final String ACTION_EXIT = "/account/end_session";
	public static final String ACTION_USER_TIMELINE = "/comment/user_timeline";// 获取用户评论Timeline(基本稳定)
	public static final String ACTION_HOME_TIMELINE = "/comment/home_timeline";// 获取用户评论Timeline(基本稳定)
	public static final String ACTION_MODIFY_PWD = "/account/modify_password";
	public static final String ACTION_SEARCH_USER = "/search/user";
	public static final String ACTION_SEARCH_COMMENT = "/search/comment";
	public static final String ACTION_FOLLOW_USER_OR_YUANSU = "/relationship/follow";
	public static final String ACTION_UNFOLLOW_USER_OR_YUANSU = "/relationship/unfollow";
	public static final String ACTION_YUANSU_COMMENT = "/comment/show";
	public static final String ACTION_SHARE = "/comment/share";
	public static final String ACTION_COMMENT_YUANSU = "/comment/create";
	public static final String ACTION_GET_REMARKTOKEN = "/auth/remark_token";
	public static final String ACTION_GET_FOLLOWER = "/relationship/follower";
	public static final String ACTION_GUANZHU_YUANSU = "/relationship/following_remark";
	public static final String ACTION_GUANZHU_YONGHU = "/relationship/following_user";
	public static final String ACTION_COUNT = "/user/counts";
	public static final String ACTION_UPLOAD_HEAD = "/user/modify_avatar";
	public static final String ACTION_USER_INFO = "/user/show";
	public static final String ACTION_MODIFY_USER_INFO = "/user/modify_info";

}
