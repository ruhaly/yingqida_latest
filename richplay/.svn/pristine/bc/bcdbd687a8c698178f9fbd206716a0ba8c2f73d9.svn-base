package com.yingqida.richplay.packet;

public interface UdpCommond {
	// 旧版本命令吗---------------------------------------------------------------
	// AA命令码
	public static final byte HEART_BEAT = 0x01;
	public static final byte KICK_OUT = 0x05;// 被踢下线

	// CA命令码
	public static final byte ENTER_ROOM = 0x01;// 进入视频聊天房间
	public static final byte EXIT_ROOM = 0x02;// 退出视频聊天房间
	public static final byte INVITE_VIDEO_CHAT = 0x10;// 邀请视频聊天
	public static final byte VIDEO_INVITE = 0x11;// 收到视频聊天邀请
	public static final byte MAIL_MSG = 0x12;// 邮件消息
	public static final byte AGREE_VIDEO = 0x13;// 接受视频邀请
	public static final byte CHARGE = 0x14;// 计费请求
	public static final byte CA_HT = 0x50;// CA心跳命令码

	// PDU命令码
	public static final byte SUBSCRIBE_MEDIA = 0x01;
	public static final byte UNSUBSCRIBE_MEDIA = 0x02;
	public static final byte GRANT_MICRO = 0x03;
	public static final byte DEPRIVE_MICRO = 0x04;
	public static final byte ROW_MICRO = 0x05;
	public static final byte RELEASE_MICRO = 0x06;
	public static final byte ADD_MICRO = 0x07;
	public static final byte TEMP_MANAGER = 0x08;
	public static final byte UNTEMP_MANAGER = 0x09;
	public static final byte SET_MICRO_ATTR = 0x0A;
	public static final byte SET_ROOM_ATTR = 0x0B;
	public static final byte SET_ROOM_PWD = 0x0C;
	public static final byte KICK_MEMBER = 0x0D;
	public static final byte FORBID_IP = 0x0E;
	public static final byte UNFORBID_IP = 0x10;
	public static final byte GET_MEMBERS = 0x12;
	public static final byte GET_MICRO_USERS = 0x13;// 获得当前上麦成员
	public static final byte TEXT_MSG = 0x30;
	public static final byte PRIVATE_MSG = 0x31;
	public static final byte CONSUME_MSG = 0x32;
	public static final byte USER_ENTER = 0x51;// 用户进入房间广播
	public static final byte USER_LEAVE = 0x52;// 用户离开广播
	public static final byte USER_MICRO_ON = 0x53;// 用户上麦广播
	public static final byte PRIMARY_MSG = 0x54;// 普通消息广播
	public static final byte USER_MICRO_OFF = 0x55;// 用户下麦广播

	// 新版本命令吗---------------------------------------------------------------
	// PERSON
	public static final byte PERSON_MODULE_ID = 0x01;
	public static final byte PERSON_05_KICK_OUT = 0x05;
	public static final byte PERSON_06_ENTER_ROOM = 0x06;
	public static final byte PERSON_07_LEAVE_ROOM = 0x07;

	// CA

	public static final byte CA_MODULE_ID = 0x02;
	public static final byte CA_01_CHECK_VERSION = 0x01;
	public static final byte CA_02_UPDATE_VERSION = 0x02;
	public static final byte CA_03_RECOMMOND = 0x03;
	public static final byte CA_04_DETAIL = 0x04;
	public static final byte CA_05_VCODE = 0x05;
	public static final byte CA_06_REGISTER = 0x06;
	public static final byte CA_07_SEARCH = 0X07;
	public static final byte CA_08_PROFILE = 0X08;
	public static final byte CA_0A_RETRIEVEPWD = 0X0A;

	// AA

	public static final byte AA_MODULE_ID = 0x05;
	public static final byte AA_01_LOGIN = 0x01;
	public static final byte AA_02_LOGOUT = 0x02;
	public static final byte AA_03_REGISTER = 0x03;
	public static final byte AA_04_VALIDATE_CODE = 0x04;
	public static final byte AA_05_RELOGIN = 0x05;
	public static final byte AA_06_HEART_BEAT = 0x06;
	public static final byte AA_09_ACCOUNT_INF = 0x09;
	public static final byte AA_10_PUSH_BALANCE = 0x10;

	public static final byte AA_11_MORE_APP = 0x11;
	public static final byte AA_12_RECHARGE = 0x12;
	public static final byte AA_13_FEEDBACK = 0x13;
	public static final byte AA_17_VALIDATE_CODE = 0x17;// 获取验证码
	public static final byte AA_18_BANGDING_PHONE = 0x18;// 绑定手机
	public static final byte AA_19_LIANGHAO = 0x19;// 获取验证码
	public static final byte AA_0x1A_ONLINE_KEFU = 0x1A;// 获取在线客服

