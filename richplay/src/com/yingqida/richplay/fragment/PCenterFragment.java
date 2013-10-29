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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import com.yingqida.richplay.widget.PullToRefreshView;

public class PCenterFragment extends SuperFragment implements
		OnItemClickListener {

	private static PCenterFragment ins;
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

	public int index = 0;

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
			pullToRefreshView.setEnablePullTorefresh(false);
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

	View convertView;
	private BitmapUtils bitmapUtilsContent;
	private BitmapUtils bitmapUtilsHead;

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.pcenter_layout, null);
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
		requestFayan(0);
		return convertView;
	}

	@Override
	public void onClick(View v) {

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
		pcLogic.setDate(fHandler, httpUtil);
		((SuperActivityForFragment) getActivity()).showProcessDialog(dismiss);
		pcLogic.sendFayanRequest1(getUser().getRemarkToken(), type);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		startActivity(new Intent(getActivity().getBaseContext(),
				YuansuInfoActivity.class)
				.putExtra("content",
						adapterFy.getItem(position).getRemarkContent())
				.putExtra("remarkId", adapterFy.getItem(position).getId())
				.putExtra("label", adapterFy.getItem(position).getLabel()));

	}

	@OnClick(R.id.frameFy)
	public void frameFyClick(View view) {
		startActivity(new Intent(getActivity().getBaseContext(),
				PcFayanActivity.class));
	}

	@OnClick(R.id.frameBgz)
	public void frameBgzClick(View view) {
		startActivity(new Intent(getActivity().getBaseContext(),
				PcBeiGuanzhuActivity.class));
	}

	@OnClick(R.id.frameGzYs)
	public void frameGzYsClick(View view) {
		startActivity(new Intent(getActivity().getBaseContext(),
				PcGuanzhuYsActivity.class));
	}

	@OnClick(R.id.frameGzYh)
	public void frameGzYhClick(View view) {
		startActivity(new Intent(getActivity().getBaseContext(),
				PcGuanzhuYhActivity.class));
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
