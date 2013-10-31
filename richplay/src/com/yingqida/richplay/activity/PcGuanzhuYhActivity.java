package com.yingqida.richplay.activity;

import java.io.InputStream;
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
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.logic.PCenterLogic;
import com.yingqida.richplay.logic.ShareAndFollowLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.logic.UserLogic;
import com.yingqida.richplay.widget.PullToRefreshView;

public class PcGuanzhuYhActivity extends SuperActivity {

	@ViewInject(R.id.pullToRefreshViewYh)
	private PullToRefreshView pullToRefreshViewYh;

	private BitmapUtils bitmapUtilsYh;

	@ViewInject(R.id.gridviewYh)
	private GridView gridviewYh;

	private PCenterLogic pcLogic;

	private HttpUtils httpUtil;

	private YhAdapter adapterYh;
	private UserLogic uLogic;
	private ShareAndFollowLogic sLogic;
	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	@Override
	public void handleHttpResponse(String response, int requestId, InputStream is) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void initData() {
		pcLogic = PCenterLogic.getInstance();
		uLogic = UserLogic.getInstance();
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
		pcLogic.sendGuanzhuYhRequest(getUser().getRemarkToken(), type);
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
		case SuperLogic.PCENTER_GUANZHUYH_SUCCESS_MSGWHAT: {
			updateView();
			onLoad();
			break;
		}
		case SuperLogic.UNFOLLOW_USER_SUCCESS_MSGWHAT: {
			pcLogic.guanzhuYhList.remove(temp);
			showToast(getString(R.string.unfollow));
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
			adapterYh = new YhAdapter(pcLogic.guanzhuYhList, getBaseContext(),
					bitmapUtilsYh);
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

			holder.tvGuanzhu.setText(getString(R.string.unfollow));
			holder.tvGuanzhu.setTag(position);
			holder.tvGuanzhu.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int p = (Integer) v.getTag();
					temp = p;
					unFollowUser(getItem(p).uid);
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

	public void unFollowUser(String uid) {
		httpUtil = new HttpUtils();
		sLogic.setDate(mHandler, httpUtil);
		showProcessDialog(dismiss);
		sLogic.sendUnFollowUserRequest(getUser().getRemarkToken(), uid);
	}
}
