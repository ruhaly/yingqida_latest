package com.yingqida.richplay.activity;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
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
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.logic.PCenterLogic;
import com.yingqida.richplay.logic.ShareAndFollowLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.logic.UserSearchLogic;
import com.yingqida.richplay.widget.PullToRefreshView;

public class PcBeiGuanzhuActivity extends SuperActivity {

	@ViewInject(R.id.pullToRefreshViewYh)
	private PullToRefreshView pullToRefreshViewYh;

	private BitmapUtils bitmapUtilsYh;

	@ViewInject(R.id.gridviewYh)
	private GridView gridviewYh;

	private PCenterLogic pcLogic;

	private HttpUtils httpUtil;

	private YhAdapter adapterYh;
	private ShareAndFollowLogic sLogic;
	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	@Override
	public void handleHttpResponse(String response, int requestId) {

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
		pcLogic.sendBeiGuanzhuRequest(getUser().getRemarkToken(), getUser()
				.getUid(), type);
	}

	DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			httpUtil.getHttpClient().getConnectionManager().shutdown();
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

	private void onLoad() {

		if (actionType == 0) {
			pullToRefreshViewYh.onHeaderRefreshComplete();
		} else {
			pullToRefreshViewYh.onFooterRefreshComplete();
		}
	}

	public void updateView() {
		if (null == adapterYh) {
			adapterYh = new YhAdapter(pcLogic.beiguanzhuYhList,
					getBaseContext(), bitmapUtilsYh);
			gridviewYh.setAdapter(adapterYh);
			pullToRefreshViewYh
					.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

						@Override
						public void onHeaderRefresh(PullToRefreshView view) {
							refreshYonghu(0);
						}
					});
			pullToRefreshViewYh
					.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

						@Override
						public void onFooterRefresh(PullToRefreshView view) {
							refreshYonghu(1);

						}
					});
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
			holder.tvName.setText(getItem(position).getName());
			bitmapUtils
					.display(
							holder.imgHeader,
							"http://g.hiphotos.baidu.com/album/w%3D2048/sign=10e27d76adaf2eddd4f14ee9b92800e9/bd315c6034a85edfeb5afd5348540923dc5475ef.jpg");
			return convertView;
		}

		class Holder {
			ImageView imgHeader;
			TextView tvFy;
			TextView tvName;
			TextView tvGuanzhu;
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
}
