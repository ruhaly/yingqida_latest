package com.yingqida.richplay.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.PicInfoActivity;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.pubuliu.DuitangInfo;
import com.yingqida.richplay.pubuliu.ImageCache;
import com.yingqida.richplay.pubuliu.ImageFetcher;
import com.yingqida.richplay.pubuliu.ImageWorker.ICallBack;
import com.yingqida.richplay.pubuliu.PLA_AdapterView;
import com.yingqida.richplay.pubuliu.XListView;
import com.yingqida.richplay.pubuliu.XListView.IXListViewListener;

public class FayanFragment extends SuperFragment implements IXListViewListener {

	public static SuperFragment getInstance(String name) {
		return new FayanFragment();
	}

	public FayanFragment() {
		super();
	}

	private ImageFetcher mImageFetcher;
	private XListView mAdapterView = null;
	private StaggeredAdapter mAdapter = null;
	private int currentPage = 0;
	private static final int HEADWIDTH = 40;
	ContentTask task = new ContentTask(getActivity(), 2);

	private class ContentTask extends
			AsyncTask<String, Integer, List<DuitangInfo>> {

		private int mType = 1;

		public ContentTask(Context context, int type) {
			super();
			mType = type;
		}

		@Override
		protected List<DuitangInfo> doInBackground(String... params) {
			try {
				return parseNewsJSON(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<DuitangInfo> result) {
			if (mType == 1) {
				mInfos.addAll(result);
				// mAdapter.addItemTop(result);
				mAdapter.notifyDataSetChanged();
				mAdapterView.stopRefresh();

			} else if (mType == 2) {
				mAdapterView.stopLoadMore();
				mAdapter.addItemLast(result);
				mAdapter.notifyDataSetChanged();
			}

		}

		@Override
		protected void onPreExecute() {
		}

		public List<DuitangInfo> parseNewsJSON(String url) throws IOException {
			return mInfos;
		}
	}

	private List<DuitangInfo> mInfos = new ArrayList<DuitangInfo>();

	public void initList() {
		for (int i = 0; i < 20; i++) {
			DuitangInfo di = new DuitangInfo();
			di.setIsrc(imageThumbUrls[i]);
			di.setName("ruhaly" + i);
			di.setMsg("msg" + i);
			di.setHeader(imageThumbUrls2[i]);
			mInfos.add(di);
		}
	}

	public final static String[] imageThumbUrls = new String[] {
			"http://c.hiphotos.baidu.com/album/w%3D2048/sign=d975ee8a5bafa40f3cc6c9dd9f5c024f/a08b87d6277f9e2fad8d44b01e30e924b999f3ca.jpg",
			"http://g.hiphotos.baidu.com/album/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=626fcead5ab5c9ea62f307e0e30f8d63/0b7b02087bf40ad1342d3f45562c11dfa8ecce4f.jpg",
			"http://b.hiphotos.baidu.com/album/w%3D2048/sign=a0c734ad5d6034a829e2bf81ff2b4b54/71cf3bc79f3df8dce8e2323dcc11728b461028ba.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=5ef7ddec024f78f0800b9df34d090b55/29381f30e924b8990c1b3a806f061d950a7bf667.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=eef3c4999213b07ebdbd570838ef9023/e61190ef76c6a7efdb1aeb9efcfaaf51f3de664d.jpg",
			"http://d.hiphotos.baidu.com/album/w%3D2048/sign=ad5c3ef6b21c8701d6b6b5e613479e2f/b3fb43166d224f4aa38bf30108f790529822d196.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=a28afc6777c6a7efb926af26c9c2ae51/32fa828ba61ea8d3b8842dbc960a304e241f58e7.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=5ef7ddec024f78f0800b9df34d090b55/29381f30e924b8990c1b3a806f061d950a7bf667.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=eef3c4999213b07ebdbd570838ef9023/e61190ef76c6a7efdb1aeb9efcfaaf51f3de664d.jpg",
			"http://d.hiphotos.baidu.com/album/w%3D2048/sign=ad5c3ef6b21c8701d6b6b5e613479e2f/b3fb43166d224f4aa38bf30108f790529822d196.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=a28afc6777c6a7efb926af26c9c2ae51/32fa828ba61ea8d3b8842dbc960a304e241f58e7.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=5ef7ddec024f78f0800b9df34d090b55/29381f30e924b8990c1b3a806f061d950a7bf667.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=eef3c4999213b07ebdbd570838ef9023/e61190ef76c6a7efdb1aeb9efcfaaf51f3de664d.jpg",
			"http://d.hiphotos.baidu.com/album/w%3D2048/sign=ad5c3ef6b21c8701d6b6b5e613479e2f/b3fb43166d224f4aa38bf30108f790529822d196.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=a28afc6777c6a7efb926af26c9c2ae51/32fa828ba61ea8d3b8842dbc960a304e241f58e7.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=5ef7ddec024f78f0800b9df34d090b55/29381f30e924b8990c1b3a806f061d950a7bf667.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=eef3c4999213b07ebdbd570838ef9023/e61190ef76c6a7efdb1aeb9efcfaaf51f3de664d.jpg",
			"http://d.hiphotos.baidu.com/album/w%3D2048/sign=ad5c3ef6b21c8701d6b6b5e613479e2f/b3fb43166d224f4aa38bf30108f790529822d196.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=a28afc6777c6a7efb926af26c9c2ae51/32fa828ba61ea8d3b8842dbc960a304e241f58e7.jpg",
			"http://g.hiphotos.baidu.com/album/w%3D2048/sign=2ede4113023b5bb5bed727fe02ebd739/7dd98d1001e9390127d5cd867aec54e737d196b6.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=5ef7ddec024f78f0800b9df34d090b55/29381f30e924b8990c1b3a806f061d950a7bf667.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=eef3c4999213b07ebdbd570838ef9023/e61190ef76c6a7efdb1aeb9efcfaaf51f3de664d.jpg",
			"http://d.hiphotos.baidu.com/album/w%3D2048/sign=ad5c3ef6b21c8701d6b6b5e613479e2f/b3fb43166d224f4aa38bf30108f790529822d196.jpg",
			"http://f.hiphotos.baidu.com/album/w%3D2048/sign=a28afc6777c6a7efb926af26c9c2ae51/32fa828ba61ea8d3b8842dbc960a304e241f58e7.jpg",
			"http://b.hiphotos.bdimg.com/album/w%3D2048/sign=c17a8b6830adcbef0134790698972cdd/3b292df5e0fe9925ade9dbfc35a85edf8cb171b0.jpg" };

	public final static String[] imageThumbUrls2 = new String[] {
			"http://wenwen.soso.com/p/20091227/20091227163325-542526313.jpg",
			"http://poto.kikicici.com/up_files/2010-03-04/1267710539Twky.png",
			"http://poto.kikicici.com/up_files/2009-12-14/thumb_1260789547FtqW.jpg",
			"http://poto.kikicici.com/up_files/2010-03-04/1267710885yi96.png",
			"http://poto.kikicici.com/up_files/2010-03-04/1267710710DUVQ.png",
			"http://poto.kikicici.com/up_files/2009-12-14/1260789519GMih.jpg",
			"http://poto.kikicici.com/up_files/2010-03-04/1267706823ftm9.png",
			"http://poto.kikicici.com/up_files/2010-03-04/1267710861w4LF.png",
			"http://poto.kikicici.com/up_files/2010-03-04/1267710533d8de.png",
			"http://www.3g37.com/haotu/UploadFiles_5488/201103/20110314202100564.jpg",
			"http://poto.kikicici.com/up_files/2010-03-04/1267710541siRk.png",
			"http://www.1124.cc/up_files/2009-12-14/1260789559bq9h.jpg",
			"http://poto.kikicici.com/up_files/2010-03-04/126770683987jA.png",
			"http://poto.kikicici.com/up_files/2010-03-04/1267710876Deck.png",
			"http://poto.kikicici.com/up_files/2009-12-14/thumb_1260789493L3wA.jpg",
			"http://poto.kikicici.com/up_files/2009-12-14/thumb_1260789510HSiW.jpg",
			"http://poto.kikicici.com/up_files/2010-03-04/1267707206dsqt.png",
			"http://img.sccnn.com/simg/154/13.jpg",
			"http://poto.kikicici.com/up_files/2009-12-14/1260789489rGR7.jpg",
			"http://poto.kikicici.com/up_files/2009-12-14/1260789493Tjsj.jpg",
			"http://poto.kikicici.com/up_files/2010-03-04/1267710532eHax.png" };

	/**
	 * 添加内容
	 * 
	 * @param pageindex
	 * @param type
	 *            1为下拉刷新 2为加载更多
	 */
	private void AddItemToContainer(int pageindex, int type) {
		if (task.getStatus() != Status.RUNNING) {
			String url = "http://www.duitang.com/album/1733789/masn/p/"
					+ pageindex + "/24/";
			Log.d("MainActivity", "current url:" + url);
			ContentTask task = new ContentTask(getActivity(), type);
			task.execute(url);

		}
	}

	public class StaggeredAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			DuitangInfo duitangInfo = mInfos.get(position);

			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(parent
						.getContext());
				convertView = layoutInflator.inflate(R.layout.infos_list, null);
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.news_pic);
				holder.imageView2 = (ImageView) convertView
						.findViewById(R.id.news_pic2);
				holder.contentView = (TextView) convertView
						.findViewById(R.id.news_title);
				convertView.setTag(holder);
			}