	// YJZQ PM

	public static final byte YJZQ_MODULE_ID = 0x04;
	public static final byte YJZQ_01_SEND_INVITE = 0x01;
	public static final byte YJZQ_02_RECEIVE_INVITE = 0x02;
	public static final byte YJZQ_03_ACCEPT_INVITE = 0x03;
	public static final byte YJZQ_04_MAIL_MSG = 0x04;
	public static final byte YJZQ_01_RECOMMOND_USER = 0x05;
	public static final byte YJZQ_02_USER_DETAIL = 0x06;
	public static final byte YJZQ_03_QUERY_USER = 0x07;
	public static final byte YJZQ_04_GET_COMMENT = 0x08;
	public static final byte YJZQ_05_SUBMIT_COMMENT = 0x09;
	public static final byte YJZQ_06_MAIL_LIST = 0x10;
	public static final byte YJZQ_07_MAIL_DETAIL = 0x11;
	public static final byte YJZQ_08_MODIFY_MAIL_STATUS = 0x12;
	public static final byte YJZQ_09_ADD_FOCUS = 0x12;
	public static final byte YJZQ_10_ADD_BLACKLIST = 0x12;
	public static final byte YJZQ_11_DEL_MAIL = 0x13;
	public static final byte YJZQ_12_MODIFY_PROFILE = 0x14;
	public static final byte YJZQ_13_SET_MATE = 0x15;
	public static final byte YJZQ_14_UPLOAD_FILE = 0x16;
	public static final byte YJZQ_15_DEL_FILE = 0x17;
	public static final byte YJZQ_16_MY_PHOTO = 0x18;
	public static final byte YJZQ_17_VIEW_ME = 0x19;
	public static final byte YJZQ_18_MY_FOCUS = 0x19;
	public static final byte YJZQ_19_MY_BLACKLIST = 0x19;
	public static final byte YJZQ_20_DEL_BLACKLIST = 0x20;
	public static final byte YJZQ_21_DEL_FOCUS = 0x20;
	public static final byte YJZQ_21_PUSH_MSG = 0x21;
	public static final byte YJZQ_22_PUSH_TIPS = 0x22;

	// PDU

	public static final byte PDU_MODULE_ID = 0x03;

	public static final byte PDU_01_SUBSCRIBE_MEDIA = 0x01;
	public static final byte PDU_02_UNSUBSCRIBE_MEDIA = 0x02;
	public static final byte PDU_03_GRANT_MICRO = 0x03;
	public static final byte PDU_04_DEPRIVE_MICRO = 0x04;
	public static final byte PDU_05_ROW_MICRO = 0x05;
	public static final byte PDU_06_RELEASE_MICRO = 0x06;
	public static final byte PDU_07_ADD_MICRO = 0x07;
	public static final byte PDU_08_TEMP_MANAGER = 0x08;
	public static final byte PDU_09_UNTEMP_MANAGER = 0x09;
	public static final byte PDU_0A_SET_MICRO_ATTR = 0x0A;
	public static final byte PDU_0B_SET_ROOM_ATTR = 0x0B;
	public static final byte PDU_0C_SET_ROOM_PWD = 0x0C;
	public static final byte PDU_0D_KICK_MEMBER = 0x0D;
	public static final byte PDU_0E_FORBID_IP = 0x0E;
	public static final byte PDU_10_UNFORBID_IP = 0x10;
	public static final byte PDU_12_GET_MEMBERS = 0x12;
	public static final byte PDU_13_GET_MICRO_USERS = 0x13;// 获得当前上麦成员
	public static final byte PDU_30_TEXT_MSG = 0x30;
	public static final byte PDU_31_PRIVATE_MSG = 0x31;
	public static final byte PDU_32_CONSUME_MSG = 0x32;
	public static final byte PDU_51_USER_ENTER = 0x51;// 用户进入房间广播
	public static final byte PDU_52_USER_LEAVE = 0x52;// 用户离开广播
	public static final byte PDU_53_USER_MICRO_ON = 0x53;// 用户上麦广播
	public static final byte PDU_54_PRIMARY_MSG = 0x54;// 普通消息广播
	public static final byte PDU_55_USER_MICRO_OFF = 0x55;// 用户下麦广播
	public static final byte PDU_HEART_BEAT = 0x00;

	// UM
	public static final byte UM_MODULE_ID = 0x06;

	public static final byte UM_02_ACCOUNT_DETAIL = 0x02;

	// SYS
	public static final byte SYS_MODULE_ID = 0x08;

