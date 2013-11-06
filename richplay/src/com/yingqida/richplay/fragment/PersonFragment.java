package com.yingqida.richplay.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.ModifyPWDActivity;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.logic.PersonInfoLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.logic.UserLogic;
import com.yingqida.richplay.widget.PullToRefreshView;

public class PersonFragment extends SuperFragment {

	@ViewInject(R.id.frameMoreInfo)
	private LinearLayout frameMoreInfo;

	PersonInfoLogic logic;

	UserLogic uLogic;
	private static PersonFragment ins;

	@ViewInject(R.id.rgShiptime)
	private RadioGroup rgShiptime;

	@ViewInject(R.id.etUname)
	private EditText etUname;

	@ViewInject(R.id.etSummary)
	private EditText etSummary;

	@ViewInject(R.id.etEmail)
	private EditText etEmail;

	@ViewInject(R.id.tvPwd)
	private TextView tvPwd;

	@ViewInject(R.id.etSchool)
	private EditText etSchool;

	@ViewInject(R.id.etCompany)
	private EditText etCompany;

	@ViewInject(R.id.etRealname)
	private EditText etRealname;

	@ViewInject(R.id.etAddr)
	private EditText etAddr;

	@ViewInject(R.id.etZipcode)
	private EditText etZipcode;

	@ViewInject(R.id.etPhone)
	private EditText etPhone;

	@ViewInject(R.id.btnConfirm)
	private Button btnConfirm;

	public synchronized static PersonFragment getIns() {
		if (null == ins) {
			ins = new PersonFragment();
		}
		return ins;
	}

	@OnClick(R.id.btnToggle)
	public void btnToggleClick(View view) {
		((SuperActivityForFragment) getActivity()).toggle();
	}

	@Override
	public void onClick(View v) {

	}

	@OnClick(R.id.btnConfirm)
	public void btnConfirmClick(View view) {
		User u = new User();
		u.setName(etUname.getText().toString());
		u.setSummary(etSummary.getText().toString());
		u.setEmail(etEmail.getText().toString());
		u.setEducation(etSchool.getText().toString());
		u.setCommpany(etCompany.getText().toString());
		u.setRealname(etRealname.getText().toString());
		u.setAddr(etAddr.getText().toString());
		u.setZipCode(etZipcode.getText().toString());
		u.setPhone(etPhone.getText().toString());
		if (rgShiptime.getCheckedRadioButtonId() == R.id.shiptime1) {
			u.setShipTime("1");
		} else if (rgShiptime.getCheckedRadioButtonId() == R.id.shiptime2) {
			u.setShipTime("2");
		} else {
			u.setShipTime("3");
		}

		httpUtil = new HttpUtils();
		uLogic.setDate(fHandler, httpUtil);
		((SuperActivityForFragment) getActivity()).showProcessDialog(udismiss);
		uLogic.sendSaveUserInfoRequest(getUser().getRemarkToken(), u);
	}

	@Override
	public void updateView() {
		etUname.setText(uLogic.user.getName());
		etSummary.setText(uLogic.user.getSummary());
		etEmail.setText(uLogic.user.getEmail());
		etSchool.setText(uLogic.user.getEducation());
		etCompany.setText(uLogic.user.getCommpany());
		etRealname.setText(uLogic.user.getRealname());
		etAddr.setText(uLogic.user.getAddr());
		etZipcode.setText(uLogic.user.getZipCode());
		etPhone.setText(uLogic.user.getPhone());
		if (uLogic.user.getShipTime().equals("1")) {
			((RadioButton) rgShiptime.getChildAt(0)).setChecked(true);
		} else if (uLogic.user.getShipTime().equals("2")) {
			((RadioButton) rgShiptime.getChildAt(1)).setChecked(true);
		} else {
			((RadioButton) rgShiptime.getChildAt(2)).setChecked(true);
		}
		if (uLogic.user.getIs_avatar().equals("true")) {
			bitmapUtilsHead.display(imgHead,
					getHeadUrl(1, 2, getUser().getUid()));
		} else {
			bitmapUtilsHead.display(imgHead,
					getHeadUrl(2, 2, getUser().getUid()));
		}
	}

	private void onLoad() {

		pullToRefreshView.onHeaderRefreshComplete();
	}

	@Override
	public void initData() {
		logic = PersonInfoLogic.getInstance();
		uLogic = UserLogic.getInstance();
	}

	View convertView;
	private BitmapUtils bitmapUtilsHead;

	@ViewInject(R.id.pullToRefreshView)
	private PullToRefreshView pullToRefreshView;

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (convertView == null)
			convertView = inflater.inflate(R.layout.person_info_layout, null);
		ViewUtils.inject(this, convertView);
		pullToRefreshView.setEnablePullLoadMoreDataStatus(false);
		pullToRefreshView
				.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						getUserInfo();
					}
				});
		bitmapUtilsHead = new BitmapUtils(getActivity());
		bitmapUtilsHead.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		getUserInfo();
		return convertView;
	}

	DialogInterface.OnDismissListener udismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			uLogic.stop();
		}
	};

	public void getUserInfo() {
		httpUtil = new HttpUtils();
		uLogic.setDate(fHandler, httpUtil);
		((SuperActivityForFragment) getActivity()).showProcessDialog(udismiss);
		uLogic.sendGetUserInfoRequest(getUser().getRemarkToken(), getUser()
				.getUid());
	}

	@OnClick(R.id.tvMore)
	public void tvMoreClick(View view) {
		if (frameMoreInfo.getVisibility() == View.GONE) {
			frameMoreInfo.setVisibility(View.VISIBLE);
		} else {
			frameMoreInfo.setVisibility(View.GONE);
		}
	}

	// 上传头像
	@ViewInject(R.id.imgHead)
	public ImageView imgHead;

	@OnClick(R.id.imgHead)
	public void imgHeadClick(View view) {
		((SuperActivityForFragment) getActivity()).showPicturePicker(true);
	}

	public Bitmap bitmap;
	private HttpUtils httpUtil;
	DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			// httpUtil.getHttpClient().getConnectionManager().shutdown();
			logic.stop();
		}
	};

	@SuppressLint("NewApi")
	public void uploadHeadPic(Bitmap bitmap, String headUrl) {
		this.bitmap = bitmap;
		showToast(headUrl);
		if (headUrl != null && !headUrl.isEmpty()) {
			httpUtil = new HttpUtils();
			logic.setDate(fHandler, httpUtil);
			((SuperActivityForFragment) getActivity())
					.showProcessDialog(dismiss);
			logic.sendUploadHeadRequest(getUser().getRemarkToken(), headUrl);
		}
	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.UPLOAD_HEAD_SUCCESS_MSGWHAT: {
			showToast(getString(R.string.upload_head_success));
			imgHead.setImageBitmap(bitmap);
			break;
		}
		case SuperLogic.USER_INFO_SUCCESS_MSGWHAT: {
			onLoad();
			updateView();
			break;
		}
		case SuperLogic.UPDATE_USER_INFO_SUCCESS_MSGWHAT: {
			showToast(getString(R.string.update_userinfo_success));
			break;
		}
		default:
			break;
		}
		super.handleMsg(msg);
	}

	@OnClick(R.id.tvPwd)
	public void tvPwdClick(View view) {
		getActivity().startActivity(
				new Intent(getActivity().getBaseContext(),
						ModifyPWDActivity.class));
	}
}
