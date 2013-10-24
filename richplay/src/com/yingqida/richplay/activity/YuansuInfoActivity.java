package com.yingqida.richplay.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.baseapi.Constant;
import com.yingqida.richplay.entity.Comment;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.logic.YuansuCommentLogic;
import com.yingqida.richplay.widget.PullToRefreshView;

public class YuansuInfoActivity extends SuperActivity {

	@ViewInject(R.id.pullToRefreshView)
	private PullToRefreshView pullToRefreshView;

	@ViewInject(R.id.listComm)
	private ListView listComm;

	Adapter adapter;

	private List<Comment> list = new ArrayList<Comment>();

	private HttpUtils httpUtil;

	private YuansuCommentLogic logic;
	private BitmapUtils bitmapUtilsContent;
	private BitmapUtils bitmapUtilsHead;

	@Override
	public void initData() {
		logic = YuansuCommentLogic.getInstance();
	}

	String purl = "";
	String remarkId = "";

	View headerView;
	ImageView imgGuanZhu;
	ImageView imgShare;
	ImageView imgPingLun;
	ImageView imgContent;
	TextView text_word_content;
	TextView text_name;
	TextView text_msg;
	TextView text_share_count;
	String yuansuType;
	String content;
	String shareCount;

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.picinfo_layout);
		ViewUtils.inject(this);
		purl = getIntent().getStringExtra("PURL");
		remarkId = getIntent().getStringExtra("remarkId");
		headerView = LayoutInflater.from(getBaseContext()).inflate(
				R.layout.yuansu_top_layout, null);

		listComm.addHeaderView(headerView);
		text_name = (TextView) headerView.findViewById(R.id.text_name);
		text_msg = (TextView) headerView.findViewById(R.id.text_msg);

		imgGuanZhu = (ImageView) headerView.findViewById(R.id.imgGuanZhu);
		imgShare = (ImageView) headerView.findViewById(R.id.imgShare);
		imgPingLun = (ImageView) headerView.findViewById(R.id.imgPingLun);
		text_word_content = (TextView) headerView.findViewById(R.id.content);
		imgContent = (ImageView) headerView.findViewById(R.id.imgContent);
		text_share_count = (TextView) headerView
				.findViewById(R.id.text_share_count);
		yuansuType = getIntent().getExtras().getString("yuansutype");
		content = getIntent().getExtras().getString("content");
		updateHeaderView(yuansuType);
		imgGuanZhu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pingGuanZhuClick();
			}
		});
		imgShare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pingShareClick();
			}
		});
		imgPingLun.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pingLunClick();
			}
		});

		pullToRefreshView.setEnablePullTorefresh(false);
		pullToRefreshView
				.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(PullToRefreshView view) {
						requestYuansuComment(0);
					}
				});
		pullToRefreshView
				.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

					@Override
					public void onFooterRefresh(PullToRefreshView view) {
						requestYuansuComment(1);
					}
				});

		bitmapUtilsHead = new BitmapUtils(getBaseContext());
		bitmapUtilsHead.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.failed);
		bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		updateView();
		requestYuansuComment(0);
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
				holder.imgHead = (ImageView) convertView
						.findViewById(R.id.imgHead);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.text_name.setText(getItem(position).getUname());
			holder.text_msg.setText(getItem(position).getContent());
			bitmapUtilsHead
					.display(
							holder.imgHead,
							"http://imgtest.meiliworks.com/pic/_o/a2/68/53732042564a9248900de058832f_440_441.jpg?refer_type=&expr_alt=b&frm=out_pic");
			return convertView;
		}

		class Holder {
			ImageView imgHead;
			TextView text_name;
			TextView text_msg;
		}
	}

	@Override
	public void clearData() {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void handleHttpResponse(String response, int requestId) {

	}

	private void onLoad() {
		if (actionType == 0) {
			pullToRefreshView.onHeaderRefreshComplete();
		} else {
			pullToRefreshView.onFooterRefreshComplete();
		}
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
		onLoad();
		switch (msg.what) {
		case SuperLogic.YUANSU_COMMENT_SUCCESS_MSGWHAT: {
			updateView();
			break;
		}
		}
		super.handleMsg(msg);
	}

	public void updateHeaderView(String type) {
		if (type.equals(Constant.TYPE_YUANSU_WORD)) {
			imgContent.setVisibility(View.GONE);
			text_word_content.setVisibility(View.VISIBLE);
			text_word_content.setText(content);
			if (text_word_content.getLineCount() <= 1) {
				text_word_content.setGravity(Gravity.CENTER);
			} else {
				text_word_content.setGravity(Gravity.LEFT
						| Gravity.CENTER_VERTICAL);
			}
		} else {
			imgContent.setVisibility(View.VISIBLE);
			text_word_content.setVisibility(View.GONE);
			if (null == bitmapUtilsContent) {
				bitmapUtilsContent = new BitmapUtils(getBaseContext());
				bitmapUtilsContent
						.configDefaultLoadingImage(R.drawable.ic_launcher);
				bitmapUtilsContent
						.configDefaultLoadFailedImage(R.drawable.list_item_bg);
				bitmapUtilsContent
						.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
			}
			bitmapUtilsContent.display(imgContent, content);
		}
	}

	public int actionType = 0;

	public void requestYuansuComment(int type) {
		actionType = type;
		httpUtil = new HttpUtils();
		logic.setDate(mHandler, httpUtil);
		// ((SuperActivityForFragment)
		// getActivity()).showProcessDialog(dismiss);
		logic.sendYuansuCommentRequest(getUser().getRemarkToken(), remarkId,
				type);
	}

	public void updateView() {
		if (null == adapter) {
			adapter = new Adapter();
			listComm.setAdapter(adapter);

		} else {
			adapter.notifyDataSetChanged();
		}
	}
}
