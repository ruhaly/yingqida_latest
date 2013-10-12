package com.yingqida.richplay.baseapi.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.packet.HttpAction;

public class HttpChannel implements HttpAction {

	private static final String DEFAULT_HOST = "http://192.168.0.140/app/app_remark/public/";

	public static final byte METHOD_GET = 0;
	public static final byte METHOD_POST = 1;
	private static final String MY_TAG = "HttpChannel";

	private void sendMsgImpl(String action, final HttpRequest request,
			byte method, boolean isfile) {
		try {
			String baseUri = getUri();
			if (isfile) {
				URI uri = new URI(baseUri + action);
				HttpPost post = new HttpPost(uri);
				post.setEntity(request.getEntity());
				BasicHttpParams params = new BasicHttpParams();
				// 设置请求超时
				HttpConnectionParams.setConnectionTimeout(params,
						request.getmTimeoutTime());
				// 设置等待数据超时时间
				HttpConnectionParams.setSoTimeout(params,
						request.getmTimeoutTime());
				HttpClient client = new DefaultHttpClient(params);
				HttpResponse response = client.execute(post);
				// show(post);
				// 开启计时器
				synchronized (request) {
					if (null != request.getmTimer()) {
						request.getmTimer().schedule(new TimerTask() {
							@Override
							public void run() {
								HttpTimeoutHandler timeoutHandler = request.getmTimeoutHandler();
								request.setmResponseHandler(null);
								if (null == timeoutHandler) {
									return;
								}
								timeoutHandler.handleHttpTimeout(request
										.getRequestId());
							}
						}, request.getmTimeoutTime());
					}
				}

				AppLog.out(MY_TAG, request.getBody(), AppLog.LEVEL_INFO);
				AppLog.logToFile(MY_TAG, request.getBody());

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// 从response中取出HttpEntity对象
					HttpEntity et = response.getEntity();
					// AppLog.out(MY_TAG, EntityUtils.toString(et),
					// AppLog.LEVEL_INFO);
					// 取得服务器返回的流
					InputStream is = et.getContent();
					StringBuffer sb = new StringBuffer();
					String readLine = null;
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, RichResource.ENCODE_UTF));
					while (null != (readLine = reader.readLine())) {
						sb.append(readLine);
						sb.append("\n");
					}
					reader.close();
					synchronized (request) {
						if (null != request.getmTimer()) {
							request.getmTimer().cancel();
						}
						request.setResponsed(true);
						if (null != request.getmResponseHandler()) {
							request.getmResponseHandler().handleHttpResponse(
									sb.toString(),
									Byte.valueOf(response.getFirstHeader(
											"returnCode").getValue()),
									request.getRequestId());
							AppLog.out(
									MY_TAG,
									"returnCode: "
											+ Byte.valueOf(response
													.getFirstHeader(
															"returnCode")
													.getValue()),
									AppLog.LEVEL_INFO);
						}
					}
					AppLog.out(MY_TAG, "HttpResponse: " + sb.toString(),
							AppLog.LEVEL_INFO);
					sb = null;
				} else {
					if (null != request.getmTimer()) {
						request.getmTimer().cancel();
					}
					if (null != request.getmResponseHandler()) {
						request.getmResponseHandler().handleHttpResponse(null,
								RichResource.ERROR_FWQ, 0);
					}
				}
			} else {
				URL url = new URL(baseUri + action);
				AppLog.out(MY_TAG, url.toString(), AppLog.LEVEL_INFO);
				HttpURLConnection httpConnection = (HttpURLConnection) url
						.openConnection();
				httpConnection.setDoOutput(true);
				httpConnection.setDoInput(true);
				httpConnection.setUseCaches(false);
				httpConnection.setConnectTimeout(60000);
				if (METHOD_GET == method) {
					httpConnection.setRequestMethod("GET");
				} else {
					httpConnection.setRequestMethod("POST");
				}
				httpConnection.setRequestProperty("Content-length", request
						.getBody().getBytes().length + "");
				httpConnection.setRequestProperty("Content-Type",
						"application/octet-stream");
				httpConnection.setRequestProperty("Connection", "Keep-Alive");
				httpConnection.setRequestProperty("Charset", "UTF-8");

				// 开启计时器
				synchronized (request) {
					if (null != request.getmTimer()) {
						request.getmTimer().schedule(new TimerTask() {
							@Override
							public void run() {
								HttpTimeoutHandler timeoutHandler = request.getmTimeoutHandler();
								request.setmResponseHandler(null);
								if (null == timeoutHandler) {
									return;
								}
								timeoutHandler.handleHttpTimeout(request
										.getRequestId());
							}
						}, request.getmTimeoutTime());
					}
				}

				AppLog.out(MY_TAG, request.getBody(), AppLog.LEVEL_INFO);

				OutputStream output = httpConnection.getOutputStream();

				if (null == request.getmResponseHandler()) {
					return;
				}
				output.write(request.getBody().getBytes());
				output.flush();
				output.close();

				int rspCode = httpConnection.getResponseCode();
				AppLog.out(MY_TAG, "rspCode: " + rspCode, AppLog.LEVEL_INFO);
				// 200 OK
				if (HttpURLConnection.HTTP_OK == rspCode) {
					StringBuffer sb = new StringBuffer();
					String readLine = null;
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(
									httpConnection.getInputStream(),
									RichResource.ENCODE_UTF));
					while (null != (readLine = reader.readLine())) {
						sb.append(readLine);
						sb.append("\n");
					}
					reader.close();

					synchronized (request) {
						if (null != request.getmTimer()) {
							request.getmTimer().cancel();
						}
						request.setResponsed(true);
						if (null != request.getmResponseHandler()) {
							request.getmResponseHandler().handleHttpResponse(
									sb.toString(),
									(byte) httpConnection.getHeaderFieldInt(
											"returnCode", -1),
									request.getRequestId());

							AppLog.out(
									MY_TAG,
									"returnCode: "
											+ (byte) httpConnection
													.getHeaderFieldInt(
															"returnCode", -1),
									AppLog.LEVEL_INFO);
						}
					}
					AppLog.out(MY_TAG, "HttpResponse: " + sb.toString(),
							AppLog.LEVEL_INFO);
					sb = null;
				} else {
					if (null != request.getmTimer()) {
						request.getmTimer().cancel();
					}
					if (null != request.getmResponseHandler()) {
						request.getmResponseHandler().handleHttpResponse(null,
								RichResource.ERROR_NET, 0);
					}
				}
			}
		} catch (Exception e) {
			if (null != request.getmTimer()) {
				request.getmTimer().cancel();
			}
			if (null != request.getmResponseHandler()) {
				request.getmResponseHandler().handleHttpResponse(null,
						RichResource.ERROR_FWQ, 0);
			}
			AppLog.out(MY_TAG, e.getMessage(), AppLog.LEVEL_ERROR);
		}
	}

	private void show(HttpPost post) {
		// HttpEntity et = response.getEntity();
		// AppLog.out(MY_TAG, EntityUtils.toString(et),
		// AppLog.LEVEL_INFO);
		// 取得服务器返回的流
		HttpEntity et = post.getEntity();
		InputStream is;
		try {
			is = et.getContent();
			StringBuffer sb = new StringBuffer();
			String readLine = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, RichResource.ENCODE_UTF));
			while (null != (readLine = reader.readLine())) {
				sb.append(readLine);
				sb.append("\n");
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getUri() {
		return DEFAULT_HOST;
	}

	public synchronized void sendMessage(final String action,
			final byte method, final HttpRequest request, final boolean isfile) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				sendMsgImpl(action, request, method, isfile);
			}
		}).start();
	}
}