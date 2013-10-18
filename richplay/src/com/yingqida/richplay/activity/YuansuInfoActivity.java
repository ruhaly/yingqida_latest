package com.yingqida.richplay.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.paintpad.activity.Main;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.baseapi.http.HttpSenderUtils;
import com.yingqida.richplay.entity.Comment;
import com.yingqida.richplay.pubuliu.ImageFetcher;
import com.yingqida.richplay.pubuliu.ImageWorker.ICallBack;
import com.yingqida.richplay.widget.XListView;
import com.yingqida.richplay.widget.XListView.IXListViewListener;

public class YuansuInfoActivity extends SuperActivity implements
		IXListViewListener {
	@ViewInject(R.id.listComm)
	private XListView listComm;

	Adapter adapter;

	private List<Comment> list = new ArrayList<Comment>();

	private ImageView img;
	public ImageFetcher mImageFetcher;

	private HttpSenderUtils httputils;

	@Override
	public void initData() {
		for (int i = 0; i < 20; i++) {
			Comment c = new Comment();
			c.setUname("user" + i);
			c.setCmsg("usermsg" + i);
			list.add(c);
		}
	}

	private ICallBack callback = new ICallBack() {

		@Override
		public void invoke(final String name, int code) {
			if (200 == code) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						showToast("success");
						updateView();
					}
				});
			}
		}
	};

	public void updateView() {
		img.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				startActivity(new Intent(getBaseContext(), Main.class)
						.putExtra("PURL", purl));
				showToast("setOnLongClickListener");
				return false;
			}
		});
		img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(getBaseContext(), Main.class)
						.putExtra("PURL", purl));
			}
		});
	}

	String purl = "";

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.picinfo_layout);
		ViewUtils.inject(this);
		purl = getIntent().getStringExtra("PURL");
		listComm = (XListView) findViewById(R.id.listComm);
		listComm.setPullLoadEnable(true);
		listComm.setPullRefreshEnable(false);
		listComm.setXListViewListener(this);
		img = (ImageView) findViewById(R.id.img);
		img.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
		mImageFetcher = initFetcher(mImageFetcher, callback);
		mImageFetcher.loadImage(purl, img, getScreenW(), true);
		if (null == adapter) {
			adapter = new Adapter();
			listComm.setAdapter(adapter);
			// setListViewHeight(listComm);

		} else {
			adapter.notifyDataSetChanged();
		}
	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Comment getItem(int arg0) {
			return list.get(arg0);
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
				convertView = LayoutInflater.from(getBaseContext()).inflate(
						R.layout.comment_item_layout, null);
				holder.text_name = (TextView) convertView
						.findViewById(R.id.text_name);
				holder.text_msg = (TextView) convertView
						.findViewById(R.id.text_msg);
				holder.frameTop = convertView.findViewById(R.id.frameTop);
				holder.frameBottom = convertView.findViewById(R.id.frameBottom);
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
			if (0 == position) {
				holder.frameTop.setVisibility(View.VISIBLE);
				holder.frameBottom.setVisibility(View.GONE);
				holder.imgGuanZhu
						.setOnClickListener(new View.OnClickListener() {

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
				holder.imgPingLun
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								pingLunClick();
							}
						});
			} else {
				holder.frameTop.setVisibility(View.GONE);
				holder.frameBottom.setVisibility(View.VISIBLE);
				holder.text_name.setText(getItem(position).getUname());
				holder.text_msg.setText(getItem(position).getCmsg());
			}
			return convertView;
		}

		class Holder {
			TextView text_name;
			TextView text_msg;
			View frameTop;
			View frameBottom;
			ImageView imgGuanZhu;
			ImageView imgShare;
			ImageView imgPingLun;
		}
	}

	@Override
	public void clearData() {

	}

	/**
	 * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
	 * 
	 * @param listView
	 */
	public void setListViewHeight(ListView listView) {

		// 获取ListView对应的Adapter

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void handleHttpResponse(String response, int requestId) {

	}

	@Override
	public void onRefresh() {
		listComm.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		listComm.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 2000);
	}

	private void onLoad() {
		listComm.stopRefresh();
		listComm.stopLoadMore();
		listComm.setRefreshTime("刚刚");
	}

	public void pingGuanZhuClick() {
		showToast("添加关注");
	}

	public void pingShareClick() {
		showToast("分享");
	}

	public void pingLunClick() {
		showToast("评论");
	}

	@Override
	public void handleMsg(Message msg) {
		super.handleMsg(msg);
	}
}
