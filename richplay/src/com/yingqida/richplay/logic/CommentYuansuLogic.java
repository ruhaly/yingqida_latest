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
public class CommentYuansuLogic extends SuperLogic implements HttpAction {

	private static CommentYuansuLogic ins;
	HttpHandler<String> httpHanlder;

	public synchronized static CommentYuansuLogic getInstance() {
		if (null == ins) {
			ins = new CommentYuansuLogic();
		}
		return ins;
	}

	public void sendCommentYuanRequest(String remark_token, String remark_id,
			String comment_content) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("remark_token", remark_token);
		params.addBodyParameter("remark_id", remark_id);
		params.addBodyParameter("comment_content", comment_content);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_COMMENT_YUANSU,
				params, HttpSenderUtils.METHOD_POST, httpUtils,
				RequestId.COMMENT_YUANSU, this, false);
	}

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {
	}

	@Override
	public void clear() {
	}

	public void releaseRequest() {
		if (null != httpHanlder) {
			httpHanlder.stop();
		}
	}

	@Override
	public void handleHttpResponse(String response, int requestId, InputStream is) {

		switch (requestId) {
		case RequestId.COMMENT_YUANSU: {
			httpCommentYuansuResponse(response);
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
	public void httpCommentYuansuResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				handler.sendEmptyMessage(COMMENT_YUANSU_SUCCESS_MSGWHAT);
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
	public void sendFollowUserRequest(String remark_token, String uid) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("remark_token", remark_token);
		params.addBodyParameter("type", "1");
		params.addBodyParameter("uid", uid);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_FOLLOW_USER_OR_YUANSU, params,
				HttpSenderUtils.METHOD_POST, httpUtils, RequestId.FOLLOW_USER,
				this, false);
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
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_UNFOLLOW_USER_OR_YUANSU, params,
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

}
