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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.logic.PCenterLogic;
import com.yingqida.richplay.logic.ShareAndFollowLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.widget.PullToRefreshView;

public class PcFayanActivity extends SuperActivity implements
		OnItemClickListener {

	@ViewInject(R.id.pullToRefreshView)
	private PullToRefreshView pullToRefreshView;

	@ViewInject(R.id.listViewFy)
	private ListView listViewFy;

	@ViewInject(R.id.frameFy)
	private LinearLayout frameFy;

	private Adapter adapterFy;
	private ShareAndFollowLogic sLogic;
	private PCenterLogic pcLogic;
	private HttpUtils httpUtil;
	private BitmapUtils bitmapUtilsContent;
	private BitmapUtils bitmapUtilsHead;

	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	@Override
	public void handleHttpResponse(String response, int requestId, InputStream is) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	public void updateView() {

		if (null == adapterFy) {
			adapterFy = new Adapter();
			listViewFy.setAdapter(adapterFy);
			listViewFy.setOnItemClickListener(this);
			pullToRefreshView
					.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

						@Override
						public void onHeaderRefresh(PullToRefreshView view) {
							requestFayan(0);
						}
					});
			pullToRefreshView
					.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

						@Override
						public void onFooterRefresh(PullToRefreshView view) {
							requestFayan(1);

						}
					});
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
		pcLogic.sendFayanRequest2(getUser().getRemarkToken(), type, null);
	}

	public void pingGuanZhuClick() {
		showToast("添加关注");
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(getBaseContext(), YuansuInfoActivity.class)
				.putExtra("content",
						adapterFy.getItem(position).getRemarkContent())
				.putExtra("remarkId", adapterFy.getItem(position).getId())
				.putExtra("label", adapterFy.getItem(position).getLabel()));

	}

	@OnClick(R.id.frameFy)
	public void frameFyClick(View view) {
		requestFayan(0);
	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.PCENTER_FAYAN_SUCCESS_MSGWHAT: {
			updateView();
			break;
		}
		case SuperLogic.PCENTER_BEIGUANZHU_SUCCESS_MSGWHAT: {
			break;
		}
		case SuperLogic.SHARE_SUCCESS_MSGWHAT: {
			showToast(getString(R.string.share_success));
			break;
		}
		}
		onLoad();
		super.handleMsg(msg);
	}

	private void onLoad() {
		if (actionType == 0)
			pullToRefreshView.onHeaderRefreshComplete();
		pullToRefreshView.onFooterRefreshComplete();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 2) {
			requestFayan(0);
		}
	}
}
