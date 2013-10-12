package com.yingqida.richplay.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Toast;

import com.yingqida.richplay.HomeActivity;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;

public class LoginActivity extends SuperActivity {

	@Override
	public void initData() {

	}

	// WebView webview;

	@Override
	public void initLayout() {
		setContentView(R.layout.login_layout);
		// webview = (WebView) findViewById(R.id.webview);
		// WebSettings webSettings = webview.getSettings();
		// webSettings.setSavePassword(false);
		// webSettings.setSaveFormData(false);
		// // 下面的一句话是必须的，必须要打开javaScript不然所做一切都是徒劳的
		// webSettings.setJavaScriptEnabled(true);
		// webSettings.setSupportZoom(false);
		// // webSettings.setLoadWithOverviewMode(true);
		// // webSettings.setUseWideViewPort(true);
		// webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// webview.setWebChromeClient(new MyWebChromeClient());
		//
		// // 看这里用到了 addJavascriptInterface 这就是我们的重点中的重点
		// // 我们再看他的DemoJavaScriptInterface这个类。还要这个类一定要在主线程中
		// webview.addJavascriptInterface(new DemoJavaScriptInterface(),
		// "click");
		//
		// webview.loadUrl("file:///android_asset/demo.html");
		// webview.addJavascriptInterface("测试", "demo");
		// webview.loadUrl("javascript:wave()");
	}

	// 这是他定义由 addJavascriptInterface 提供的一个Object
	final class DemoJavaScriptInterface {
		DemoJavaScriptInterface() {
		}

		/**
		 * This is not called on the UI thread. Post a runnable to invoke
		 * 这不是呼吁界面线程。发表一个运行调用 loadUrl on the UI thread. loadUrl在UI线程。
		 */
		public void clickOnAndroid(final String str) { // 注意这里的名称。它为clickOnAndroid(),注意，注意，严重注意
			mHandler.post(new Runnable() {

				public void run() {

					Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT)
							.show();

				}

			});
		}
	}

	/**
	 * Provides a hook for calling "alert" from javascript. Useful for
	 * 从javascript中提供了一个叫“提示框” 。这是很有用的 debugging your javascript.
	 * 调试你的javascript。
	 */
	final class MyWebChromeClient extends WebChromeClient {
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			result.confirm();
			return true;
		}
	}

	@Override
	public void clearData() {

	}

	public void btnRegisterLis(View view) {
		startActivity(new Intent(getBaseContext(), RegisterActivity.class));
	}

	public void btnLogin(View view) {
		startActivity(new Intent(getBaseContext(), HomeActivity.class));
	}
	
}
