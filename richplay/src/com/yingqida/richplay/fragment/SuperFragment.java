package com.yingqida.richplay.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.MenuActivity;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.logic.SuperLogic;

public abstract class SuperFragment extends Fragment implements
		View.OnClickListener {
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

	public void reqeustDate(String keyword) {

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
			showToast(getString(R.string.error_net));
			break;
		}
		case RichResource.ERROR_FWQ: {
			showToast(getString(R.string.error_fwq));
			break;
		}
		case SuperLogic.DATA_FORMAT_ERROR_MSGWHAT: {
			showToast(getString(R.string.date_format_error));
			break;
		}
		default:
			break;
		}
		((SuperActivityForFragment) getActivity()).dismissProgress();
	}

	public void showToast(String str) {
		if (str != null)
			((SuperActivityForFragment) getActivity()).showToast(str);
	}

	public void showProcessDialog(DialogInterface.OnDismissListener dismiss) {
		((SuperActivityForFragment) getActivity()).showProgressDialog("",
				getString(R.string.loading), true, dismiss);
	}

	public User getUser() {
		return ((SuperActivityForFragment) getActivity()).getUser();
	}

	public void toggle() {
		((SuperActivityForFragment) getActivity()).toggle();
	}

	public void hiddenSoft(EditText view) {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
		if (isOpen) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
}
