package com.yingqida.richplay.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.YuansuInfoActivity;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.logic.PageHomeLogic;
import com.yingqida.richplay.widget.XListView;
import com.yingqida.richplay.widget.XListView.IXListViewListener;

public class PageHomeFragment extends SuperFragment implements
		OnItemClickListener, IXListViewListener {

	@ViewInject(R.id.listViewYs)
	private XListView listViewYs;

	private Adapter adapter;

	private PageHomeLogic logic;

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

	}

	@Override
	public void initData() {
		logic = PageHomeLogic.getInstance();
		for (int i = 0; i < 20; i++) {
			Yuansu ys = new Yuansu();
			User user = new User();
			user.setName("用户" + i);
			user.setContent("用户" + i);
			ys.setUser(user);
			logic.list.add(ys);
		}
	}

	View convertView;

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (convertView == null)
			convertView = inflater.inflate(R.layout.page_home_layout, null);
		ViewUtils.inject(this, convertView);
		listViewYs.setPullLoadEnable(true);
		listViewYs.setPullRefreshEnable(true);
		listViewYs.setXListViewListener(this);
		if (null == adapter) {
			adapter = new Adapter();
			listViewYs.setAdapter(adapter);
			listViewYs.setOnItemClickListener(this);
		} else {
			adapter.notifyDataSetChanged();
		}
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
				holder.tvContent = (TextView) convertView
						.findViewById(R.id.tvContent);
				holder.imgGuanZhu = (ImageView) convertView
						.findViewById(R.id.imgGuanZhu);
				holder.imgShare = (ImageView) convertView
						.findViewById(R.id.imgShare);
				holder.imgPingLun = (ImageView) convertView
						.findViewById(R.id.imgPingLun);
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
			holder.tvName.setText(getItem(position).getUser().getName());
			holder.tvContent.setText(getItem(position).getUser().getContent());
			return convertView;
		}

		class Holder {
			TextView tvName;
			TextView tvContent;
			ImageView imgGuanZhu;
			ImageView imgShare;
			ImageView imgPingLun;
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
	public void requestYuansu() {
		httpUtil = new HttpUtils();
		logic.setDate(fHandler, httpUtil);
		((SuperActivityForFragment) getActivity()).showProcessDialog(dismiss);
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
				YuansuInfoActivity.class)
				.putExtra(
						"PURL",
						"http://d.hiphotos.baidu.com/album/w%3D2048/sign=709a42c779f0f736d8fe4b013e6db119/1e30e924b899a90197f62f8e1c950a7b0308f543.jpg"));
	}

	@Override
	public void onRefresh() {
		listViewYs.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 2000);
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

	public void pingGuanZhuClick() {
		((SuperActivityForFragment) getActivity()).showToast("添加关注");
	}

	public void pingShareClick() {
		((SuperActivityForFragment) getActivity()).showToast("分享");
	}

	public void pingLunClick() {
		((SuperActivityForFragment) getActivity()).showToast("评论");
	}
}
