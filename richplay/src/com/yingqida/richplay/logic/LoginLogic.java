package com.yingqida.richplay.logic;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.client.RequestParams;
import com.yingqida.richplay.baseapi.http.HttpSenderUtils;
import com.yingqida.richplay.baseapi.http.ResponseCode;
import com.yingqida.richplay.packet.HttpAction;
import com.yingqida.richplay.packet.RequestId;

public class LoginLogic extends SuperLogic implements HttpAction {

	private static LoginLogic ins;
	HttpHandler<String> loginHanlder;

	public synchronized static LoginLogic getInstance() {
		if (null == ins) {
			ins = new LoginLogic();
		}
		return ins;
	}

	public void sendLoginRequest(String account, String pwd) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("email", account);
		params.addBodyParameter("password", pwd);
		loginHanlder = HttpSenderUtils.sendMsgImpl(ACTION_LOGIN, params,
				HttpSenderUtils.METHOD_POST, handler, httpRequest,
				RequestId.LOGIN_REQUESTID, this);
	}

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {
	}

	@Override
	public void clear() {

	}

	public void releaseLoginRequest() {
		if (null != loginHanlder) {
			loginHanlder.stop();
		}
	}

	@Override
	public void handleHttpResponse(String response, int requestId) {

		switch (requestId) {
		case RequestId.LOGIN_REQUESTID:
			httpResponse(response);
			break;

		default:
			break;
		}

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {
		System.out.println(error.getMessage() + "-----------"
				+ error.getExceptionCode() + "----------" + msg);
	}

	/**
	 * 
	 * Function:解析返回响应json格式
	 * 
	 * @author ruhaly DateTime 2013-10-11 下午5:56:34
	 * @param response
	 */
	public void httpResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				handler.sendEmptyMessage(LOGIN_SUCCESS_MSGWHAT);
			} else {
				handler.sendEmptyMessage(LOGIN_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
