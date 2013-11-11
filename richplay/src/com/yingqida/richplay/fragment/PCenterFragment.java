package com.yingqida.richplay.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.CommentYuansuActivity;
import com.yingqida.richplay.activity.PcBeiGuanzhuActivity;
import com.yingqida.richplay.activity.PcFayanActivity;
import com.yingqida.richplay.activity.PcGuanzhuYhActivity;
import com.yingqida.richplay.activity.PcGuanzhuYsActivity;
import com.yingqida.richplay.activity.YuansuInfoActivity;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;
import com.yingqida.richplay.baseapi.Constant;
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.logic.PCenterLogic;
import com.yingqida.richplay.logic.ShareAndFollowLogic;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.pubuliu.XListView;
import com.yingqida.richplay.pubuliu.XListView.IXListViewListener;

public class PCenterFragment extends SuperFragment implements
		IXListViewListener, OnItemClickListener, OnClickListener {

	private static PCenterFragment ins;
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
	private HttpUtils httpUtil2;

	public int index = 0;
	public TextView tvFy;

	public TextView tvFu;

	public TextView tvBgz;

	public TextView tvFys;

	public TextView tvName;

	public TextView tvSummary;

	public ImageView imgHeader;

	public Button btnGuanzhu;

	public synchronized static PCenterFragment getIns() {
		if (null == ins) {
			ins = new PCenterFragment();
		}
		return ins;
	}

	@OnClick(R.id.btnToggle)
	public void btnToggleClick(View view) {
		((SuperActivityForFragment) getActivity()).toggle();
	}

	@Override
	public void updateView() {

		if (null == adapterFy) {
			adapterFy = new Adapter();
			listViewFy.setAdapter(adapterFy);
			listViewFy.setOnItemClickListener(this);
			listViewFy.setPullLoadEnable(false);
			// pullToRefreshView.setEnablePullTorefresh(true);
			// pullToRefreshView
			// .setOnHeaderRefreshListener(new
			// PullToRefreshView.OnHeaderRefreshListener() {
			//
			// @Override
			// public void onHeaderRefresh(PullToRefreshView view) {
			// requestFayan(0);
			// requestCount();
			// }
			// });
			// pullToRefreshView
			// .setOnFooterRefreshListener(new
			// PullToRefreshView.OnFooterRefreshListener() {
			//
			// @Override
			// public void onFooterRefresh(PullToRefreshView view) {
			// requestFayan(1);
			// requestCount();
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

	View convertView;
	private BitmapUtils bitmapUtilsContent;
	private BitmapUtils bitmapUtilsHead;
	View headerView;

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.pcenter_layout, null);
		ViewUtils.inject(this, convertView);

		headerView = LayoutInflater.from(getActivity().getBaseContext())
				.inflate(R.layout.pcenter_top_layout, null);
		btnGuanzhu = (Button) headerView.findViewById(R.id.btnGuanzhu);
		btnGuanzhu.setVisibility(View.GONE);
		tvFy = (TextView) headerView.findViewById(R.id.tvFy);
		tvFu = (TextView) headerView.findViewById(R.id.tvFu);
		tvBgz = (TextView) headerView.findViewById(R.id.tvBgz);
		tvFys = (TextView) headerView.findViewById(R.id.tvFys);
		tvName = (TextView) headerView.findViewById(R.id.tvName);
		tvSummary = (TextView) headerView.findViewById(R.id.tvSummary);
		imgHeader = (ImageView) headerView.findViewById(R.id.imgHeader);
		listViewFy.addHeaderView(headerView);
		listViewFy.setXListViewListener(this);
		listViewFy.setPullLoadEnable(true);
		headerView.findViewById(R.id.frameFy).setOnClickListener(this);
		headerView.findViewById(R.id.frameBgz).setOnClickListener(this);
		headerView.findViewById(R.id.frameGzYs).setOnClickListener(this);
		headerView.findViewById(R.id.frameGzYh).setOnClickListener(this);

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
		requestFayan(0);
		requestCount();
		return convertView;
	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return pcLogic.fyList.size();
		}

		@Override
		public Yuansu getItem(int arg0) {
			return pcLogic.fyList.get(arg0);
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
		pcLogic.setDate(fHandler, httpUtil);
		((SuperActivityForFragment) getActivity()).showProcessDialog(dismiss);
		pcLogic.sendFayanRequest1(getUser().getRemarkToken(), type, null);
	}

	public int temp = 0;

	public void pingGuanZhuClick(int p, String follow) {
		sLogic.setDate(fHandler, httpUtil);
		showProcessDialog(sdismiss);
		if (Constant.HAS_FOLLOW.equals(follow)) {
			sLogic.sendUnFollowYuansuRequest(getUser().getRemarkToken(),
					adapterFy.getItem(p).getId());
		} else {
			sLogic.sendFollowYuansuRequest(getUser().getRemarkToken(),
					adapterFy.getItem(p).getId());
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
		sLogic.setDate(fHandler, httpUtil);
		showProcessDialog(sdismiss);
		sLogic.sendShareRequest(getUser().getRemarkToken(), adapterFy
				.getItem(p).getId());
	}

	public void pingLunClick(int p) {
		startActivityForResult(new Intent(getActivity().getBaseContext(),
				CommentYuansuActivity.class).putExtra("remarkId", adapterFy
				.getItem(p).getId()), 2);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.frameFy: {
			startActivity(new Intent(getActivity().getBaseContext(),
					PcFayanActivity.class).putExtra("uid", getUser().getUid()));
			break;
		}
		case R.id.frameBgz: {
			startActivity(new Intent(getActivity().getBaseContext(),
					PcBeiGuanzhuActivity.class).putExtra("uid", getUser()
					.getUid()));

			break;
		}
		case R.id.frameGzYs: {
			startActivity(new Intent(getActivity().getBaseContext(),
					PcGuanzhuYsActivity.class).putExtra("uid", getUser()
					.getUid()));

			break;
		}
		case R.id.frameGzYh: {
			startActivity(new Intent(getActivity().getBaseContext(),
					PcGuanzhuYhActivity.class).putExtra("uid", getUser()
					.getUid()));

			break;
		}

		default:
			break;
		}
	}

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
		case SuperLogic.PCENTER_BEIGUANZHU_SUCCESS_MSGWHAT: {
			break;
		}
		case SuperLogic.FOLLOW_YUANSU_SUCCESS_MSGWHAT: {
			if (pcLogic.fyList.size() > 0) {
				pcLogic.fyList.get(temp).setFollowState(Constant.HAS_FOLLOW);
			}
			showToast(getString(R.string.has_follow));
			updateView();
			break;
		}
		case SuperLogic.UNFOLLOW_YUANSU_SUCCESS_MSGWHAT: {
			if (pcLogic.fyList.size() > 0) {
				pcLogic.fyList.get(temp).setFollowState(Constant.UN_FOLLOW);
			}
			showToast(getString(R.string.unfollow));
			updateView();
			break;
		}
		case SuperLogic.ERROR_FOLLOW_YUANSU_DONT_EXIST: {
			if (pcLogic.fyList.size() > 0) {
				pcLogic.fyList.get(temp).setFollowState(Constant.UN_FOLLOW);
			}
			showToast(getString(R.string.unfollow));
			updateView();
			break;
		}
		case SuperLogic.FOLLOW_YUANSU_ERROR_EXIST_MSGWHAT: {
			if (pcLogic.fyList.size() > 0) {
				pcLogic.fyList.get(temp).setFollowState(Constant.HAS_FOLLOW);
			}
			showToast(getString(R.string.has_follow));
			updateView();

			break;
		}
		case SuperLogic.PCENTER_COUNT_SUCCESS_MSGWHAT: {
			updateCount();
			break;
		}
		}
		onLoad();
		super.handleMsg(msg);
	}

	public void updateCount() {
		tvFy.setText(pcLogic.comment_count);
		tvFu.setText(pcLogic.following_user_count);
		tvFys.setText(pcLogic.following_remark_count);
		tvBgz.setText(pcLogic.follower_count);
	}

	private void onLoad() {
		if (actionType == 0)
			listViewFy.stopRefresh();
		listViewFy.stopLoadMore();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 2) {
			requestFayan(0);
			requestCount();
		}
	}

	public void requestCount() {
		httpUtil2 = new HttpUtils();
		pcLogic.setDate(fHandler, httpUtil2);
		pcLogic.sendCountRequest(getUser().getRemarkToken(), getUser().getUid());
	}

	@Override
	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
			long id) {

		startActivity(new Intent(getActivity().getBaseContext(),
				YuansuInfoActivity.class)
				.putExtra("content",
						adapterFy.getItem(position - 1).getRemarkContent())
				.putExtra("remarkId", adapterFy.getItem(position - 1).getId())
				.putExtra("label", adapterFy.getItem(position - 1).getLabel())
				.putExtra("followstate",
						adapterFy.getItem(position - 1).getFollowState()));

	}

	@Override
	public void onRefresh() {
		requestFayan(0);
		requestCount();
	}

	@Override
	public void onLoadMore() {
		requestFayan(1);
		requestCount();
	}
}
