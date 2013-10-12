package com.yingqida.richplay.baseapi.http;

import com.lidroid.xutils.exception.HttpException;

public abstract interface HttpResponseHanlder {
	public abstract void handleHttpResponse(String response, int rspCode,
			int requestId);

	public abstract void handleHttpResponse(String response, int requestId);

	public abstract void handleHttpException(HttpException error, String msg);
}