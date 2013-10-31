package com.yingqida.richplay.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.YuansuInfoActivity;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;
import com.yingqida.richplay.baseapi.Constant;
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.logic.UserLogic;
import com.yingqida.richplay.widget.XListView;
import com.yingqida.richplay.widget.XListView.IXListViewListener;

public class YonghuFragment extends SuperFragment implements
		OnItemClickListener, IXListViewListener {

	public String keyword = "";

	@ViewInject(R.id.frameTitle)
	private RelativeLayout frameTitle;

	@ViewInject(R.id.listViewYs)
	private XListView listViewYs;

	private Adapter adapter;

	private UserLogic logic;

	private HttpUtils httpUtil;

	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	@OnClick(R.id.btnToggle)
	public void btnToggleClick(View view) {
		((SuperActivityForFragment) getActivity()).toggle();
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void updateView() {
		if (null == adapter) {
			adapter = new Adapter();
			listViewYs.setAdapter(adapter);
			listViewYs.setOnItemClickListener(this);
		} else {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void initData() {
		logic = UserLogic.getInstance();
	}

	View convertView;

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (convertView == null)
			convertView = inflater.inflate(R.layout.yonghu_layout, null);
		ViewUtils.inject(this, convertView);
		frameTitle.setVisibility(View.GONE);
		listViewYs.setPullLoadEnable(true);
		listViewYs.setPullRefreshEnable(true);
		listViewYs.setXListViewListener(this);
		updateView();
		refreshYonghu(0);
		return convertView;
	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return logic.list.size();
		}

		@Override
		public User getItem(int arg0) {
			return logic.list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (null == convertView) {
				holder = new Holder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.user_item_layout, null);
				holder.imgHeader = (ImageView) convertView
						.findViewById(R.id.imgHeader);
				holder.tvName = (TextView) convertView
						.findViewById(R.id.tvName);
				// holder.tvBgz = (TextView)
				// convertView.findViewById(R.id.tvBgz);
				// holder.btnGz = (Button) convertView.findViewById(R.id.btnGz);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			return convertView;
		}

		class Holder {
			ImageView imgHeader;
			TextView tvName;
			TextView tvBgz;
			Button btnGz;
		}
	}

	DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			httpUtil.getHttpClient().getConnectionManager().shutdown();
		}
	};

	/**
	 * 
	 * Function:获取元素
	 * 
	 * @author ruhaly DateTime 2013-10-17 上午10:34:17
	 */
	public void refreshYonghu(int type) {
		clearData();
		httpUtil = new HttpUtils();
		logic.setDate(fHandler, httpUtil);
		logic.sendGetUserRequest(getUser().getRemarkToken(), keyword, type);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(getActivity().getBaseContext(),
				YuansuInfoActivity.class).putExtra("content",
				adapter.getItem(position).getComment_content()).putExtra(
				"label", ""));
	}

	@Override
	public void onRefresh() {
		refreshYonghu(0);
	}

	@Override
	public void onLoadMore() {
		listViewYs.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 2000);
	}

	private void onLoad() {
		listViewYs.stopRefresh();
		listViewYs.stopLoadMore();
		listViewYs.setRefreshTime("刚刚");
	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.GET_SEARCH_USER_SUCCESS_MSGWHAT: {
			onLoad();
			updateView();
			break;
		}

		default:
			break;
		}
		super.handleMsg(msg);
	}

	public void pingGuanZhuClick() {
		((SuperActivityForFragment) getActivity()).showToast("添加关注");
	}

	public void pingShareClick() {
		((SuperActivityForFragment) getActivity()).showToast("分享");
	}

	public void pingLunClick() {
		((SuperActivityForFragment) getActivity()).showToast("评论");
	}

	public void clearData() {
		logic.clear();
	}

	@Override
	public void reqeustDate(String keyword) {
		showToast("yonghu:" + keyword);
	}
}