	public static final byte SYS_01_NOTICE = 0x01;
	public static final byte SYS_02_APP_LIST = 0x02;
	public static final byte SYS_03_APP_DETAIL = 0x03;

	// MLG
	public static final byte MLG_01_CHANNEL_LSIT_OLD = 0x01;
	public static final byte MLG_02_VIDEO_PERIOD_OLD = 0x02;
	public static final byte MLG_03_GOOD_INFO = 0x03;
	public static final byte MLG_04_ORDER_COMFIRM = 0x04;
	public static final byte MLG_05_ORDER_LIST = 0x05;
	public static final byte MLG_06_ORDER_DETAIL = 0x06;
	public static final byte MLG_07_GOOD_PINGJIA = 0x07;
	public static final byte MLG_08_ORDER_RECEIVE = 0x08;
	public static final byte MLG_09_ADDRESS_LIST = 0x09;
	public static final byte MLG_010_DEL_ADDRESS = 0x10;
	public static final byte MLG_011_ADD_ADDRESS = 0x11;
	public static final byte MLG_011_EDIT_ADDRESS = 0x11;
	public static final byte MLG_012_ACCOUNT_INFO = 0x12;
	public static final byte MLG_013_SHANPING_PINGJIA = 0x13;
	public static final byte MLG_014_MY_PINGJIA = 0x14;
	public static final byte MLG_015_PINPAI_INFO = 0x15;
	public static final byte MLG_016_ORDER_UNHANDLE = 0x16;
	public static final byte MLG_017_SORT_INFO_OLD = 0x17;
	public static final byte MLG_018_CHECK_SUPPORT = 0x18;
	// public static final byte MLG_019_AD_LIST_OLD = 0x19;
	public static final byte MLG_020_EDIT_ADDRESS = 0x20;
	public static final byte MLG_021_ALIPAY_INFO = 0x21;

	public static final byte MLG_022_SORT_INFO = 0x22;
	public static final byte MLG_023_CHANNEL_LSIT = 0x23;
	public static final byte MLG_024_VIDEO_PERIOD = 0x24;
	public static final byte MLG_025_AD_LIST = 0x25;

	public static final byte MLG_026_MAIL_MSG = 0x26;
	public static final byte MLG_27_PUSH_MSG = 0x27;
	public static final byte PUSH_UNDERLINE_MSG = 0x2E;

	public static final byte MLG_2A_UPLOAD_FILE = 0x2A;

	public static final byte MLG_29_ENTER_CHAT_ROOM = 0x29;
	public static final byte MLG_30_EXIT_CHAT_ROOM = 0x30;
	public static final byte MLG_40_CHAT_ROOM_busy = 0x40;
	public static final byte MLG_31_PRIVATE_ROOM_MSG = 0X31;

	public static final byte MLG_2B_DELIVER_AREA = 0x2B;

	public static final byte MLG_1B_PIC_MSG = 0X1B;

	public static final byte MLG_2C_ONLINE_KEFU = 0x2C;

	// MLG
	public static final byte MLG_MODULE_ID = 0X07;

	// 账号不存在
	public static final byte MLG_ACCOUNT_NOT_EXIST = 0X60;

	// 未绑定手机
	public static final byte MLG_NOT_BANGDING = 0X61;

	// 数据格式错误
	public static final byte MLG_DATA_ERROR = -5;

	// 广告墙
	public static final byte MLG_GUANGGAOQIANG = 0x2F;

	// 添加到购物车
	public static final byte MLG_ADD_TO_CART = 0x38;

	// 购物车商品列表
	public static final byte MLG_CART_GOODS = 0x31;
	// 批量支付
	public static final byte MLG_PILIANG_ZHIFU = 0x32;

	// 商家信息
	public static final byte MLG_SJ_INFO = 0x33;

	// 商家商品列表
	public static final byte MLG_SJ_GOODS = 0x34;

	// 购物车商品列表
	public static final byte MLG_IM_CART_GOODS = 0x35;

	public static final byte MLG_PILIANG_ZHIFU2 = 0x36;

	public static final byte MLG_QUEREN_FENXIANG = 0x37;

	public static final byte MLG_FANLI = 0x39;

	public static final byte MLG_SHARE_CONFIRM = 0x31;

	public static final byte MLG_SHETUAN_LIEBIAO = 0x1D;

	public static final byte MLG_JIARU_SHETUAN = 0x1E;

	// 修改资料
	public static final byte MLG_MODIFY_INFO = 0x1F;

	// 修改密碼
	public static final byte MLG_MODIFY_PWD = 0x1F;

	// 推送广告
	public static final byte MLG_TUISONG_AD = 0x08;
}
