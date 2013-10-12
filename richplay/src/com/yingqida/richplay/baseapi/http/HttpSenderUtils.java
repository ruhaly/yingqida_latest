package com.yingqida.richplay.baseapi.http;

import android.os.Handler;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.http.client.RequestParams;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.packet.HttpAction;

public class HttpSenderUtils implements HttpAction {
	public static final String DEFAULT_HOST = "http://dev.remark2/api/v1";
	public static final byte METHOD_GET = 0;
	public static final byte METHOD_POST = 1;

	public static HttpHandler<String> sendMsgImpl(String action,
			RequestParams params, int method, final Handler mHandler,
			HttpUtils http, final int requestId, final SuperLogic logic) {

		HttpMethod httpMethod = method == METHOD_GET ? HttpMethod.GET
				: HttpMethod.POST;

		HttpHandler<String> httpHandler = http.send(httpMethod, DEFAULT_HOST
				+ action, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {
			}

			@Override
			public void onLoading(long total, long current) {
			}

			@Override
			public void onSuccess(String result) {
				logic.handleHttpResponse(result, requestId);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				logic.handleHttpException(error, msg);
			}
		});
		return httpHandler;
	}
}
