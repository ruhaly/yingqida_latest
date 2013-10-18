package com.yingqida.richplay.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.client.RequestParams;
import com.yingqida.richplay.baseapi.http.HttpSenderUtils;
import com.yingqida.richplay.baseapi.http.ResponseCode;
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.packet.HttpAction;
import com.yingqida.richplay.packet.JsonParse;
import com.yingqida.richplay.packet.RequestId;

public class PageHomeLogic extends SuperLogic implements HttpAction {

	private static PageHomeLogic ins;

	public List<Yuansu> list = new ArrayList<Yuansu>();

	private HttpHandler<String> httpHanlder;

	public String curPage = "1";
	public String perPage = "20";

	public synchronized static PageHomeLogic getInstance() {
		if (null == ins) {
			ins = new PageHomeLogic();
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
			httpPageHomeYuanSuResponse(response);
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
		curPage = "1";
	}

	public void realseYuanSuRequest() {
		httpHanlder.stop();
	}

	public void sendPageHomeYuanSuRequest(String remark_token, String uid) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("remark_token", remark_token);
		params.addBodyParameter("uid", uid);
		params.addBodyParameter("cur_page", curPage);
		params.addBodyParameter("per_page", perPage);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_USER_TIMELINE, params,
				HttpSenderUtils.METHOD_GET, httpRequest,
				RequestId.USER_TIMELINE, this);
	}

	/**
	 * Function:解析首页元素json数据
	 * 
	 * @author ruhaly DateTime 2013-10-18 上午11:59:57
	 * @param response
	 */
	public void httpPageHomeYuanSuResponse(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				list = JsonParse.parsePageHomeYuansuRes(response);
				handler.sendEmptyMessage(HOME_PAGE_YUANSU_SUCCESS_MSGWHAT);
			} else {
				handler.sendEmptyMessage(LOGIN_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}

	}
}
