package com.yingqida.richplay.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
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
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.logic.YuansuSearchLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.widget.XListView;
import com.yingqida.richplay.widget.XListView.IXListViewListener;

public class YuansuFragment extends SuperFragment implements
		OnItemClickListener, IXListViewListener {

	@ViewInject(R.id.frameTitle)
	private RelativeLayout frameTitle;

	@ViewInject(R.id.listViewYs)
	private XListView listViewYs;

	private Adapter adapter;

	private YuansuSearchLogic logic;

	private HttpUtils httpUtil;

	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	public String keyword = "";

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
		logic = YuansuSearchLogic.getInstance();
	}

	View convertView;

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (convertView == null)
			convertView = inflater.inflate(R.layout.page_home_layout, null);
		ViewUtils.inject(this, convertView);
		frameTitle.setVisibility(View.GONE);
		listViewYs.setPullLoadEnable(true);
		listViewYs.setPullRefreshEnable(true);
		listViewYs.setXListViewListener(this);
		updateView();
		return convertView;
	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return logic.list.size();
		}

		@Override
		public Yuansu getItem(int arg0) {
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
						R.layout.yuansu_item_layout, null);
				holder.tvName = (TextView) convertView
						.findViewById(R.id.tvName);
				holder.tvCommentContent = (TextView) convertView
						.findViewById(R.id.tvCommentContent);
				holder.imgGuanZhu = (ImageView) convertView
						.findViewById(R.id.imgGuanZhu);
				holder.imgShare = (ImageView) convertView
						.findViewById(R.id.imgShare);
				holder.imgPingLun = (ImageView) convertView
						.findViewById(R.id.imgPingLun);
				holder.imgContent = (ImageView) convertView
						.findViewById(R.id.imgContent);
				holder.content = (TextView) convertView
						.findViewById(R.id.content);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.imgGuanZhu.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					pingGuanZhuClick();
				}
			});
			holder.imgShare.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					pingShareClick();
				}
			});
			holder.imgPingLun.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					pingLunClick();
				}
			});
			holder.content.setText(getItem(position).getRemarkContent());
			if (holder.content.getLineCount() <= 1) {
				holder.content.setGravity(Gravity.CENTER);
			} else {
				holder.content.setGravity(Gravity.LEFT
						| Gravity.CENTER_VERTICAL);
			}
			holder.tvName.setText(getItem(position).getUser().getName());
			holder.tvCommentContent.setText(getItem(position).getUser()
					.getComment_content());
			return convertView;
		}

		class Holder {
			TextView tvName;
			TextView tvCommentContent;
			ImageView imgGuanZhu;
			ImageView imgShare;
			ImageView imgPingLun;
			ImageView imgContent;
			TextView content;
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
	public void refreshYuansu(int type) {
		clearData();
		httpUtil = new HttpUtils();
		logic.setDate(fHandler, httpUtil);
		showProcessDialog(dismiss);
		logic.sendPageHomeYuanSuRequest(getUser().getRemarkToken(), type,
				keyword);
	}

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {

	}

	@Override
	public void handleHttpResponse(String response, int requestId) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void handleHttpTimeout(int paramInt) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(getActivity().getBaseContext(),
				YuansuInfoActivity.class).putExtra("content",
				adapter.getItem(position - 1).getRemarkContent()).putExtra(
				"yuansutype", Constant.TYPE_YUANSU_WORD));
	}

	@Override
	public void onRefresh() {
		refreshYuansu(0);
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
		case SuperLogic.GET_SEARCH_YUANSU_SUCCESS_MSGWHAT: {
			updateView();
			onLoad();
			break;
		}
		}
		onLoad();
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
		this.keyword = keyword;
		refreshYuansu(0);
	}
}
