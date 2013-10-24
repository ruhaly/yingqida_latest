package com.yingqida.richplay.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.client.RequestParams;
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.baseapi.http.HttpSenderUtils;
import com.yingqida.richplay.baseapi.http.ResponseCode;
import com.yingqida.richplay.packet.HttpAction;
import com.yingqida.richplay.packet.JsonParse;
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
public class UserSearchLogic extends SuperLogic implements HttpAction {

	private static UserSearchLogic ins;
	HttpHandler<String> httpHanlder;
	public List<User> list = new ArrayList<User>();
	// 0刷新；1加载更多
	public int type = 0;
	public int curPage = 1;
	public int targetCurPage = 1;
	public String perPage = "20";

	public synchronized static UserSearchLogic getInstance() {
		if (null == ins) {
			ins = new UserSearchLogic();
		}
		return ins;
	}

	public void sendGetUserRequest(String remark_token, String keyword, int type) {
		this.type = type;
		if (type == 0) {
			targetCurPage = 1;
		} else {
			targetCurPage = curPage + 1;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("remark_token", remark_token);
		params.addQueryStringParameter("keyword", keyword);
		params.addQueryStringParameter("cur_page", "" + targetCurPage);
		params.addQueryStringParameter("per_page", perPage);
		httpHanlder = HttpSenderUtils
				.sendMsgImpl(ACTION_SEARCH_USER, params,
						HttpSenderUtils.METHOD_GET, httpUtils,
						RequestId.GET_USER, this);
	}

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {
	}

	@Override
	public void clear() {
		curPage = 0;
	}

	public void releaseRequest() {
		if (null != httpHanlder) {
			httpHanlder.stop();
		}
	}

	@Override
	public void handleHttpResponse(String response, int requestId) {

		switch (requestId) {
		case RequestId.GET_USER: {
			httpGetUserResponse(response);
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
	public void httpGetUserResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				List<User> users = JsonParse.parseGetUserRes(response);
				if (null != users) {
					if (0 == type)
						list.clear();
					list.addAll(users);
					if (!list.isEmpty()) {
						curPage = targetCurPage;
					}
				}
				handler.sendEmptyMessage(GET_SEARCH_USER_SUCCESS_MSGWHAT);
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
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_FOLLOW_USER, params,
				HttpSenderUtils.METHOD_POST, httpUtils, RequestId.FOLLOW_USER,
				this);
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
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_UNFOLLOW_USER, params,
				HttpSenderUtils.METHOD_POST, httpUtils,
				RequestId.UNFOLLOW_USER, this);
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
}
