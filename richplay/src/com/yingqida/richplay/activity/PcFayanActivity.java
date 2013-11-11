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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.baseapi.Constant;
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.logic.PCenterLogic;
import com.yingqida.richplay.logic.ShareAndFollowLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.pubuliu.XListView;
import com.yingqida.richplay.pubuliu.XListView.IXListViewListener;

public class PcFayanActivity extends SuperActivity implements
		IXListViewListener, OnItemClickListener {

	// @ViewInject(R.id.pullToRefreshView)
	// private PullToRefreshView pullToRefreshView;

	@ViewInject(R.id.listViewFy)
	private XListView listViewFy;

	@ViewInject(R.id.frameFy)
	private LinearLayout frameFy;

	private Adapter adapterFy;
	private ShareAndFollowLogic sLogic;
	private PCenterLogic pcLogic;
	private HttpUtils httpUtil;
	private BitmapUtils bitmapUtilsContent;
	private BitmapUtils bitmapUtilsHead;

	public String uid;
	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	@Override
	public void handleHttpResponse(String response, int requestId,
			InputStream is) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	public void updateView() {

		if (null == adapterFy) {
			adapterFy = new Adapter();
			listViewFy.setAdapter(adapterFy);
			listViewFy.setXListViewListener(this);
			listViewFy.setPullLoadEnable(false);
			listViewFy.setOnItemClickListener(this);
			// listViewFy.setOnItemClickListener(this);
			// pullToRefreshView
			// .setOnHeaderRefreshListener(new
			// PullToRefreshView.OnHeaderRefreshListener() {
			//
			// @Override
			// public void onHeaderRefresh(PullToRefreshView view) {
			// requestFayan(0);
			// }
			// });
			// pullToRefreshView
			// .setOnFooterRefreshListener(new
			// PullToRefreshView.OnFooterRefreshListener() {
			//
			// @Override
			// public void onFooterRefresh(PullToRefreshView view) {
			// requestFayan(1);
			//
			// }
			// });
		} else {
			adapterFy.notifyDataSetChanged();
		}

	}

	@Override
	public void initData() {
		pcLogic = PCenterLogic.getInstance();
		sLogic = ShareAndFollowLogic.getInstance();
	}

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.pc_fayan_layout);
		ViewUtils.inject(this);
		uid = getIntent().getExtras().getString("uid");
		btnToggle.setVisibility(View.GONE);
		bitmapUtilsContent = new BitmapUtils(this);
		bitmapUtilsContent.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtilsContent
				.configDefaultLoadFailedImage(R.drawable.list_item_bg);
		bitmapUtilsContent.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		bitmapUtilsHead = new BitmapUtils(this);
		bitmapUtilsHead.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.failed);
		bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		updateView();
		requestFayan(0);
	}

	@Override
	public void clearData() {

	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return pcLogic.fyList2.size();
		}

		@Override
		public Yuansu getItem(int arg0) {
			return pcLogic.fyList2.get(arg0);
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
				holder.imgHead = (ImageView) convertView
						.findViewById(R.id.imgHead);
				holder.content = (TextView) convertView
						.findViewById(R.id.content);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			if (getItem(position).getFollowState().equals(Constant.HAS_FOLLOW)) {
				holder.imgGuanZhu
						.setBackgroundResource(android.R.drawable.ic_menu_close_clear_cancel);
			} else {
				holder.imgGuanZhu
						.setBackgroundResource(android.R.drawable.ic_input_add);

			}
			holder.imgGuanZhu.setTag(position);
			holder.imgGuanZhu.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int p = (Integer) v.getTag();
					temp = p;
					pingGuanZhuClick(getItem(p).getFollowState(), getItem(p)
							.getId());
				}
			});
			holder.imgShare.setTag(position);
			holder.imgShare.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int p = (Integer) v.getTag();
					pingShareClick(p);
				}
			});
			holder.imgPingLun.setTag(position);
			holder.imgPingLun.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int p = (Integer) v.getTag();
					pingLunClick(p);
				}
			});

			if (getItem(position).getLabel()
					.equals(Yuansu.Label.img.toString())) {
				holder.content.setVisibility(View.GONE);
				holder.imgContent.setVisibility(View.VISIBLE);
				holder.imgContent.setMaxWidth(getScreenW());
				bitmapUtilsContent.display(holder.imgContent, getItem(position)
						.getRemarkContent());
			} else {
				holder.content.setVisibility(View.VISIBLE);
				holder.imgContent.setVisibility(View.GONE);
				holder.content.setText(getItem(position).getRemarkContent());
				if (holder.content.getLineCount() <= 1) {
					holder.content.setGravity(Gravity.CENTER);
				} else {
					holder.content.setGravity(Gravity.LEFT
							| Gravity.CENTER_VERTICAL);
				}
			}

			holder.tvName.setText(getItem(position).getUser().getName());
			holder.tvCommentContent.setText(getItem(position).getUser()
					.getComment_content());
			if (getItem(position).getUser().getIs_avatar().equals("true")) {
				bitmapUtilsHead.display(holder.imgHead,
						getHeadUrl(1, 2, getItem(position).getUser().getUid()));
			} else {
				bitmapUtilsHead.display(holder.imgHead,
						getHeadUrl(2, 2, getItem(position).getUser().getUid()));
			}
			return convertView;
		}

		class Holder {
			TextView tvName;
			TextView tvCommentContent;
			ImageView imgGuanZhu;
			ImageView imgShare;
			ImageView imgPingLun;
			ImageView imgContent;
			ImageView imgHead;
			TextView content;
		}
	}

	public int actionType = 0;
	DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			onLoad();
			pcLogic.stopReqeust();
		}
	};

	public void requestFayan(int type) {
		actionType = type;
		httpUtil = new HttpUtils();
		pcLogic.setDate(mHandler, httpUtil);
		showProcessDialog(dismiss);
		pcLogic.sendFayanRequest2(getUser().getRemarkToken(), type, uid);
	}

	public void pingGuanZhuClick(String follow, String remarkId) {
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
			onLoad();
			sLogic.stopReqeust();
		}
	};

	public void pingShareClick(int p) {
		sLogic.setDate(mHandler, httpUtil);
		showProcessDialog(sdismiss);
		sLogic.sendShareRequest(getUser().getRemarkToken(), adapterFy
				.getItem(p).getId());
	}

	public void pingLunClick(int p) {
		startActivityForResult(new Intent(getBaseContext(),
				CommentYuansuActivity.class).putExtra("remarkId", adapterFy
				.getItem(p).getId()), 2);
	}

	@OnClick(R.id.frameFy)
	public void frameFyClick(View view) {
		requestFayan(0);
	}

	int temp;

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.PCENTER_FAYAN_SUCCESS_MSGWHAT: {
			updateView();
			break;
		}
		case SuperLogic.SHARE_SUCCESS_MSGWHAT: {
			showToast(getString(R.string.share_success));
			break;
		}
		case SuperLogic.FOLLOW_YUANSU_SUCCESS_MSGWHAT: {
			if (pcLogic.fyList2.size() > 0) {
				pcLogic.fyList2.get(temp).setFollowState(Constant.HAS_FOLLOW);
			}
			showToast(getString(R.string.has_follow));
			updateView();
			break;
		}
		case SuperLogic.UNFOLLOW_YUANSU_SUCCESS_MSGWHAT: {
			if (pcLogic.fyList2.size() > 0) {
				pcLogic.fyList2.get(temp).setFollowState(Constant.HAS_FOLLOW);
			}
			showToast(getString(R.string.unfollow));
			updateView();
			break;
		}
		}
		onLoad();
		super.handleMsg(msg);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 2) {
			requestFayan(0);
		}
	}

	@Override
	public void onClick(View v) {

	}

	private void onLoad() {
		if (actionType == 0)
			listViewFy.stopRefresh();
		listViewFy.stopLoadMore();
	}

	@Override
	public void onRefresh() {
		requestFayan(0);
	}

	@Override
	public void onLoadMore() {
		requestFayan(1);
	}

	@Override
	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(getBaseContext(), YuansuInfoActivity.class)
				.putExtra("content",
						adapterFy.getItem(position - 1).getRemarkContent())
				.putExtra("remarkId", adapterFy.getItem(position - 1).getId())
				.putExtra("label", adapterFy.getItem(position - 1).getLabel())
				.putExtra("followstate",
						adapterFy.getItem(position - 1).getFollowState()));

	}
}
