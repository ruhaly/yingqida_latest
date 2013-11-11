package com.yingqida.richplay.fragment;

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
import android.widget.TextView;

import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.CommentYuansuActivity;
import com.yingqida.richplay.activity.PCenterActivity;
import com.yingqida.richplay.activity.YuansuInfoActivity;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;
import com.yingqida.richplay.baseapi.Constant;
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.logic.PageHomeLogic;
import com.yingqida.richplay.logic.ShareAndFollowLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.pubuliu.XListView;
import com.yingqida.richplay.pubuliu.XListView.IXListViewListener;

public class PageHomeFragment extends SuperFragment implements
		IXListViewListener, OnItemClickListener {

	// @ViewInject(R.id.pullToRefreshView)
	// private PullToRefreshView pullToRefreshView;

	// @ViewInject(R.id.listViewYs)
	// private ListView listViewYs;

	@ViewInject(R.id.listViewYs)
	private XListView listViewYs;

	private Adapter adapter;

	private PageHomeLogic logic;

	private HttpUtils httpUtil;

	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	private static PageHomeFragment ins;

	private ShareAndFollowLogic sLogic;

	public synchronized static PageHomeFragment getIns() {
		if (null == ins) {
			ins = new PageHomeFragment();
		}
		return ins;
	}

	@OnClick(R.id.btnToggle)
	public void btnToggleClick(View view) {
		((SuperActivityForFragment) getActivity()).toggle();
	}

	@Override
	public void updateView() {
		if (null == adapter) {
			adapter = new Adapter();
			listViewYs.setAdapter(adapter);
			listViewYs.setXListViewListener(this);
			listViewYs.setPullLoadEnable(false);
			listViewYs.setOnItemClickListener(this);
			// pullToRefreshView
			// .setOnHeaderRefreshListener(new
			// PullToRefreshView.OnHeaderRefreshListener() {
			//
			// @Override
			// public void onHeaderRefresh(PullToRefreshView view) {
			// requestYuansu(0);
			// }
			// });
			// pullToRefreshView
			// .setOnFooterRefreshListener(new
			// PullToRefreshView.OnFooterRefreshListener() {
			//
			// @Override
			// public void onFooterRefresh(PullToRefreshView view) {
			// requestYuansu(1);
			//
			// }
			// });
		} else {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void initData() {
		logic = PageHomeLogic.getInstance();
		sLogic = ShareAndFollowLogic.getInstance();
	}

	View convertView;
	private BitmapUtils bitmapUtilsContent;
	private BitmapUtils bitmapUtilsHead;

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (convertView == null)
			convertView = inflater.inflate(R.layout.page_home_layout, null);
		ViewUtils.inject(this, convertView);
		bitmapUtilsContent = new BitmapUtils(getActivity());
		bitmapUtilsContent.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtilsContent
				.configDefaultLoadFailedImage(R.drawable.list_item_bg);
		bitmapUtilsContent.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		bitmapUtilsHead = new BitmapUtils(getActivity());
		bitmapUtilsHead.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtilsHead.configDefaultLoadFailedImage(R.drawable.failed);
		bitmapUtilsHead.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		updateView();
		requestYuansu(0);
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
			holder.imgGuanZhu.setTag(position);
			String follow = getItem(position).getFollowState();
			if (Constant.HAS_FOLLOW.equals(follow)) {
				holder.imgGuanZhu
						.setBackgroundResource(android.R.drawable.ic_menu_close_clear_cancel);
			} else {
				holder.imgGuanZhu
						.setBackgroundResource(android.R.drawable.ic_input_add);
			}
			holder.imgGuanZhu.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int p = (Integer) v.getTag();
					String follow = getItem(p).getFollowState();
					temp = p;
					pingGuanZhuClick(p, follow);
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

			if (getItem(position).getUser().getIs_avatar().equals("true")) {
				bitmapUtilsHead.display(holder.imgHead,
						getHeadUrl(1, 2, getItem(position).getUser().getUid()));
			} else {
				bitmapUtilsHead.display(holder.imgHead,
						getHeadUrl(2, 2, getItem(position).getUser().getUid()));
			}
			holder.imgHead.setTag(position);
			holder.imgHead.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int p = (Integer) v.getTag();
					Intent intent = new Intent(getActivity().getBaseContext(),
							PCenterActivity.class);
					intent.putExtra("uid", getItem(p).getUser().getUid());
					startActivity(intent);

				}
			});
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
			ImageView imgHead;
			TextView content;
		}
	}

	DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			// httpUtil.getHttpClient().getConnectionManager().shutdown();
			logic.realseYuanSuRequest();
			onLoad();
		}
	};

	/**
	 * 
	 * Function:获取元素
	 * 
	 * @author ruhaly DateTime 2013-10-17 上午10:34:17
	 */
	public void requestYuansu(int type) {
		actionType = type;
		httpUtil = new HttpUtils();
		logic.setDate(fHandler, httpUtil);
		((SuperActivityForFragment) getActivity()).showProcessDialog(dismiss);
		logic.sendPageHomeYuanSuRequest(getUser().getUid(), getUser()
				.getRemarkToken(), type);
	}

	public int actionType = 0;

	private void onLoad() {

		if (actionType == 0)
			listViewYs.stopRefresh();
		listViewYs.stopLoadMore();
		// pullToRefreshView.onHeaderRefreshComplete();
		// pullToRefreshView.onFooterRefreshComplete();
	}

	int temp;

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.HOME_PAGE_YUANSU_SUCCESS_MSGWHAT: {
			updateView();
			break;
		}
		case SuperLogic.SHARE_SUCCESS_MSGWHAT: {
			showToast(getString(R.string.share_success));
			break;
		}
		case SuperLogic.FOLLOW_YUANSU_SUCCESS_MSGWHAT: {
			if (logic.list.size() > 0) {
				logic.list.get(temp).setFollowState(Constant.HAS_FOLLOW);
			}
			showToast(getString(R.string.has_follow));
			updateView();
			break;
		}

		case SuperLogic.UNFOLLOW_YUANSU_SUCCESS_MSGWHAT: {
			if (logic.list.size() > 0) {
				logic.list.get(temp).setFollowState(Constant.UN_FOLLOW);
			}
			showToast(getString(R.string.unfollow));
			updateView();
			break;
		}
		case SuperLogic.ERROR_FOLLOW_YUANSU_DONT_EXIST: {
			if (logic.list.size() > 0) {
				logic.list.get(temp).setFollowState(Constant.UN_FOLLOW);
			}
			showToast(getString(R.string.unfollow));
			updateView();
			break;
		}
		case SuperLogic.FOLLOW_YUANSU_ERROR_EXIST_MSGWHAT: {
			if (logic.list.size() > 0) {
				logic.list.get(temp).setFollowState(Constant.HAS_FOLLOW);
			}
			showToast(getString(R.string.has_follow));
			updateView();

			break;
		}
		}
		onLoad();
		super.handleMsg(msg);
	}

	public void pingGuanZhuClick(int p, String follow) {
		sLogic.setDate(fHandler, httpUtil);
		showProcessDialog(sdismiss);
		if (Constant.HAS_FOLLOW.equals(follow)) {
			sLogic.sendUnFollowYuansuRequest(getUser().getRemarkToken(),
					adapter.getItem(p).getId());
		} else {
			sLogic.sendFollowYuansuRequest(getUser().getRemarkToken(), adapter
					.getItem(p).getId());
		}
	}

	DialogInterface.OnDismissListener sdismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			// httpUtil.getHttpClient().getConnectionManager().shutdown();
			sLogic.stopReqeust();
		}
	};

	public void pingShareClick(int p) {
		sLogic.setDate(fHandler, httpUtil);
		showProcessDialog(sdismiss);
		sLogic.sendShareRequest(getUser().getRemarkToken(), adapter.getItem(p)
				.getId());
	}

	public void pingLunClick(int p) {
		startActivityForResult(new Intent(getActivity().getBaseContext(),
				CommentYuansuActivity.class).putExtra("remarkId", adapter
				.getItem(p).getId()), 2);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 2) {
			requestYuansu(0);
		}
	}

	public void clearData() {
		logic.clear();
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onRefresh() {
		requestYuansu(0);
	}

	@Override
	public void onLoadMore() {
		requestYuansu(1);
	}

	@Override
	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
			long id) {

		startActivity(new Intent(getActivity().getBaseContext(),
				YuansuInfoActivity.class)
				.putExtra("content",
						adapter.getItem(position - 1).getRemarkContent())
				.putExtra("remarkId", adapter.getItem(position - 1).getId())
				.putExtra("label", adapter.getItem(position - 1).getLabel())
				.putExtra("followstate",
						adapter.getItem(position - 1).getFollowState()));

	}
}
