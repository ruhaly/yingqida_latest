package com.yingqida.richplay.logic;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.client.RequestParams;
import com.yingqida.richplay.RichPlayApplication;
import com.yingqida.richplay.baseapi.common.GlobalVar;
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.baseapi.http.HttpSenderUtils;
import com.yingqida.richplay.baseapi.http.ResponseCode;
import com.yingqida.richplay.packet.HttpAction;
import com.yingqida.richplay.packet.JsonParse;
import com.yingqida.richplay.packet.RequestId;

/**
 * 
 * Class Name: LoginLogic.java Function:
 * 
 * Modifications:
 * 
 * @author ruhaly DateTime 2013-10-15 下午3:43:35
 * @version 1.0
 */
public class LoginLogic extends SuperLogic implements HttpAction {

	private static LoginLogic ins;
	HttpHandler<String> httpHanlder;
	public User user;

	public synchronized static LoginLogic getInstance() {
		if (null == ins) {
			ins = new LoginLogic();
		}
		return ins;
	}

	public void sendLoginRequest(String account, String pwd,
			String remarkToken, String captcha) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("email", account);
		params.addBodyParameter("password", pwd);
		params.addBodyParameter("remark_token", remarkToken);
		if (null != captcha) {
			params.addBodyParameter("captcha", captcha);
		}
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_LOGIN, params,
				HttpSenderUtils.METHOD_POST, httpUtils,
				RequestId.LOGIN_REQUESTID, this, false);
	}

	public void sendRegisterRequest(String remark_token, String account,
			String pwd, String nickName, String etCaptcha) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("remark_token", remark_token);
		params.addBodyParameter("email", account);
		params.addBodyParameter("password", pwd);
		params.addBodyParameter("username", nickName);
		params.addBodyParameter("captcha", etCaptcha);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_REGISTER, params,
				HttpSenderUtils.METHOD_POST, httpUtils,
				RequestId.REGISTER_REQUESTID, this, false);
	}

	public void sendModPWDRequest(String remarkToken, String oldPwd,
			String newPwd) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("remark_token", remarkToken);
		params.addBodyParameter("old_password", oldPwd);
		params.addBodyParameter("new_password", newPwd);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_MODIFY_PWD, params,
				HttpSenderUtils.METHOD_POST, httpUtils, RequestId.MODIFY_PWD,
				this, false);
	}

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {
	}

	@Override
	public void clear() {

	}

	public void stopRequest() {
		if (null != httpHanlder) {
			httpHanlder.stop();
		}
	}

	@Override
	public void handleHttpResponse(String response, int requestId,
			InputStream is) {

		switch (requestId) {
		case RequestId.LOGIN_REQUESTID: {
			httpLoginResponse(response);
			break;
		}
		case RequestId.REGISTER_REQUESTID: {
			httpRegisterResponse(response);
			break;
		}
		case RequestId.MODIFY_PWD: {
			httpModPwdResponse(response);
			break;
		}
		case RequestId.GET_REMARKTOKEN: {
			httpGetRemarkTokenResponse(response);
			break;
		}
		case RequestId.REGISTER_CAPTCHA_REQUESTID: {
			httpGetRegisterCaptchaResponse(is);
			break;
		}
		case RequestId.LOGIN_CAPTCHA_REQUESTID: {
			httpGetLoginCaptchaResponse(is);
			break;
		}
		default:
			break;
		}

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {
		handler.sendEmptyMessage(CONNECT_ERROR_MSGWHAT);
	}

	/**
	 * 
	 * Function:解析返回响应json格式
	 * 
	 * @author ruhaly DateTime 2013-10-11 下午5:56:34
	 * @param response
	 */
	public void httpLoginResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				user = JsonParse.parseLoginRes(response);
				handler.sendEmptyMessage(LOGIN_SUCCESS_MSGWHAT);
			} else if (code.equals(ResponseCode.ERROR_LOGIN_ERROR_CAPTACHA)
					|| code.equals(ResponseCode.ERROR_LOGIN_ERROR_CAPTACHA2)) {
				handler.sendEmptyMessage(LOGIN_ERROR_NEED_CAPTCHA_MSGWHAT);
			} else {
				handler.sendEmptyMessage(LOGIN_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}
	}

	/**
	 * 
	 * Function:解析返回响应json格式
	 * 
	 * @author ruhaly DateTime 2013-10-14 下午3:31:43
	 * @param response
	 */
	public void httpRegisterResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				handler.sendEmptyMessage(REGISTER_SUCCESS_MSGWHAT);
			} else if (code.equals(ResponseCode.CAPTCHA_REGISTER)) {
				handler.sendEmptyMessage(REGISTER_ERROR_NEED_CAPTCHA_MSGWHAT);
			} else {
				handler.obtainMessage(REGISTER_ERROR_MSGWHAT, code)
						.sendToTarget();
			}

		} catch (JSONException e) {
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			e.printStackTrace();
		}
	}

	public void exitHttpSend() {
		RequestParams params = new RequestParams();
		String remark_token = GlobalVar.ins.getUser(RichPlayApplication
				.getIns().getAppShare()).remarkToken;
		params.addBodyParameter("remark_token", remark_token);
		HttpSenderUtils.sendMsgImpl(ACTION_EXIT, params,
				HttpSenderUtils.METHOD_GET, httpUtils,
				RequestId.EXIT_REQUESTID, this, false);
	}

	public void httpModPwdResponse(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				handler.sendEmptyMessage(MODIFY_PWD_SUCCESS_MSGWHAT);
			} else {
				handler.sendEmptyMessage(MODIFY_PWD_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			e.printStackTrace();
		}

	}

	public void sendGetRemarkTokenRequest() {
		RequestParams params = new RequestParams();
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_GET_REMARKTOKEN,
				params, HttpSenderUtils.METHOD_GET, httpUtils,
				RequestId.GET_REMARKTOKEN, this, false);
	}

	public void httpGetRemarkTokenResponse(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				String remarkToken = JsonParse.parseRemarkTokenRes(response);
				// handler.sendEmptyMessage(GET_REMARKTOKEN_SUCCESS_MSGWHAT);
				handler.obtainMessage(GET_REMARKTOKEN_SUCCESS_MSGWHAT,
						remarkToken).sendToTarget();
			} else {
				handler.sendEmptyMessage(GET_REMARKTOKEN_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			handler.sendEmptyMessage(GET_REMARKTOKEN_ERROR_MSGWHAT);
			e.printStackTrace();
		}

	}

	public void sendGetRegisterCaptchaRequest(String remarkToken) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("remark_token", remarkToken);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_REGISTER_CAPTCHA,
				params, HttpSenderUtils.METHOD_GET, httpUtils,
				RequestId.REGISTER_CAPTCHA_REQUESTID, this, true);
	}

	public void sendGetLoginCaptchaRequest(String remarkToken) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("remark_token", remarkToken);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_LOGIN_CAPTCHA, params,
				HttpSenderUtils.METHOD_GET, httpUtils,
				RequestId.LOGIN_CAPTCHA_REQUESTID, this, true);
	}

	public void httpGetRegisterCaptchaResponse(InputStream is) {

		try {

			Bitmap bitmap = BitmapFactory.decodeStream(is);
			handler.obtainMessage(REGISTER_ERROR_NEED_CAPTCHA_MSGWHAT, bitmap)
					.sendToTarget();
		} catch (Exception e) {
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			e.printStackTrace();
		}

	}

	public void httpGetLoginCaptchaResponse(InputStream is) {

		try {

			Bitmap bitmap = BitmapFactory.decodeStream(is);
			handler.obtainMessage(LOGIN_CAPTCHA_MSGWHAT, bitmap).sendToTarget();
		} catch (Exception e) {
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			e.printStackTrace();
		}

	}
}
