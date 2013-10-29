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
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.packet.HttpAction;
import com.yingqida.richplay.packet.JsonParse;
import com.yingqida.richplay.packet.RequestId;

public class PCenterLogic extends SuperLogic implements HttpAction {

	private static PCenterLogic ins;

	public List<Yuansu> fyList = new ArrayList<Yuansu>();
	public List<Yuansu> fyList2 = new ArrayList<Yuansu>();

	public List<User> beiguanzhuYhList = new ArrayList<User>();
	public List<Yuansu> guanzhuYsList = new ArrayList<Yuansu>();
	public List<User> guanzhuYhList = new ArrayList<User>();

	private HttpHandler<String> httpHanlder;
	public String perPage = "50";

	// 发言
	public int curPageFy2 = 1;
	public int targetCurPageFy2 = 1;

	// 发言
	public int curPageFy1 = 1;
	public int targetCurPageFy1 = 1;

	// 被关注
	public int curPageBgz = 1;
	public int targetCurPageBgz = 1;

	// 关注的元素
	public int curPageYs = 1;
	public int targetCurPageYs = 1;

	// 关注的用户
	public int curPageYh = 1;
	public int targetCurPageYh = 1;

	// 0刷新；1加载更多
	public int type = 0;

	public synchronized static PCenterLogic getInstance() {
		if (null == ins) {
			ins = new PCenterLogic();
		}
		return ins;
	}

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {

	}

	@Override
	public void handleHttpResponse(String response, int requestId) {

		switch (requestId) {
		case RequestId.USER_TIMELINE: {
			httpFaYanResponse1(response);
			break;
		}
		case RequestId.USER_TIMELINE2: {
			httpFaYanResponse2(response);
			break;
		}
		case RequestId.PCENTER_FOLLOWER: {
			httpBeiGuanzhuResponse(response);
			break;
		}
		case RequestId.PCENTER_GUANZHU_YUANSU: {
			httpGuanzhuYsResponse(response);
			break;
		}
		case RequestId.PCENTER_GUANZHU_YONGHU: {
			httpGuanzhuYhResponse(response);
			break;
		}
		default:
			break;
		}

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void clear() {
	}

	public void realseYuanSuRequest() {
		httpHanlder.stop();
	}

	public void sendFayanRequest1(String remark_token, int type) {
		this.type = type;
		if (type == 0) {
			targetCurPageFy1 = 1;
		} else {
			targetCurPageFy1 = curPageFy1 + 1;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("remark_token", remark_token);
		params.addQueryStringParameter("cur_page", targetCurPageFy1 + "");
		params.addQueryStringParameter("per_page", perPage);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_USER_TIMELINE, params,
				HttpSenderUtils.METHOD_GET, httpUtils, RequestId.USER_TIMELINE,
				this);
	}

	/**
	 * Function:解析首页元素json数据
	 * 
	 * @author ruhaly DateTime 2013-10-18 上午11:59:57
	 * @param response
	 */
	public void httpFaYanResponse1(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				List<Yuansu> tempList = JsonParse.parseFayanRes(response);
				if (tempList != null) {
					if (type == 0)
						fyList.clear();
					fyList.addAll(tempList);
				}
				if (null == fyList) {
					fyList = new ArrayList<Yuansu>();
				}
				if (!tempList.isEmpty()) {
					curPageFy1 = targetCurPageFy1;
				}
				handler.sendEmptyMessage(PCENTER_FAYAN_SUCCESS_MSGWHAT);
			} else {
				handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}

	}

	public void sendFayanRequest2(String remark_token, int type) {
		this.type = type;
		if (type == 0) {
			targetCurPageFy2 = 1;
		} else {
			targetCurPageFy2 = curPageFy2 + 1;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("remark_token", remark_token);
		params.addQueryStringParameter("cur_page", targetCurPageFy2 + "");
		params.addQueryStringParameter("per_page", perPage);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_USER_TIMELINE, params,
				HttpSenderUtils.METHOD_GET, httpUtils,
				RequestId.USER_TIMELINE2, this);
	}

