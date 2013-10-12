package com.yingqida.richplay.activity;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.baseapi.common.RichResource;
import com.yingqida.richplay.pubuliu.DuitangInfo;
import com.yingqida.richplay.pubuliu.ImageCache;
import com.yingqida.richplay.pubuliu.ImageFetcher;
import com.yingqida.richplay.pubuliu.ImageWorker.ICallBack;

public class WallPicActivityOld extends SuperActivity {
	private List<DuitangInfo> mInfos = new ArrayList<DuitangInfo>();
	private ImageFetcher mImageFetcher;
	private ListView list_wall;
	private Adapter adapter;
	public static int itemheight = 200;

	@Override
	public void initData() {
		initList();
		itemheight = convertDipOrPx(getBaseContext(), itemheight);
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void initLayout() {

		setContentView(R.layout.wall_pic_layout);
		mImageFetcher = new ImageFetcher(this, getScreenW(), new ICallBack() {

			@Override
			public void invoke(String name, int code) {

			}
		});
		mImageFetcher.setImageCache(new ImageCache(getBaseContext(),
				RichResource.PIC_PATH));
		mImageFetcher.setLoadingImage(R.drawable.list_item_bg);
		mImageFetcher.setExitTasksEarly(false);
		list_wall = (ListView) findViewById(R.id.list_wall);
		if (adapter == null) {
			adapter = new Adapter();
			list_wall.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		} else {
			adapter.notifyDataSetChanged();
		}

	}

	@Override
	public void clearData() {

	}

	class Adapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater layoutInflator = LayoutInflater.from(parent
						.getContext());
				holder = new ViewHolder();
				convertView = layoutInflator.inflate(R.layout.wall_pic_item,
						null);

				holder.img_pic = (ImageView) convertView
						.findViewById(R.id.img_pic1);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_name1);
				holder.tv_msg = (TextView) convertView
						.findViewById(R.id.tv_msg1);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.img_pic.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, 300));
			mImageFetcher.loadImage(getItem(position).getIsrc(),
					holder.img_pic, getScreenW(), false);
			holder.tv_name.setText(getItem(position).getMsg());
			holder.tv_msg.setText(getItem(position).getMsg());
			return convertView;
		}

		class ViewHolder {
			ImageView img_pic;
			TextView tv_name;
			TextView tv_msg;
		}

		@Override
		public int getCount() {
			return mInfos.size();
		}

		@Override
		public DuitangInfo getItem(int arg0) {
			return mInfos.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
	}

	public void initList() {
		for (int i = 0; i < 20; i++) {
			DuitangInfo di = new DuitangInfo();
			di.setIsrc(imageThumbUrls[i]);
			di.setName("ruhaly" + i);
			di.setMsg("msg" + i);
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
}
