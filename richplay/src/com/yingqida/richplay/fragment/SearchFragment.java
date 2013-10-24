package com.yingqida.richplay.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
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
import com.yingqida.richplay.activity.common.SuperActivityForFragment;
import com.yingqida.richplay.baseapi.Constant;
import com.yingqida.richplay.baseapi.common.User;
import com.yingqida.richplay.entity.Yuansu;
import com.yingqida.richplay.logic.SuperLogic;
import com.yingqida.richplay.logic.UserSearchLogic;
import com.yingqida.richplay.logic.YuansuSearchLogic;
import com.yingqida.richplay.widget.PullToRefreshView;

@SuppressLint("ValidFragment")
public class SearchFragment extends SuperFragment {

	private YsAdapter adapterYs;

	private YhAdapter adapterYh;

	public SearchFragment() {

	}

	private static SearchFragment ins;

	public String keyword = "";

	@ViewInject(R.id.pullToRefreshViewYs)
	private PullToRefreshView pullToRefreshViewYs;

	@ViewInject(R.id.pullToRefreshViewYh)
	private PullToRefreshView pullToRefreshViewYh;

	@ViewInject(R.id.listviewYs)
	private ListView listviewYs;

	@ViewInject(R.id.gridviewYh)
	private GridView gridviewYh;

	@ViewInject(R.id.frameYs)
	private LinearLayout frameYs;

	@ViewInject(R.id.frameYh)
	private LinearLayout frameYh;

	private YuansuSearchLogic yLogic;

	private UserSearchLogic uLogic;

	private HttpUtils httpUtil;

	public synchronized static SearchFragment getIns(String keyword) {
		if (null == ins) {
			ins = new SearchFragment(keyword);
		}
		ins.keyword = keyword;
		return ins;
	}

