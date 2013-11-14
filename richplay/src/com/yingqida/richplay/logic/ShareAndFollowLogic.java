package com.yingqida.richplay.logic;

import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.yingqida.richplay.baseapi.http.HttpSenderUtils;
import com.yingqida.richplay.baseapi.http.ResponseCode;
import com.yingqida.richplay.packet.HttpAction;
import com.yingqida.richplay.packet.RequestId;

/**
 * 
 * Class Name: UserLogic.java Function:
 * 
 * Modifications:
 * 
 * @author ruhaly DateTime 2013-10-15 下午3:43:35
 * @version 1.0
 */
public class ShareAndFollowLogic extends SuperLogic implements HttpAction {

	private static ShareAndFollowLogic ins;
	HttpHandler<String> httpHanlder;

	public synchronized static ShareAndFollowLogic getInstance() {
		if (null == ins) {
			ins = new ShareAndFollowLogic();
		}
		return ins;
	}

	public void sendShareRequest(String remark_token, String remark_id) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("remark_token", remark_token);
		params.addBodyParameter("remark_id", remark_id);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_SHARE, params,
				HttpSenderUtils.METHOD_POST, httpUtils, RequestId.SHARE, this,
				false);
	}

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {
	}

	@Override
	public void clear() {
	}

	public void stop() {
		if (null != httpHanlder) {
			httpHanlder.stop();
		}
	}

	@Override
	public void handleHttpResponse(String response, int requestId,
			InputStream is) {

		switch (requestId) {
		case RequestId.SHARE: {
			httpShareResponse(response);
			break;
		}
		case RequestId.FOLLOW_USER: {
			httpFollowUser(response);
			break;
		}
		case RequestId.UNFOLLOW_USER: {
			httpUnFollowUser(response);
			break;
		}
		case RequestId.FOLLOW_YUANSU: {
			httpFollowYuansu(response);
			break;
		}
		case RequestId.UNFOLLOW_YUANSU: {
			httpUnFollowYuansu(response);
			break;
		}
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
	public void httpShareResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				handler.sendEmptyMessage(SHARE_SUCCESS_MSGWHAT);
			} else {
				handler.sendEmptyMessage(GET_SEARCH_USER_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}
	}

	/**
	 * 
	 * Function:关注用户
	 * 
	 * @author ruhaly DateTime 2013-10-23 下午3:06:13
	 * @param remark_token
	 * @param type
	 * @param uid
	 */
	public void sendFollowUserRequest(String remark_token, String uid) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("remark_token", remark_token);
		params.addBodyParameter("type", "1");
		params.addBodyParameter("uid", uid);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_FOLLOW_USER_OR_YUANSU,
				params, HttpSenderUtils.METHOD_POST, httpUtils,
				RequestId.FOLLOW_USER, this, false);
	}

	public void httpFollowUser(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				handler.sendEmptyMessage(FOLLOW_USER_SUCCESS_MSGWHAT);
			} else {
				handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}

	}

	/**
	 * 
	 * Function:关注用户
	 * 
	 * @author ruhaly DateTime 2013-10-23 下午3:06:13
	 * @param remark_token
	 * @param type
	 * @param uid
	 */
	public void sendUnFollowUserRequest(String remark_token, String uid) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("remark_token", remark_token);
		params.addBodyParameter("type", "1");
		params.addBodyParameter("uid", uid);
		httpHanlder = HttpSenderUtils.sendMsgImpl(
				ACTION_UNFOLLOW_USER_OR_YUANSU, params,
				HttpSenderUtils.METHOD_POST, httpUtils,
				RequestId.UNFOLLOW_USER, this, false);
	}

	public void httpUnFollowUser(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				handler.sendEmptyMessage(UNFOLLOW_USER_SUCCESS_MSGWHAT);
			} else {
				handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}

	}

	public void stopReqeust() {
		httpHanlder.stop();
	}

	public void sendFollowYuansuRequest(String remark_token, String remark_id) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("remark_token", remark_token);
		params.addBodyParameter("type", "2");
		params.addBodyParameter("remark_id", remark_id);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_FOLLOW_USER_OR_YUANSU,
				params, HttpSenderUtils.METHOD_POST, httpUtils,
				RequestId.FOLLOW_YUANSU, this, false);
	}

	public void httpFollowYuansu(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				handler.sendEmptyMessage(FOLLOW_YUANSU_SUCCESS_MSGWHAT);
			} else if (code.equals(ResponseCode.ERROR_FOLLOW_YUANSU_EXIST)) {
				handler.sendEmptyMessage(FOLLOW_YUANSU_ERROR_EXIST_MSGWHAT);
			} else if (code.equals(ResponseCode.ERROR_FOLLOW_YUANSU_EXIST)) {
				handler.sendEmptyMessage(FOLLOW_YUANSU_ERROR_EXIST_MSGWHAT);
			} else {
				handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}

	}

	public void sendUnFollowYuansuRequest(String remark_token, String remark_id) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("remark_token", remark_token);
		params.addBodyParameter("type", "2");
		params.addBodyParameter("remark_id", remark_id);
		httpHanlder = HttpSenderUtils.sendMsgImpl(
				ACTION_UNFOLLOW_USER_OR_YUANSU, params,
				HttpSenderUtils.METHOD_POST, httpUtils,
				RequestId.UNFOLLOW_YUANSU, this, false);
	}

	public void httpUnFollowYuansu(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				handler.sendEmptyMessage(UNFOLLOW_YUANSU_SUCCESS_MSGWHAT);
			} else if (code.equals(ResponseCode.ERROR_FOLLOW_YUANSU_DONT_EXIST)) {
				handler.sendEmptyMessage(ERROR_FOLLOW_YUANSU_DONT_EXIST);
			} else {
				handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}

	}
}
