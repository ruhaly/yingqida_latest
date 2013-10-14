package com.yingqida.richplay.baseapi.common;

public class RichResource {

	public static final String ENCODE_UTF = "UTF-8";
	public static final byte NO_ERROR = 0;
	public static final byte ERROR_NET = -1;
	public static final byte ERROR_FWQ = -2;
	public static final String ACTION_NET_BROKEN = "com.richplay.action_net_broken";
	public static final String ACTION_NET_CONNECTED = "com.richplay.action_net_connected";

	public class ShareKey {
		public static final String SETTING_VOLUMN_VALUE = "SETTING_VOLUMN_VALUE";
		public static final String REMARK_TOKEN = "REMARK_TOCKEN";
		public static final String UID = "UID";
		public static final String ACCOUNT = "ACCOUNT";
		public static final String PWD = "PWD";
		public static final String NAME = "NAME";
		public static final String NICKNAME = "NICKNAME";
		public static final String SEX = "SEX";
	}

	// 是否保存到sd卡
	public static boolean saveToSD = false;

	public static final String F_PATH = "/richplay";

	public static final String PIC_PATH = F_PATH + "/m_pic";

	public static final String CONFIG_NAME = "meww_config";

	public static final String NOTIFYCATION_CHECK = "notifycation_check";
	public static final String ACTION_BACKGROUND = "com.richplay.action_background";
	public static final int TIMEOUT = 20000;
}