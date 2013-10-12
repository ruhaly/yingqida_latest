package com.yingqida.richplay.baseapi.http;

import java.util.Timer;

import org.apache.http.entity.mime.MultipartEntity;

import com.yingqida.richplay.baseapi.common.RichResource;

public class HttpRequest {
	private String body;
	private HttpResponseHanlder mResponseHandler;
	private HttpTimeoutHandler mTimeoutHandler;
	private int mTimeoutTime;
	private Timer mTimer;
	private int requestId;
	private boolean responsed;
	private MultipartEntity entity;

	public HttpRequest(HttpResponseHanlder paramHttpResponseHanlder,
			HttpTimeoutHandler paramHttpTimeoutHandler, int paramInt) {
		Timer localTimer = new Timer();
		this.mTimer = localTimer;
		this.mTimeoutTime = RichResource.TIMEOUT;
		this.responsed = false;
		this.mResponseHandler = paramHttpResponseHanlder;
		this.mTimeoutHandler = paramHttpTimeoutHandler;
		this.requestId = paramInt;
	}

	public boolean cancel() {
		synchronized (this) {
			if (!responsed) {
				mResponseHandler = null;
				if (null != mTimer) {
					mTimer.cancel();
				}
				mTimer = null;
				return true;
			}
			return false;
		}
	}

	public String getBody() {
		return this.body;
	}

	public int getRequestId() {
		return this.requestId;
	}

	public HttpResponseHanlder getmResponseHandler() {
		return this.mResponseHandler;
	}

	public HttpTimeoutHandler getmTimeoutHandler() {
		return this.mTimeoutHandler;
	}

	public int getmTimeoutTime() {
		return this.mTimeoutTime;
	}

	public Timer getmTimer() {
		return this.mTimer;
	}

	public boolean isResponsed() {
		return this.responsed;
	}

	public void setBody(String paramString) {
		this.body = paramString;
	}

	public void setRequestId(int paramInt) {
		this.requestId = paramInt;
	}

	public void setResponsed(boolean paramBoolean) {
		this.responsed = paramBoolean;
	}

	public void setmResponseHandler(HttpResponseHanlder paramHttpResponseHanlder) {
		this.mResponseHandler = paramHttpResponseHanlder;
	}

	public void setmTimeoutHandler(HttpTimeoutHandler paramHttpTimeoutHandler) {
		this.mTimeoutHandler = paramHttpTimeoutHandler;
	}

	public void setmTimeoutTime(int paramInt) {
		this.mTimeoutTime = paramInt;
	}

	public MultipartEntity getEntity() {
		return entity;
	}

	public void setEntity(MultipartEntity entity) {
		this.entity = entity;
	}

}
