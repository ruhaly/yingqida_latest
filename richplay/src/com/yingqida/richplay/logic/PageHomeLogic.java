package com.yingqida.richplay.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
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

	public int curPage = 1;
	public String perPage = "10";
	public int targetCurPage = 1;
	// 0刷新；1加载更多
	public int type = 0;

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
	public void handleHttpResponse(String response, int requestId, InputStream is) {

		switch (requestId) {
		case RequestId.HOME_TIMELINE: {
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
		curPage = 0;
	}

	public void realseYuanSuRequest() {
		httpHanlder.stop();
	}

	public void sendPageHomeYuanSuRequest(String uid, String remark_token,
			int type) {
		this.type = type;
		if (type == 0) {
			targetCurPage = 1;
		} else {
			targetCurPage = curPage + 1;
		}
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("remark_token", remark_token);
		params.addQueryStringParameter("cur_page", targetCurPage + "");
		params.addQueryStringParameter("per_page", perPage);
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_HOME_TIMELINE, params,
				HttpSenderUtils.METHOD_GET, httpUtils, RequestId.HOME_TIMELINE,
				this, false);
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
				List<Yuansu> tempList = JsonParse
						.parsePageHomeYuansuRes(response);
				if (tempList != null) {
					if (type == 0)
						list.clear();
					list.addAll(tempList);
				}
				if (null == list) {
					list = new ArrayList<Yuansu>();
				}
				if (!list.isEmpty()) {
					curPage = targetCurPage;
				}
				handler.sendEmptyMessage(HOME_PAGE_YUANSU_SUCCESS_MSGWHAT);
			} else {
				handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}

	}

}
