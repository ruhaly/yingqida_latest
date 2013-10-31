package com.yingqida.richplay.logic;

import com.lidroid.xutils.HttpUtils;
import com.yingqida.richplay.baseapi.http.HttpResponseHanlder;

import android.os.Handler;

/**
 * 
 * Class Name: SuperLogic.java Function:
 * 
 * Modifications:
 * 
 * @author ruhaly DateTime 2013-10-15 下午3:43:19
 * @version 1.0
 */
public abstract class SuperLogic implements HttpResponseHanlder {

	public static final int DATA_FORMAT_ERROR_MSGWHAT = 000;
	public static final int CONNECT_ERROR_MSGWHAT = 001;
	public static final int LOGIN_SUCCESS_MSGWHAT = 100;
	public static final int LOGIN_ERROR_MSGWHAT = 101;
	public static final int LOGIN_ERROR_NEED_CAPTCHA_MSGWHAT = 1010;
	public static final int LOGIN_CAPTCHA_MSGWHAT = 10101;
	public static final int REGISTER_SUCCESS_MSGWHAT = 200;
	public static final int REGISTER_ERROR_MSGWHAT = 201;
	public static final int REGISTER_ERROR_NEED_CAPTCHA_MSGWHAT = 2010;
	public static final int HOME_PAGE_YUANSU_SUCCESS_MSGWHAT = 300;
	public static final int HOME_PAGE_YUANSU_ERROR_MSGWHAT = 301;
	public static final int MODIFY_PWD_SUCCESS_MSGWHAT = 400;
	public static final int MODIFY_PWD_ERROR_MSGWHAT = 401;
	public static final int GET_SEARCH_USER_SUCCESS_MSGWHAT = 500;
	public static final int GET_SEARCH_USER_ERROR_MSGWHAT = 501;
	public static final int GET_SEARCH_YUANSU_SUCCESS_MSGWHAT = 600;
	public static final int GET_SEARCH_YUANSU_ERROR_MSGWHAT = 601;
	public static final int FOLLOW_USER_SUCCESS_MSGWHAT = 700;
	public static final int FOLLOW_USER_ERROR_MSGWHAT = 701;
	public static final int UNFOLLOW_USER_SUCCESS_MSGWHAT = 800;
	public static final int UNFOLLOW_USER_ERROR_MSGWHAT = 801;

	public static final int FOLLOW_YUANSU_SUCCESS_MSGWHAT = 7000;
	public static final int FOLLOW_YUANSU_ERROR_MSGWHAT = 7010;
	public static final int UNFOLLOW_YUANSU_SUCCESS_MSGWHAT = 8000;
	public static final int UNFOLLOW_YUANSU_ERROR_MSGWHAT = 8010;

	public static final int YUANSU_COMMENT_SUCCESS_MSGWHAT = 900;
	public static final int YUANSU_COMMENT_ERROR_MSGWHAT = 901;
	public static final int SHARE_SUCCESS_MSGWHAT = 1000;
	public static final int SHARE_ERROR_MSGWHAT = 1001;

	public static final int COMMENT_YUANSU_SUCCESS_MSGWHAT = 1100;
	public static final int COMMENT_YUANSU_ERROR_MSGWHAT = 1201;
	public static final int GET_REMARKTOKEN_SUCCESS_MSGWHAT = 1400;
	public static final int GET_REMARKTOKEN_ERROR_MSGWHAT = 1401;
	public static final int PCENTER_FAYAN_SUCCESS_MSGWHAT = 1300;
	public static final int PCENTER_FAYAN_ERROR_MSGWHAT = 1301;
	public static final int PCENTER_BEIGUANZHU_SUCCESS_MSGWHAT = 1500;
	public static final int PCENTER_BEIGUANZHU_ERROR_MSGWHAT = 1501;

	public static final int PCENTER_GUANZHUYS_SUCCESS_MSGWHAT = 1600;
	public static final int PCENTER_GUANZHUYS_ERROR_MSGWHAT = 1601;
	public static final int PCENTER_GUANZHUYH_SUCCESS_MSGWHAT = 1700;
	public static final int PCENTER_GUANZHUYH_ERROR_MSGWHAT = 1701;
	public static final int PCENTER_COUNT_SUCCESS_MSGWHAT = 1801;
	public static final int UPLOAD_HEAD_SUCCESS_MSGWHAT = 1901;
	public static final int USER_INFO_SUCCESS_MSGWHAT = 2000;
	public static final int UPDATE_USER_INFO_SUCCESS_MSGWHAT = 2001;

	public Handler handler;

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public HttpUtils httpUtils;

	public void setHttpUtils(HttpUtils httpUtils) {
		this.httpUtils = httpUtils;
	}

	public void setDate(Handler handler, HttpUtils httpUtils) {
		this.handler = handler;
		this.httpUtils = httpUtils;
	}

	public abstract void clear();
}
