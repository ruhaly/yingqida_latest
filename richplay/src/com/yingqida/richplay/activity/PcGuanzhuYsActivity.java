package com.yingqida.richplay.activity;

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
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.logic.PCenterLogic;
import com.yingqida.richplay.logic.ShareAndFollowLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.widget.PullToRefreshView;

public class PcGuanzhuYsActivity extends SuperActivity implements
		OnItemClickListener {

	@ViewInject(R.id.pullToRefreshView)
	private PullToRefreshView pullToRefreshView;

	@ViewInject(R.id.listViewFy)
	private ListView listViewFy;

	private Adapter adapterGuanzhuYs;
	private ShareAndFollowLogic sLogic;
	private PCenterLogic pcLogic;
	private HttpUtils httpUtil;
	private BitmapUtils bitmapUtilsContent;
	private BitmapUtils bitmapUtilsHead;
	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	@Override
	public void handleHttpResponse(String response, int requestId) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	public void updateView() {

		if (null == adapterGuanzhuYs) {
			adapterGuanzhuYs = new Adapter();
			listViewFy.setAdapter(adapterGuanzhuYs);
			listViewFy.setOnItemClickListener(this);
			pullToRefreshView
					.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

						@Override
						public void onHeaderRefresh(PullToRefreshView view) {
							requestGuanzhuYs(0);
						}
					});
			pullToRefreshView
					.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

						@Override
						public void onFooterRefresh(PullToRefreshView view) {
							requestGuanzhuYs(1);

						}
					});
		} else {
			adapterGuanzhuYs.notifyDataSetChanged();
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
		requestGuanzhuYs(0);
	}

	@Override
	public void clearData() {

	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return pcLogic.guanzhuYsList.size();
		}

		@Override
		public Yuansu getItem(int arg0) {
			return pcLogic.guanzhuYsList.get(arg0);
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

				holder.frameU = (LinearLayout) convertView
						.findViewById(R.id.frameU);
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
			holder.imgGuanZhu.setTag(position);
			holder.imgGuanZhu
					.setBackgroundResource(android.R.drawable.ic_menu_close_clear_cancel);
			holder.imgGuanZhu.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int p = (Integer) v.getTag();
					temp = p;
					pingGuanZhuClick(p);
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
					holder.content.setTextAppearance(getBaseContext(),
							android.R.attr.textAppearanceLarge);
				} else {
					holder.content.setGravity(Gravity.LEFT
							| Gravity.CENTER_VERTICAL);
					holder.content.setTextAppearance(getBaseContext(),
							android.R.attr.textAppearanceMedium);
				}
			}
			holder.frameU.setVisibility(View.GONE);
			return convertView;
		}

		class Holder {
			ImageView imgGuanZhu;
			ImageView imgShare;
			ImageView imgPingLun;
			ImageView imgContent;
			TextView content;
			LinearLayout frameU;
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

	public int temp;

	public void requestGuanzhuYs(int type) {
		actionType = type;
		httpUtil = new HttpUtils();
		pcLogic.setDate(mHandler, httpUtil);
		showProcessDialog(dismiss);
		pcLogic.sendGuanzhuYsRequest(getUser().getRemarkToken(), type);
	}

	public void pingGuanZhuClick(int p) {
		sLogic.setDate(mHandler, httpUtil);
		showProcessDialog(sdismiss);
		sLogic.sendUnFollowYuansuRequest(getUser().getRemarkToken(),
				adapterGuanzhuYs.getItem(p).getId());
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
		sLogic.sendShareRequest(getUser().getRemarkToken(), adapterGuanzhuYs
				.getItem(p).getId());
	}

	public void pingLunClick(int p) {
		startActivityForResult(new Intent(getBaseContext(),
				CommentYuansuActivity.class).putExtra("remarkId",
				adapterGuanzhuYs.getItem(p).getId()), 2);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(getBaseContext(), YuansuInfoActivity.class)
				.putExtra("content",
						adapterGuanzhuYs.getItem(position).getRemarkContent())
				.putExtra("remarkId",
						adapterGuanzhuYs.getItem(position).getId())
				.putExtra("label",
						adapterGuanzhuYs.getItem(position).getLabel()));

	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.PCENTER_GUANZHUYS_SUCCESS_MSGWHAT: {
			updateView();
			break;
		}
		case SuperLogic.SHARE_SUCCESS_MSGWHAT: {
			showToast(getString(R.string.share_success));
			break;
		}
		case SuperLogic.UNFOLLOW_YUANSU_SUCCESS_MSGWHAT: {
			pcLogic.guanzhuYsList.remove(temp);
			showToast(getString(R.string.unfollow));
			updateView();
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
			requestGuanzhuYs(0);
		}
	}
}
