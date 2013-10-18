package com.yingqida.richplay.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.MenuActivity;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.baseapi.http.HttpResponseHanlder;
import com.yingqida.richplay.baseapi.http.HttpTimeoutHandler;
import com.yingqida.richplay.logic.SuperLogic;

public abstract class SuperFragment extends Fragment implements
		View.OnClickListener, HttpResponseHanlder, HttpTimeoutHandler {
	public int getScreenH() {
		return getActivity().getWindowManager().getDefaultDisplay().getHeight();
	}

	public int getScreenW() {
		return getActivity().getWindowManager().getDefaultDisplay().getWidth();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public abstract void updateView();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initData();
		return initLayout(inflater, container, savedInstanceState);
	}

	public abstract void initData();

	public abstract View initLayout(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	public void switchFragment(SuperFragment fragment) {
		if (getActivity() == null)
			return;
		if (getActivity() instanceof MenuActivity) {
			((MenuActivity) getActivity()).switchContent(fragment);
		}
	}

	/**
	 * mHandler
	 */
	public MewwHandler fHandler = new MewwHandler();

	@SuppressLint("HandlerLeak")
	public class MewwHandler extends Handler {
		/*
		 * (non-Javadoc) (覆盖方法) 方法名称： handleMessage 作者： ruhaly 方法描述：
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			handleMsg(msg);
		}
	}

	/**
	 * 方法名称： handleMsg 作者： ruhaly 方法描述： 处理消息 输入参数： @param msg 返回类型： void
	 */
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case RichResource.ERROR_NET: {
			((SuperActivityForFragment) getActivity())
					.showToast(getString(R.string.error_net));
			break;
		}
		case RichResource.ERROR_FWQ: {
			((SuperActivityForFragment) getActivity())
					.showToast(getString(R.string.error_fwq));
			break;
		}
		case SuperLogic.DATA_FORMAT_ERROR_MSGWHAT: {
			((SuperActivityForFragment) getActivity())
					.showToast(getString(R.string.date_format_error));
			break;
		}
		default:
			break;
		}
		((SuperActivityForFragment) getActivity()).dismissProgress();
	}
}
