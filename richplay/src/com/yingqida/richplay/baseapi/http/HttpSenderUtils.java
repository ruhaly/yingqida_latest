package com.yingqida.richplay.baseapi.http;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
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

	public static final String DEFALUT_HEAD_AREA = "hstatic.remark2";// "static.richplay.com.tw";//
																	// "static.remark2";//
	public static final String DEFALUT_AREA = "hdev.remark2";// "www.richplay.com.tw";//
																// "dev.remark2";//
	public static final String DEFAULT_HOST = "http://" + DEFALUT_AREA
			+ "/api/v1";
	public static final String DEFAULT_TYPE = ".json";
	public static final byte METHOD_GET = 0;
	public static final byte METHOD_POST = 1;

	public static HttpHandler<String> sendMsgImpl(String action,
			RequestParams params, int method, HttpUtils http,
			final int requestId, final SuperLogic logic, boolean isStream) {
		HttpHandler<String> httpHandler = null;
		HttpMethod httpMethod = method == METHOD_GET ? HttpMethod.GET
				: HttpMethod.POST;

		if (isStream) {

			ResponseStream sendSync;
			try {
				sendSync = http.sendSync(httpMethod, DEFAULT_HOST + action
						+ DEFAULT_TYPE, params);
				logic.handleHttpResponse("", requestId, sendSync);
			} catch (HttpException e) {
				logic.handleHttpException(e, e.getMessage());
			}
		} else {
			httpHandler = http.send(httpMethod, DEFAULT_HOST + action
					+ DEFAULT_TYPE, params, new RequestCallBack<String>() {

				@Override
				public void onStart() {
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					logic.handleHttpException(error, msg);
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					logic.handleHttpResponse(arg0.result, requestId, null);
				}
			});
		}

		return httpHandler;
	}
}
