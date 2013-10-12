package com.yingqida.richplay.activity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.PhotoActivity;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.baseapi.ActivityResultCode;
import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.ImageUtil;
import com.yingqida.richplay.baseapi.common.RequestId;
import com.yingqida.richplay.baseapi.http.HttpRequest;
import com.yingqida.richplay.entity.Album;
import com.yingqida.richplay.entity.RleasePicEntity;
import com.yingqida.richplay.packet.HttpSender;

public class ReleasePicActivity extends SuperActivity implements
		ActivityResultCode {

	private ImageView imgView;

	private AlbumAdapter adapter;

	private static final int ACTION_DOWN_ALBUM = 888801;

	public List<Album> list = new ArrayList<Album>();

	private TextView txtAlbum;

	private TextView edtPicDec;

	private TextView txtLoc;

	// 当前选中的相册的id
	private String curId = "";

	private Button btnRelease;

	// 扫描条形码
	private Button btnBarcode;

	private TextView txtBarCode;

	@Override
	public void initData() {
		Album a = new Album();
		a.setId(UUID.randomUUID().toString());
		a.setName("a");
		a.setImageUrl("http://pic-hzrb.hangzhou.com.cn/0/10/43/57/10435714_979594.jpg");
		a.setCheck(false);

		Album b = new Album();
		b.setId(UUID.randomUUID().toString());
		b.setName("b");
		b.setImageUrl("http://cache.mars.sina.com.cn/nd/supportsjiaju/fitment/show/e4/5e/photo95930_175x175.jpg");
		b.setCheck(false);

		Album c = new Album();
		c.setId(UUID.randomUUID().toString());
		c.setName("c");
		c.setImageUrl("http://cache.mars.sina.com.cn/nd/supportsjiaju/fitment/show/e4/5e/photo95930_175x175.jpg");
		c.setCheck(false);

		Album d = new Album();
		d.setId(UUID.randomUUID().toString());
		d.setName("d");
		d.setImageUrl("http://cache.mars.sina.com.cn/nd/supportsjiaju/fitment/show/e4/5e/photo95930_175x175.jpg");
		d.setCheck(false);

		Album e = new Album();
		e.setId(UUID.randomUUID().toString());
		e.setName("添加相册");
		e.setImageUrl("http://cache.mars.sina.com.cn/nd/supportsjiaju/fitment/show/e4/5e/photo95930_175x175.jpg");
		e.setCheck(false);

		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);

	}

	@Override
	public void initLayout() {
		setContentView(R.layout.release_pic_layout);
		imgView = (ImageView) findViewById(R.id.imgView);

		edtPicDec = (EditText) findViewById(R.id.edtPicDec);

		txtAlbum = (TextView) findViewById(R.id.txtAlbum);
		txtAlbum.setOnClickListener(this);
		txtLoc = (TextView) findViewById(R.id.txtLoc);
		if (null == adapter) {
			adapter = new AlbumAdapter();
		}
		btnRelease = (Button) findViewById(R.id.btnRelease);
		btnRelease.setOnClickListener(this);

		btnBarcode = (Button) findViewById(R.id.btnBarcode);
		btnBarcode.setOnClickListener(this);
		initLocation();
		txtBarCode = (TextView) findViewById(R.id.txtBarCode);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txtAlbum:
			showAlbumListView();
			break;
		case R.id.btnAdd: {
			addAlbum();
			break;
		}
		case R.id.btnRelease: {
			requestReleasePic();
			break;
		}
		case R.id.btnBarcode: {
//			Intent intent = new Intent(getBaseContext(), CaptureActivity.class);
//			startActivityForResult(intent, BAR_CODE_SCAN);
			break;
		}
		default:
			break;
		}
	}

	private HttpRequest requestRP;

	public void requestReleasePic() {
		AppLog.out(TAG, "requestReleasePic()", AppLog.LEVEL_INFO);
		RleasePicEntity entity = new RleasePicEntity();
		entity.setAlbumid(curId);
		// 图片进行base64编码
		// entity.setImgStream(new String(Base64.encode(
		// getByteArrayFromImage(picPath,
		// ImageUtil.zoomBmp(getImage(picPath), 1 / 2f, true)),
		// Base64.DEFAULT)));
		entity.setFpath(picPath);
		entity.setLocation(null != loc ? loc.getAddrStr() : "");
		entity.setPicDesc(edtPicDec.getText().toString());

		requestRP = new HttpRequest(this, this, RequestId.RELEASE_PIC);
		showProgressDialog("", getString(R.string.send_request_ing), true,
				new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						if (null != requestRP) {
							requestRP.cancel();
							requestRP = null;
						}
					}
				});

		HttpSender.requestReleasePic(getService(), getSessionString(),
				requestRP, entity);
	}

	public byte[] getByteArrayFromImage(String picPath, Bitmap b) {

		// 将Bitmap转换成字符串
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		b.compress(CompressFormat.PNG, 100, bStream);
		byte[] bytes = bStream.toByteArray();
		// string=Base64.encodeToString(bytes,Base64.DEFAULT);
		return bytes;

		// byte[] b = null;
		// FileInputStream fis;
		// try {
		// fis = new FileInputStream(picPath);
		// ByteArrayOutputStream output = new ByteArrayOutputStream();
		// byte[] buf = new byte[1024];
		// int numBytesRead = 0;
		// while ((numBytesRead = fis.read(buf)) != -1) {
		// output.write(buf, 0, numBytesRead);
		// }
		// b = output.toByteArray();
		// output.close();
		// fis.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return b;
	}

	public View view;
	public ListView albumListView;
	public Button btnAdd;
	public TextView edTextAlbum;

	/**
	 * 
	 * showAlbumListView(显示相册列表) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void showAlbumListView() {
		view = LayoutInflater.from(getBaseContext()).inflate(
				R.layout.album_edit_layout, null);
		btnAdd = (Button) view.findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(this);
		edTextAlbum = (EditText) view.findViewById(R.id.edTextAlbum);
		albumListView = (ListView) view.findViewById(R.id.albumList);
		albumListView.setAdapter(adapter);
		albumListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						curId = ((Album) (adapter.getItem(position))).getId();
						txtAlbum.setText(((Album) (adapter.getItem(position)))
								.getName());
						closeDialog();
					}
				});
		showAlertDialog(0, getString(R.string.selectAlbum), null, view, null,
				DEFAULT_BTN, null, true);
	}

	public void addAlbum() {
		Album a = new Album();
		a.setId(UUID.randomUUID().toString());
		a.setName(edTextAlbum.getText().toString());
		a.setImageUrl("http://cache.mars.sina.com.cn/nd/supportsjiaju/fitment/show/e4/5e/photo95930_175x175.jpg");
		a.setCheck(false);
		list.add(a);
		curId = a.getId();
		closeDialog();
		txtAlbum.setText(edTextAlbum.getText().toString());

	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case ACTION_DOWN_ALBUM: {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (null != adapter) {
						adapter.notifyDataSetChanged();
					}
				}
			});
			break;
		}
		case SHOW_LOCATION:
			showLocation((BDLocation) msg.obj);
			return;
		default:
			break;
		}
		super.handleMsg(msg);
	}

	@Override
	public void handleHttpResponse(String response, int netErrorCode,
			int requestId) {
		if (0 != netErrorCode) {
			super.handleHttpResponse(response, netErrorCode, requestId);
		} else {
			handleResponse(requestId, response);
		}
	}

	public void handleResponse(int requestId, String response) {

		switch (requestId) {
		case RequestId.RELEASE_PIC:
			showToast(getString(R.string.release_success));
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		showImage(getIntent());
	}

	Bitmap bmp;
	String picPath;

	/**
	 * 
	 * showImage(拍完之后显示图片)
	 * 
	 * @param intent
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void showImage(Intent intent) {
		Bundle extra = (Bundle) intent.getExtras();
		picPath = (String) extra.get(SampleWallPicActivity.KEY_PHOTO_PATH);
		// BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inSampleSize = 2;// 图片宽高都为原来的二分之一，即图片为原来的四分之一
		// Bitmap b = BitmapFactory.decodeFile(picPath, options);
		Bitmap b = ImageUtil.zoomBmp(getImage(picPath), 1 / 2f, true);
		System.out.println(b.getWidth());
		if (null != b) {
			imgView.setImageBitmap(b);
			imgView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getBaseContext(),
							PhotoActivity.class);
					intent.putExtra(PhotoActivity.ISDOWNPIC, false);
					intent.putExtra(PhotoActivity.EXTRA_URL, picPath);
					startActivity(intent);
				}
			});
		}
	}

	class AlbumAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return (Album) list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (null == convertView) {
				convertView = LayoutInflater.from(getBaseContext()).inflate(
						R.layout.album_item_layout, null);
				holder = new Holder();
				holder.txtAlbum = (TextView) convertView
						.findViewById(R.id.txtAlbum);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.txtAlbum.setText(list.get(position).getName());

			return convertView;
		}

		class Holder {
			public TextView txtAlbum;
		}
	}

	// 定位相关
	private LocationClient mLocClient;

	public void initLocation() {
		mLocClient = new LocationClient(this);
		startLocationOption();
	}

	public static final String LOCTYPE = "bd09ll";
	public static final int SCANSPAN = 10000;
	public MyLocationListenner myListener = new MyLocationListenner();
	public static final int SHOW_LOCATION = 1;

	public void startLocationOption() {

		LocationClientOption option = new LocationClientOption();
		option.setCoorType(LOCTYPE); // 设置坐标类型
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setScanSpan(SCANSPAN);

		// option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
		option.setPriority(LocationClientOption.GpsFirst); // 不设置，默认是gps优先

		option.setPoiNumber(10);
		option.disableCache(true);
		mLocClient.setLocOption(option);
		mLocClient.registerLocationListener(myListener);
		mLocClient.start();
	}

	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			mHandler.obtainMessage(SHOW_LOCATION, location).sendToTarget();
		}

		@Override
		public void onReceivePoi(BDLocation location) {

		}

	}

	private BDLocation loc;

	private void showLocation(BDLocation location) {
		location.getLatitude();
		txtLoc.setText(location.getLongitude() + "," + location.getLatitude()
				+ "|" + location.getAddrStr());
		loc = location;
	}

	@Override
	public void clearData() {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (Activity.RESULT_OK == resultCode && BAR_CODE_SCAN == requestCode) {
			showToast(data.getExtras().getString("bar_code"));
			txtBarCode.setText(data.getExtras().getString("bar_code"));
		}
	}
}