			holder = (ViewHolder) convertView.getTag();
			// holder.imageView.setImageWidth(duitangInfo.getWidth());
			// holder.imageView.setImageHeight(duitangInfo.getHeight());
			holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(
					getScreenW() / 2, LinearLayout.LayoutParams.WRAP_CONTENT));
			holder.contentView.setText(duitangInfo.getMsg());
			mImageFetcher.loadImage(duitangInfo.getIsrc(), holder.imageView,
					getScreenW() / 2, false);
			mImageFetcher.loadImage(duitangInfo.getHeader(), holder.imageView2,
					HEADWIDTH, false);
			return convertView;
		}

		class ViewHolder {
			ImageView imageView;
			ImageView imageView2;
			TextView contentView;
			TextView timeView;
		}

		@Override
		public int getCount() {
			return mInfos.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mInfos.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		public void addItemLast(List<DuitangInfo> datas) {
			// mInfos.addAll(datas);
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onRefresh() {
		AddItemToContainer(++currentPage, 1);
	}

	@Override
	public void onLoadMore() {
		AddItemToContainer(++currentPage, 2);
	}

	@Override
	public void initData() {
		initList();
	}

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.yuansu_layout, container,
				false);
		mAdapterView = (XListView) convertView.findViewById(R.id.list);
		mAdapterView.setPullLoadEnable(true);
		mAdapterView.setXListViewListener(this);

		mAdapter = new StaggeredAdapter();

		mImageFetcher = new ImageFetcher(getActivity(), 240, new ICallBack() {

			@Override
			public void invoke(String name, int code) {

			}
		});
		mImageFetcher.setImageCache(new ImageCache(getActivity(),
				RichResource.PIC_PATH));
		mImageFetcher.setLoadingImage(R.drawable.empty_photo);
		mImageFetcher.setExitTasksEarly(false);
		mAdapterView.setAdapter(mAdapter);
		AddItemToContainer(currentPage, 2);
		mAdapterView
				.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(PLA_AdapterView<?> parent,
							View view, int position, long id) {
						startActivity(new Intent(
								getActivity().getBaseContext(),
								PicInfoActivity.class));
					}
				});
		return convertView;

	}

	public void initList2() {
		for (int i = 19; i > 0; i--) {
			DuitangInfo di = new DuitangInfo();
			di.setIsrc(imageThumbUrls[i]);
			di.setName("ruhaly" + i);
			di.setMsg("msg" + i);
			di.setHeader(imageThumbUrls2[i]);
			mInfos.add(di);
		}
	}

	@Override
	public void updateView() {
		mInfos.clear();
		initList2();
		if (null != mAdapter) {
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
