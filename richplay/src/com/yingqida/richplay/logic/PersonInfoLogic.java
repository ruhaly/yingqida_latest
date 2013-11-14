package com.yingqida.richplay.logic;

import java.io.File;
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

public class PersonInfoLogic extends SuperLogic implements HttpAction {

	private static PersonInfoLogic ins;

	private HttpHandler<String> httpHanlder;

	public synchronized static PersonInfoLogic getInstance() {
		if (null == ins) {
			ins = new PersonInfoLogic();
		}
		return ins;
	}

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {

	}

	@Override
	public void handleHttpResponse(String response, int requestId, InputStream is) {

		switch (requestId) {
		case RequestId.UPLOAD_HEAD: {
			httpUploadHeadResponse(response);
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

	public void stop() {
		if (null != httpHanlder)
			httpHanlder.stop();
	}

	public void sendUploadHeadRequest(String remark_token, String avatar) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("remark_token", remark_token);
		params.addBodyParameter("avatar", new File(avatar));
		httpHanlder = HttpSenderUtils.sendMsgImpl(ACTION_UPLOAD_HEAD, params,
				HttpSenderUtils.METHOD_POST, httpUtils, RequestId.UPLOAD_HEAD,
				this, false);
	}

	/**
	 * Function:解析首页元素json数据
	 * 
	 * @author ruhaly DateTime 2013-10-18 上午11:59:57
	 * @param response
	 */
	public void httpUploadHeadResponse(String response) {

		try {
			JSONObject json = new JSONObject(response);
			String code = String.valueOf(json.get("code"));
			if (code.equals(ResponseCode.SUCCESS)) {
				handler.sendEmptyMessage(UPLOAD_HEAD_SUCCESS_MSGWHAT);
			} else {
				handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(DATA_FORMAT_ERROR_MSGWHAT);
		}

	}
}