	public SearchFragment(String keyword) {
		this.keyword = keyword;
	}

	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	@OnClick(R.id.btnToggle)
	public void btnToggleClick(View view) {
		((SuperActivityForFragment) getActivity()).toggle();
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void handleHttpResponse(String response, int rspCode, int requestId) {

	}

	@Override
	public void handleHttpResponse(String response, int requestId) {

	}

	@Override
	public void handleHttpException(HttpException error, String msg) {

	}

	@Override
	public void handleHttpTimeout(int paramInt) {

	}

	@Override
	public void updateView() {
		if (0 == index) {
			if (null != adapterYs) {
				adapterYs.notifyDataSetChanged();
			}
		} else {
			if (null != adapterYh) {
				adapterYh.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void initData() {
		yLogic = YuansuSearchLogic.getInstance();
		uLogic = UserSearchLogic.getInstance();
	}

	
	@ViewInject(R.id.btnFayan)
	private Button btnFayan;

	@ViewInject(R.id.btnYonghu)
	private Button btnYonghu;

	@OnClick(R.id.btnFayan)
	public void btnFayanClick(View view) {
		setBg(true);
	}

	@OnClick(R.id.btnYonghu)
	public void btnYonghuclick(View view) {
		setBg(false);
	}

	private BitmapUtils bitmapUtils;
	View convertView;
	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.search_layout, container,
					false);
		ViewUtils.inject(this, convertView);
		bitmapUtils = new BitmapUtils(getActivity());
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.failed);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		initListView();
		return convertView;
	}

	private void initListView() {
		if (null == adapterYs) {
			adapterYs = new YsAdapter(yLogic.list, getActivity()
					.getBaseContext());
			listviewYs.setAdapter(adapterYs);
			pullToRefreshViewYs
					.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

						@Override
						public void onHeaderRefresh(PullToRefreshView view) {
							reqeustDate(0);
						}
					});
			pullToRefreshViewYs
					.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

						@Override
						public void onFooterRefresh(PullToRefreshView view) {
							reqeustDate(1);

						}
					});
		} else {
			adapterYs.notifyDataSetChanged();
		}
		if (null == adapterYh) {
			adapterYh = new YhAdapter(uLogic.list, getActivity()
					.getBaseContext(), bitmapUtils);
			gridviewYh.setAdapter(adapterYh);
			pullToRefreshViewYh
					.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

						@Override
						public void onHeaderRefresh(PullToRefreshView view) {
							reqeustDate(0);
						}
					});
			pullToRefreshViewYh
					.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

						@Override
						public void onFooterRefresh(PullToRefreshView view) {
							reqeustDate(1);

						}
					});
		} else {
			adapterYh.notifyDataSetChanged();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private int index = 0;

	public void setBg(boolean fayan) {
		if (fayan) {
			index = 0;
			frameYs.setVisibility(View.VISIBLE);
			frameYh.setVisibility(View.GONE);
			btnFayan.setBackgroundResource(R.drawable.fayan_g);
			btnYonghu.setBackgroundResource(R.drawable.fayan_p);
		} else {
			index = 1;
			frameYs.setVisibility(View.GONE);
			frameYh.setVisibility(View.VISIBLE);
			btnYonghu.setBackgroundResource(R.drawable.fayan_g);
			btnFayan.setBackgroundResource(R.drawable.fayan_p);
		}
		reqeustDate(0);
	}

	@Override
	public void reqeustDate(String keyword) {
		reqeustDate(0);
	}

	public void reqeustDate(int type) {
		if (index == 0) {
			refreshYuansu(type);
		} else {
			refreshYonghu(type);
		}
	}

	DialogInterface.OnDismissListener dismiss = new DialogInterface.OnDismissListener() {
		@Override
		public void onDismiss(DialogInterface dialog) {
			httpUtil.getHttpClient().getConnectionManager().shutdown();
		}
	};
	public int actionType = 0;

	private void onLoad() {

		if (actionType == 0) {
			if (index == 0) {
				pullToRefreshViewYs.onHeaderRefreshComplete();
			} else {
				pullToRefreshViewYh.onHeaderRefreshComplete();
			}
		} else {
			if (index == 0) {
				pullToRefreshViewYs.onFooterRefreshComplete();
			} else {
				pullToRefreshViewYh.onFooterRefreshComplete();
			}
		}
	}

	/**
	 * 
	 * Function:获取用户
	 * 
	 * @author ruhaly DateTime 2013-10-17 上午10:34:17
	 */
	public void refreshYuansu(int type) {
		actionType = type;
		httpUtil = new HttpUtils();
		yLogic.setDate(fHandler, httpUtil);
		showProcessDialog(dismiss);
		yLogic.sendPageHomeYuanSuRequest(getUser().getRemarkToken(), type,
				keyword);
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
		uLogic.setDate(fHandler, httpUtil);
		showProcessDialog(dismiss);
		uLogic.sendGetUserRequest(getUser().getRemarkToken(), keyword, type);
	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case SuperLogic.GET_SEARCH_YUANSU_SUCCESS_MSGWHAT: {
			updateView();
			onLoad();
			break;
		}
		case SuperLogic.GET_SEARCH_USER_SUCCESS_MSGWHAT: {
			updateView();
			onLoad();
			break;
		}
		case SuperLogic.FOLLOW_USER_SUCCESS_MSGWHAT: {
			if (uLogic.list.size() > 0) {
				uLogic.list.get(temp).setStateGuanzhu(Constant.HAS_FOLLOW);
			}
			showToast(getString(R.string.has_follow));
			updateView();
			break;
		}
		case SuperLogic.UNFOLLOW_USER_SUCCESS_MSGWHAT: {
			if (yLogic.list.size() > 0) {
				yLogic.list.get(temp).setFollowState(2);
			}
			showToast(getString(R.string.unfollow));
			updateView();
			break;

		}
		}
		super.handleMsg(msg);
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
							"http://imgtest.meiliworks.com/pic/_o/a2/68/53732042564a9248900de058832f_440_441.jpg?refer_type=&expr_alt=b&frm=out_pic");
			return convertView;
		}

		class Holder {
			ImageView imgHeader;
			TextView tvFy;
			TextView tvName;
			TextView tvGuanzhu;
		}
	}

	// yuansu 适配器
	class YsAdapter extends BaseAdapter {

		private List<Yuansu> list;
		private Context context;

		public YsAdapter(List<Yuansu> list, Context context) {
			this.list = list;
			this.context = context;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Yuansu getItem(int arg0) {
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
				}
			});
			holder.imgShare.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
				}
			});
			holder.imgPingLun.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
				}
			});
			holder.content.setText(getItem(position).getRemarkContent());
			if (holder.content.getLineCount() <= 1) {
				holder.content.setGravity(Gravity.CENTER);
			} else {
				holder.content.setGravity(Gravity.LEFT
						| Gravity.CENTER_VERTICAL);
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

	public int temp = 0;

	/**
	 * 
	 * Function:关注用户
	 * 
	 * @author ruhaly DateTime 2013-10-23 下午2:54:42
	 * @param uid
	 */
	public void followUser(String uid) {
		httpUtil = new HttpUtils();
		uLogic.setDate(fHandler, httpUtil);
		showProcessDialog(dismiss);
		uLogic.sendFollowUserRequest(getUser().getRemarkToken(), uid);
	}

	public void unFollowUser(String uid) {
		httpUtil = new HttpUtils();
		uLogic.setDate(fHandler, httpUtil);
		showProcessDialog(dismiss);
		uLogic.sendFollowUserRequest(getUser().getRemarkToken(), uid);
	}

	/**
	 * 
	 * Function:关注元素
	 * 
	 * @author ruhaly DateTime 2013-10-23 下午2:55:16
	 * @param remarkId
	 */
	public void follorYuansu(String remarkId) {
		showToast(remarkId);
	}

	public void unFollowYuansu(String remarkId) {
		showToast(remarkId);
	}
}