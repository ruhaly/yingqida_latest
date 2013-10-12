package com.twitter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.LoginActivity;
import com.yingqida.richplay.activity.common.SuperActivity;

public class WebsiteActivity extends SuperActivity {

	@ViewInject(R.id.webView)
	private WebView webView;

	@ViewInject(R.id.progressBar)
	private ProgressBar progressBar;

	@Override
	public void initData() {

	}

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.twitter_layout);
		ViewUtils.inject(this);
		webView.loadUrl(getIntent().getStringExtra(LoginActivity.EXTRA_URL));
		webView.setWebViewClient(new MyWebViewClient());
	}

	@Override
	public void clearData() {

	}

	private class MyWebViewClient extends WebViewClient { // 要求是可以点击其他超练级继续浏览网页的，因此继承WebViewClient实现

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) { // 在网页加载结束后，对callback_url带回的参数进行获取。并关闭这个activity
			super.onPageFinished(view, url);
			progressBar.setVisibility(View.GONE);

			if (url != null
					&& url.startsWith(LoginActivity.TWITTER_CALLBACK_URL)) {
				String oauthVerifer = Uri.parse(url).getQueryParameter(
						LoginActivity.TWITTER_OAUTH_VERIFIER);

				Intent i = new Intent();
				i.putExtra(LoginActivity.TWITTER_OAUTH_VERIFIER, oauthVerifer);
				setResult(RESULT_OK, i); // 把获取的oauth_verifer
				finish();
			}
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			progressBar.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void handleHttpResponse(String response, int requestId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleHttpException(HttpException error, String msg) {
		// TODO Auto-generated method stub
		
	}
}
