package com.yingqida.richplay.activity;

import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.baseapi.Constant;
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.logic.PCenterLogic;
import com.yingqida.richplay.logic.ShareAndFollowLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.pubuliu.XListView;
import com.yingqida.richplay.pubuliu.XListView.IXListViewListener;

public class PcBeiGuanzhuActivity extends SuperActivity implements
		IXListViewListener, OnItemClickListener {

	// @ViewInject(R.id.pullToRefreshViewYh)
	// private PullToRefreshView pullToRefreshViewYh;

	private BitmapUtils bitmapUtilsYh;

	@ViewInject(R.id.gridviewYh)
	private XListView gridviewYh;

	private PCenterLogic pcLogic;

	private HttpUtils httpUtil;

	private YhAdapter adapterYh;
	private ShareAndFollowLogic sLogic;
	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	public String uid;

	@Override
	public void handleHttpResponse(String response, int requestId,
			InputStream is) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void initData() {
		pcLogic = PCenterLogic.getInstance();
		sLogic = ShareAndFollowLogic.getInstance();
	}

	@Override
	public void initLayout(Bundle paramBundle) {
		setContentView(R.layout.pc_beiguanzhu_layout);
		ViewUtils.inject(this);
		btnToggle.setVisibility(View.GONE);
		uid = getIntent().getExtras().getString("uid");
		bitmapUtilsYh = new BitmapUtils(this);
		bitmapUtilsYh.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtilsYh.configDefaultLoadFailedImage(R.drawable.failed);
		bitmapUtilsYh.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		updateView();
		refreshYonghu(0);
	}

	@Override
	public void clearData() {

	}

	/**
	 * 
	 * Function:获取元素
	 * 
	 * @author ruhaly DateTime 2013-10-17 上午10:34:17
	 */
	public void refreshYonghu(int type) {
		actionType = type;
		httpUtil = new HttpUtils();
		pcLogic.setDate(mHandler, httpUtil);
		showProcessDialog(dismiss);
		pcLogic.sendBeiGuanzhuRequest(getUser().getRemarkToken(), uid, type);
	}

	DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			pcLogic.stopReqeust();
		}
	};

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.PCENTER_BEIGUANZHU_SUCCESS_MSGWHAT: {
			updateView();
			onLoad();
			break;
		}
		case SuperLogic.UNFOLLOW_USER_SUCCESS_MSGWHAT: {
			if (pcLogic.beiguanzhuYhList.size() > 0) {
				pcLogic.beiguanzhuYhList.get(temp).setStateGuanzhu(
						Constant.UN_FOLLOW);
			}
			showToast(getString(R.string.unfollow));
			updateView();
			break;
		}
		case SuperLogic.FOLLOW_USER_SUCCESS_MSGWHAT: {
			if (pcLogic.beiguanzhuYhList.size() > 0) {
				pcLogic.beiguanzhuYhList.get(temp).setStateGuanzhu(
						Constant.HAS_FOLLOW);
			}
			showToast(getString(R.string.has_follow));
			updateView();
			break;
		}
		case SuperLogic.SHARE_SUCCESS_MSGWHAT: {
			showToast(getString(R.string.share_success));
			break;
		}
		}
		super.handleMsg(msg);
	}

	public int actionType = 0;

	public void updateView() {
		if (null == adapterYh) {
			adapterYh = new YhAdapter(pcLogic.beiguanzhuYhList,
					getBaseContext(), bitmapUtilsYh);
			gridviewYh.setAdapter(adapterYh);
			gridviewYh.setXListViewListener(this);
			gridviewYh.setPullLoadEnable(true);
			gridviewYh.setOnItemClickListener(this);
			// pullToRefreshViewYh
			// .setOnHeaderRefreshListener(new
			// PullToRefreshView.OnHeaderRefreshListener() {
			//
			// @Override
			// public void onHeaderRefresh(PullToRefreshView view) {
			// refreshYonghu(0);
			// }
			// });
			// pullToRefreshViewYh
			// .setOnFooterRefreshListener(new
			// PullToRefreshView.OnFooterRefreshListener() {
			//
			// @Override
			// public void onFooterRefresh(PullToRefreshView view) {
			// refreshYonghu(1);
			//
			// }
			// });
		} else {
			adapterYh.notifyDataSetChanged();
		}

	}

	// yonghu 适配器
	class YhAdapter extends BaseAdapter {
		private List<User> list;
		private Context context;
		private BitmapUtils bitmapUtils;

		public YhAdapter(List<User> list, Context context,
				BitmapUtils bitmapUtils) {
			this.list = list;
			this.context = context;
			this.bitmapUtils = bitmapUtils;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public User getItem(int arg0) {
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
				convertView = LayoutInflater.from(context).inflate(
						R.layout.user_item_layout, null);
				holder.imgHeader = (ImageView) convertView
						.findViewById(R.id.imgHeader);
				holder.tvFy = (TextView) convertView.findViewById(R.id.tvFy);
				holder.tvName = (TextView) convertView
						.findViewById(R.id.tvName);
				holder.tvGuanzhu = (TextView) convertView
						.findViewById(R.id.tvGuanzhu);
				holder.imgHead = (ImageView) convertView
						.findViewById(R.id.imgHead);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			String followState = getItem(position).getStateGuanzhu();
			if (Constant.HAS_FOLLOW.equals(followState)) {
				holder.tvGuanzhu.setText(getString(R.string.unfollow));
			} else {
				holder.tvGuanzhu.setText(getString(R.string.follow));
			}
			holder.tvGuanzhu.setTag(position);
			holder.tvGuanzhu.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int p = (Integer) v.getTag();
					temp = p;
					String followState = getItem(p).getStateGuanzhu();
					if (followState.equals(Constant.HAS_FOLLOW)) {
						unFollowUser(getItem(p).uid);
					} else {
						followUser(getItem(p).uid);
					}
				}
			});
			int w = (getScreenW() - 3) / 2;
			holder.imgHead.setLayoutParams(new FrameLayout.LayoutParams(w, w));
			holder.imgHead.setScaleType(ScaleType.CENTER_INSIDE);
			holder.tvName.setText(getItem(position).getName());
			if (getItem(position).getIs_avatar().equals("true")) {
				bitmapUtils.display(holder.imgHead,
						getHeadUrl(1, 2, getItem(position).getUid()));
			} else {
				bitmapUtils.display(holder.imgHead,
						getHeadUrl(2, 2, getItem(position).getUid()));
			}
			return convertView;
		}

		class Holder {
			ImageView imgHeader;
			TextView tvFy;
			TextView tvName;
			TextView tvGuanzhu;
			ImageView imgHead;
		}
	}

	public int temp = 0;

	/**
	 * 
	 * Function:取消关注
	 * 
	 * @author ruhaly DateTime 2013-10-28 下午6:05:49
	 * @param uid
	 */
	public void unFollowUser(String uid) {
		httpUtil = new HttpUtils();
		sLogic.setDate(mHandler, httpUtil);
		showProcessDialog(dismiss);
		sLogic.sendUnFollowUserRequest(getUser().getRemarkToken(), uid);
	}

	public void followUser(String uid) {
		httpUtil = new HttpUtils();
		sLogic.setDate(mHandler, httpUtil);
		showProcessDialog(dismiss);
		sLogic.sendFollowUserRequest(getUser().getRemarkToken(), uid);
	}

	private void onLoad() {
		if (actionType == 0)
			gridviewYh.stopRefresh();
		gridviewYh.stopLoadMore();
	}

	@Override
	public void onRefresh() {
		refreshYonghu(0);
	}

	@Override
	public void onLoadMore() {
		refreshYonghu(1);
	}

	@Override
	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(getBaseContext(), PCenterActivity.class);
		intent.putExtra("uid", adapterYh.getItem(position - 1).getUid());
		startActivity(intent);
	}
}
