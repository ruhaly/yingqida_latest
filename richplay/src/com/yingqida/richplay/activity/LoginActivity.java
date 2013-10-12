package com.yingqida.richplay.activity;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.twitter.WebsiteActivity;
import com.yingqida.richplay.HomeActivity;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.baseapi.common.GlobalVar;
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.logic.LoginLogic;
import com.yingqida.richplay.logic.SuperLogic;

public class LoginActivity extends SuperActivity {

	public LoginLogic loginLogic;

	@ViewInject(R.id.btn_login_facebook)
	private LoginButton btn_login_facebook;

	@ViewInject(R.id.btn_login_twitter)
	private TextView btn_login_twitter;

	@ViewInject(R.id.btn_login)
	private TextView btn_login;

	@ViewInject(R.id.text_account)
	private TextView text_account;

	@ViewInject(R.id.text_pwd)
	private TextView text_pwd;

	public static final String HASH1 = "n0HVZZ3RXdM4KzR+FILGAc0Ak8c=";

	private HttpUtils httpLogin;

	@Override
	public void initData() {
		loginLogic = LoginLogic.getInstance();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.login_layout);
		ViewUtils.inject(this);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(paramBundle);
		btn_login_facebook
				.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
					@Override
					public void onUserInfoFetched(GraphUser user) {
						LoginActivity.this.user = user;
						// 登录成功,更新界面
					}
				});
		User user = GlobalVar.ins.getUser(getAppShare());
		if (null != user) {
			text_account.setText(user.getAccount());
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

	@OnClick(R.id.btn_login_twitter)
	public void btnTwitterLogin(View view) {
		initializeTwitter();
		String accessToken = getTwitterAccessToken();
		String accessTokenSecret = getTwitterAccessTokenSecret();
		if (accessToken == null || accessTokenSecret == null) { // 从文件系统里读取的access_token
																// 和
			reOauth(); // 验证以及出问题的重复验证都会用这个函数
		} else {
			mTwitter.setOAuthAccessToken(new AccessToken(accessToken,
					accessTokenSecret));
		}
	}

	public String getTwitterAccessToken() {
		return getAppShare().getString(TWITTERACCESSTOKEN, null);
	}

	public String getTwitterAccessTokenSecret() {
		return getAppShare().getString(TWITTERACCESSTOKENSECRET, null);
	}

	/******************************** Facebook登录 begin **************************/
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private GraphUser user;
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
		@Override
		public void onError(FacebookDialog.PendingCall pendingCall,
				Exception error, Bundle data) {
			Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
		}

		@Override
		public void onComplete(FacebookDialog.PendingCall pendingCall,
				Bundle data) {
			Log.d("HelloFacebook", "Success!");
		}
	};

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
		}
	}

	private void onClickLogout() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}

	/******************************** Facebook登录 end **************************/
	/******************************** Twitter登录 begin **************************/

	// private TwitterApp mTwitter;
	private static final String TWITTER_CONSUMER_KEY = "qRVosgvM2iDFpeqS7DYRZA";
	private static final String TWITTER_SECRET_KEY = "sph0Yf9uYSOPDuo1cjgZfdIOv85CZHrNIB1kOfNg34s";
	private Twitter mTwitter;
	private static final String TWITTERACCESSTOKEN = "TwitterAccessToken";
	private static final String TWITTERACCESSTOKENSECRET = "TwitterAccessTokenSecret";
	public static final String TWITTER_CALLBACK_URL = "http://www.richplay.com.tw";
	public static final String TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	public static final String EXTRA_URL = "AuthenticationURL";
	RequestToken requestToken;
	public static final int TITTER_OAUTH = 1001;

	public void setTwitterAccessToken(String accessToken) {
		getAppShare().edit().putString(TWITTERACCESSTOKEN, accessToken);
	}

	public void setTwitterAccessTokenSecret(String accessTokenSecret) {
		getAppShare().edit().putString(TWITTERACCESSTOKENSECRET,
				accessTokenSecret);
	}

	OnDismissListener dismissTwitter = new OnDismissListener() {

		public void onDismiss(DialogInterface dialog) {
			requestToken = null;
		}
	};

	private void reOauth() {
		showProcessDialog(dismissTwitter);

		mTwitter.setOAuthAccessToken(null);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					requestToken = mTwitter
							.getOAuthRequestToken(TWITTER_CALLBACK_URL); // 获取requestToken
					if (requestToken != null) {
						Intent i = new Intent(getBaseContext(),
								WebsiteActivity.class);
						i.putExtra(EXTRA_URL,
								requestToken.getAuthenticationURL());
						startActivityForResult(i, TITTER_OAUTH); // 开启一个带webview的Activity处理，名字为WebsiteActivity
					}
					dismissProgress();
				} catch (TwitterException e) {
					Log.d("TEST", e.getMessage());
				}
			}
		}).start();
	}

	private void initializeTwitter() {

		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY); // consumer_key
		builder.setOAuthConsumerSecret(TWITTER_SECRET_KEY); // consumer_secret
		Configuration conf = builder.build();
		mTwitter = new TwitterFactory(conf).getInstance();
		mTwitter.setOAuthAccessToken(null);
	}

	public void twitterLogout() {
		if (null != mTwitter.getAuthorization()) {
			resetAccessToken();
		}
	}

	public void resetAccessToken() {
		getAppShare().edit().putString(TWITTERACCESSTOKEN, null).commit();
		getAppShare().edit().putString(TWITTERACCESSTOKENSECRET, null).commit();
	}

	/******************************** Twitter登录 end **************************/
	@Override
	protected void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	String oauthVerifier;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TITTER_OAUTH) {
			if (resultCode == RESULT_OK) {
				oauthVerifier = data.getStringExtra(TWITTER_OAUTH_VERIFIER);
				try {
					AccessToken at = mTwitter.getOAuthAccessToken(requestToken,
							oauthVerifier);
					String accessToken = at.getToken();
					String accessTokenSecret = at.getTokenSecret();
					mTwitter.setOAuthAccessToken(at); // 这里要设置access_token
					setTwitterAccessToken(accessToken); // 记得存入文件系统
					setTwitterAccessTokenSecret(accessTokenSecret);
					showToast("Twitter登录成功");
				} catch (TwitterException e) {
					Log.d("TEST", e.getMessage());
				}
			}
		} else {
			uiHelper.onActivityResult(requestCode, resultCode, data,
					dialogCallback);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			httpLogin.getHttpClient().getConnectionManager().shutdown();
		}
	};

	@OnClick(R.id.btn_login)
	public void login(View view) {
		if (checkDataEmpty()) {
			showToast(getString(R.string.account_pwd_not_empty));
			return;
		}
		httpLogin = new HttpUtils();
		loginLogic.setDate(mHandler, httpLogin);
		showProcessDialog(dismiss);
		loginLogic.sendLoginRequest(text_account.getText().toString(), text_pwd
				.getText().toString());
	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.LOGIN_SUCCESS_MSGWHAT:
			showToast("登录成功");
			saveUser();
			toHomeActivity();
			break;
		case SuperLogic.LOGIN_ERROR_MSGWHAT: {
			showToast("登录失败");
		}
		default:
			break;
		}
		super.handleMsg(msg);
	}

	/**
	 * 
	 * Function:登录成功后保存登录信息
	 * 
	 * @author ruhaly DateTime 2013-10-11 上午10:27:50
	 */
	public void saveUser() {
		// GlobalVar.ins.clearUserDate(getAppShare());
		User user = new User();
		user.account = text_account.getText().toString().trim();
		user.pwd = text_pwd.getText().toString().trim();
		GlobalVar.ins.storeUser(getAppShare(), user);
	}

	/**
	 * 
	 * Function:跳转到首页
	 * 
	 * @author ruhaly DateTime 2013-10-11 上午10:32:57
	 */
	public void toHomeActivity() {
		Intent intent = new Intent(getBaseContext(), HomeActivity.class);
		startActivity(intent);
		finish();
	}

	@SuppressLint("NewApi")
	public boolean checkDataEmpty() {
		return text_account.getText().toString().trim().isEmpty()
				|| text_pwd.getText().toString().trim().isEmpty() ? true
				: false;
	}

	@Override
	public void handleHttpResponse(String response, int requestId) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showLogoutDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
