package com.yingqida.richplay.baseapi.http;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.http.client.RequestParams;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.packet.HttpAction;

/**
 * 
 * Class Name: HttpSenderUtils.java Function:
 * 
 * Modifications:
 * 
 * @author ruhaly DateTime 2013-10-15 下午3:43:43
 * @version 1.0
 */
public class HttpSenderUtils implements HttpAction {
	public static final String DEFAULT_HOST = "http://hdev.remark2/api/v1";
	public static final String DEFAULT_TYPE = ".json";
	public static final byte METHOD_GET = 0;
	public static final byte METHOD_POST = 1;

	public static HttpHandler<String> sendMsgImpl(String action,
			RequestParams params, int method, HttpUtils http,
			final int requestId, final SuperLogic logic) {

		HttpMethod httpMethod = method == METHOD_GET ? HttpMethod.GET
				: HttpMethod.POST;

		HttpHandler<String> httpHandler = http.send(httpMethod, DEFAULT_HOST
				+ action + DEFAULT_TYPE, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				logic.handleHttpException(error, msg);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				logic.handleHttpResponse(arg0.result, requestId);
			}
		});
		return httpHandler;
	}
}