	/**
	 * Function:解析首页元素json数据
	 * 
	 * @author ruhaly DateTime 2013-10-18 上午11:59:57
	 * @param response
	 */
	public void httpFaYanResponse2(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				List<Yuansu> tempList = JsonParse.parseFayanRes(response);
				if (tempList != null) {
					if (type == 0)
						fyList2.clear();
					fyList2.addAll(tempList);
				}
				if (null == fyList2) {
					fyList2 = new ArrayList<Yuansu>();
				}
				if (!tempList.isEmpty()) {
					curPageFy2 = targetCurPageFy2;
				}
				handler.sendEmptyMessage(PCENTER_FAYAN_SUCCESS_MSGWHAT);
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
	 * Function:获取用户的关注元素列表
	 * 
	 * @author ruhaly DateTime 2013-10-28 上午11:17:36
	 * @param remark_token
	 * @param type
	 */
	public void sendGuanzhuYsRequest(String remark_token, int type) {
		this.type = type;
		if (type == 0) {
			targetCurPageYs = 1;
		} else {
			targetCurPageYs = curPageYs + 1;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("remark_token", remark_token);
		params.addQueryStringParameter("cur_page", targetCurPageYs + "");
		params.addQueryStringParameter("per_page", perPage);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_GUANZHU_YUANSU,
				params, HttpSenderUtils.METHOD_GET, httpUtils,
				RequestId.PCENTER_GUANZHU_YUANSU, this);
	}

	/**
	 * Function:解析首页元素json数据
	 * 
	 * @author ruhaly DateTime 2013-10-18 上午11:59:57
	 * @param response
	 */
	public void httpGuanzhuYsResponse(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				List<Yuansu> tempList = JsonParse
						.parseGuanzhuYuansuRes(response);
				if (tempList != null) {
					if (type == 0)
						guanzhuYsList.clear();
					guanzhuYsList.addAll(tempList);
				}
				if (null == guanzhuYsList) {
					guanzhuYsList = new ArrayList<Yuansu>();
				}
				if (!tempList.isEmpty()) {
					curPageYs = targetCurPageYs;
				}
				handler.sendEmptyMessage(PCENTER_GUANZHUYS_SUCCESS_MSGWHAT);
			} else {
				handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}

	}

	public void sendGuanzhuYhRequest(String remark_token, int type) {
		this.type = type;
		if (type == 0) {
			targetCurPageYh = 1;
		} else {
			targetCurPageYh = curPageYh + 1;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("remark_token", remark_token);
		params.addQueryStringParameter("cur_page", targetCurPageYh + "");
		params.addQueryStringParameter("per_page", perPage);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_GUANZHU_YONGHU,
				params, HttpSenderUtils.METHOD_GET, httpUtils,
				RequestId.PCENTER_GUANZHU_YONGHU, this);
	}

	/**
	 * Function:解析首页元素json数据
	 * 
	 * @author ruhaly DateTime 2013-10-18 上午11:59:57
	 * @param response
	 */
	public void httpGuanzhuYhResponse(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				List<User> tempList = JsonParse.parseGetUserRes(response);
				if (tempList != null) {
					if (type == 0)
						guanzhuYhList.clear();
					guanzhuYhList.addAll(tempList);
				}
				if (null == guanzhuYhList) {
					guanzhuYhList = new ArrayList<User>();
				}
				if (!tempList.isEmpty()) {
					curPageYh = targetCurPageYh;
				}
				handler.sendEmptyMessage(PCENTER_GUANZHUYH_SUCCESS_MSGWHAT);
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
	 * Function:获取用户的追随列表
	 * 
	 * @author ruhaly DateTime 2013-10-28 上午11:17:09
	 * @param remark_token
	 * @param type
	 */
	public void sendBeiGuanzhuRequest(String remark_token, String uid, int type) {
		this.type = type;
		if (type == 0) {
			targetCurPageBgz = 1;
		} else {
			targetCurPageBgz = curPageBgz + 1;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("remark_token", remark_token);
		params.addQueryStringParameter("uid", uid);
		params.addQueryStringParameter("cur_page", targetCurPageBgz + "");
		params.addQueryStringParameter("per_page", perPage);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_GET_FOLLOWER, params,
				HttpSenderUtils.METHOD_GET, httpUtils,
				RequestId.PCENTER_FOLLOWER, this);
	}

	/**
	 * Function:解析首页元素json数据
	 * 
	 * @author ruhaly DateTime 2013-10-18 上午11:59:57
	 * @param response
	 */
	public void httpBeiGuanzhuResponse(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				List<User> tempList = JsonParse.parseFollowerRes(response);
				if (tempList != null) {
					if (type == 0)
						beiguanzhuYhList.clear();
					beiguanzhuYhList.addAll(tempList);
				}
				if (null == beiguanzhuYhList) {
					beiguanzhuYhList = new ArrayList<User>();
				}
				if (!tempList.isEmpty()) {
					curPageBgz = targetCurPageBgz;
				}
				handler.sendEmptyMessage(PCENTER_BEIGUANZHU_SUCCESS_MSGWHAT);
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
