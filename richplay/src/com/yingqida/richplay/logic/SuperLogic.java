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
	public static final int LOGIN_SUCCESS_MSGWHAT = 100;
	public static final int LOGIN_ERROR_MSGWHAT = 101;
	public static final int REGISTER_SUCCESS_MSGWHAT = 200;
	public static final int REGISTER_ERROR_MSGWHAT = 201;
	public static final int HOME_PAGE_YUANSU_SUCCESS_MSGWHAT = 300;
	public static final int HOME_PAGE_YUANSU_ERROR_MSGWHAT = 301;

	public Handler handler;

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public HttpUtils httpRequest;

	public void setHttpRequest(HttpUtils httpRequest) {
		this.httpRequest = httpRequest;
	}

	public void setDate(Handler handler, HttpUtils httpRequest) {
		this.handler = handler;
		this.httpRequest = httpRequest;
	}

	public abstract void clear();
}
