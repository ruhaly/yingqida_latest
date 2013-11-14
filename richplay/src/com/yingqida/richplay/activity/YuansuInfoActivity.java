package com.yingqida.richplay.activity;

import java.io.InputStream;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.logic.ShareAndFollowLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.logic.YuansuCommentLogic;
import com.yingqida.richplay.pubuliu.XListView;
import com.yingqida.richplay.pubuliu.XListView.IXListViewListener;

public class YuansuInfoActivity extends SuperActivity {

	// @ViewInject(R.id.pullToRefreshView)
	// private PullToRefreshView pullToRefreshView;

	@ViewInject(R.id.listComm)
	private XListView listComm;

	Adapter adapter;

	private HttpUtils httpUtil;

	private YuansuCommentLogic logic;
	private BitmapUtils bitmapUtilsContent;
	private BitmapUtils bitmapUtilsHead;

	private ShareAndFollowLogic sLogic;

	@Override
	public void initData() {
		logic = YuansuCommentLogic.getInstance();
		sLogic = ShareAndFollowLogic.getInstance();
	}

	String purl = "";
	String remarkId = "";
	String followstate = "";

	View headerView;
	ImageView imgGuanZhu;
	ImageView imgShare;
	ImageView imgPingLun;
	ImageView imgContent;
	TextView text_word_content;
	TextView text_name;
	TextView text_msg;
	TextView text_share_count;
	String content;
	String shareCount;
	String label;

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.picinfo_layout);
		ViewUtils.inject(this);
		purl = getIntent().getStringExtra("PURL");
		remarkId = getIntent().getStringExtra("remarkId");
		label = getIntent().getStringExtra("label");
		followstate = getIntent().getExtras().getString("followstate");
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
		content = getIntent().getExtras().getString("content");
		updateHeaderView(label);
		imgGuanZhu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pingGuanZhuClick(logic.ys.getFollowState());
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

		listComm.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				requestYuansuComment(0);
			}

			@Override
			public void onLoadMore() {
				requestYuansuComment(1);
			}
		});
		listComm.setPullLoadEnable(false);

		// pullToRefreshView.setEnablePullTorefresh(false);
		// pullToRefreshView
		// .setOnHeaderRefreshListener(new
		// PullToRefreshView.OnHeaderRefreshListener() {
		//
		// @Override
		// public void onHeaderRefresh(PullToRefreshView view) {
		// requestYuansuComment(0);
		// }
		// });
		// pullToRefreshView
		// .setOnFooterRefreshListener(new
		// PullToRefreshView.OnFooterRefreshListener() {
		//
		// @Override
		// public void onFooterRefresh(PullToRefreshView view) {
		// requestYuansuComment(1);
		// }
		// });

		bitmapUtilsHead = new BitmapUtils(getBaseContext());
		bitmapUtilsHead.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.failed);
		bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		updateGuanzhuImg();
		updateView();
		requestYuansuComment(0);
	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return logic.ys.getCommentList().size();
		}

		@Override
		public Comment getItem(int arg0) {
			return logic.ys.getCommentList().get(arg0);
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
			if (getItem(position).getIs_avatar().equals("true")) {
				bitmapUtilsHead.display(holder.imgHead,
						getHeadUrl(1, 2, getItem(position).getUid()));
			} else {
				bitmapUtilsHead.display(holder.imgHead,
						getHeadUrl(2, 2, getItem(position).getUid()));
			}
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
	public void handleHttpResponse(String response, int requestId,
			InputStream is) {

	}

	private void onLoad() {
		if (actionType == 0) {
			listComm.stopRefresh();
			// pullToRefreshView.onHeaderRefreshComplete();
		} else {
			listComm.stopLoadMore();
			// pullToRefreshView.onFooterRefreshComplete();
		}
	}

	public void pingGuanZhuClick(String follow) {
		sLogic.setDate(mHandler, httpUtil);
		showProcessDialog(sdismiss);
		if (Constant.HAS_FOLLOW.equals(follow)) {
			sLogic.sendUnFollowYuansuRequest(getUser().getRemarkToken(),
					remarkId);
		} else {
			sLogic.sendFollowYuansuRequest(getUser().getRemarkToken(), remarkId);
		}
	}

	DialogInterface.OnDismissListener sdismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			// httpUtil.getHttpClient().getConnectionManager().shutdown();
			sLogic.stopReqeust();
			onLoad();
		}
	};

	public void pingShareClick() {
		sLogic.setDate(mHandler, httpUtil);
		showProcessDialog(sdismiss);
		sLogic.sendShareRequest(getUser().getRemarkToken(), remarkId);
	}

	public static int UPDATEVIEW = 1;

	public void pingLunClick() {
		startActivityForResult(new Intent(getBaseContext(),
				CommentYuansuActivity.class).putExtra("remarkId", remarkId),
				UPDATEVIEW);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			requestYuansuComment(0);
		}
	}

	@Override
	public void handleMsg(Message msg) {
		onLoad();
		switch (msg.what) {
		case SuperLogic.YUANSU_COMMENT_SUCCESS_MSGWHAT: {
			updateView();
			break;
		}
		case SuperLogic.SHARE_SUCCESS_MSGWHAT: {
			showToast(getString(R.string.share_success));
			break;
		}
		case SuperLogic.FOLLOW_YUANSU_SUCCESS_MSGWHAT: {
			logic.ys.setFollowState(Constant.HAS_FOLLOW);
			showToast(getString(R.string.has_follow));
			followstate = Constant.HAS_FOLLOW;
			updateGuanzhuImg();
			updateView();
			break;
		}
		case SuperLogic.UNFOLLOW_YUANSU_SUCCESS_MSGWHAT: {
			logic.ys.setFollowState(Constant.UN_FOLLOW);
			showToast(getString(R.string.unfollow));
			followstate = Constant.UN_FOLLOW;
			updateGuanzhuImg();
			updateView();
			break;
		}
		}
		super.handleMsg(msg);
	}

	public void updateHeaderView(String type) {
		if (type.equals(Yuansu.Label.img.toString())) {
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
				bitmapUtilsContent.configDefaultBitmapMaxSize(getScreenW(),
						getScreenH());
			}
			bitmapUtilsContent.display(imgContent, content);
		} else {
			imgContent.setVisibility(View.GONE);
			text_word_content.setVisibility(View.VISIBLE);
			text_word_content.setText(content);
			if (text_word_content.getLineCount() <= 1) {
				text_word_content.setGravity(Gravity.CENTER);
			} else {
				text_word_content.setGravity(Gravity.LEFT
						| Gravity.CENTER_VERTICAL);
			}
		}
	}

	public int actionType = 0;
	DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			// httpUtil.getHttpClient().getConnectionManager().shutdown();
			logic.stopReqeust();
			onLoad();
		}
	};

	public void requestYuansuComment(int type) {
		actionType = type;
		httpUtil = new HttpUtils();
		logic.setDate(mHandler, httpUtil);
		showProcessDialog(dismiss);
		logic.sendYuansuCommentRequest(getUser().getRemarkToken(), remarkId,
				type);
	}

	public void updateGuanzhuImg() {
		if (followstate.equals(Constant.HAS_FOLLOW)) {
			imgGuanZhu
					.setBackgroundResource(android.R.drawable.ic_menu_close_clear_cancel);
		} else {
			imgGuanZhu.setBackgroundResource(android.R.drawable.ic_input_add);

		}
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
