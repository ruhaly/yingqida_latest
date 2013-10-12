package com.yingqida.richplay.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.baseapi.AppLog;
import com.yingqida.richplay.baseapi.ImageUtil;
import com.yingqida.richplay.baseapi.common.DownloadEntity;
import com.yingqida.richplay.baseapi.common.RequestId;
import com.yingqida.richplay.baseapi.common.UnSyncImageLoader;
import com.yingqida.richplay.baseapi.common.UnSyncImageLoader.ICallBack;
import com.yingqida.richplay.baseapi.http.HttpRequest;
import com.yingqida.richplay.entity.WallPicInfo;
import com.yingqida.richplay.logic.WallPicInfoLogic;
import com.yingqida.richplay.packet.HttpSender;
import com.yingqida.richplay.packet.HttpXml;

public class WallInfoActivity extends SuperActivity {
	private WallPicInfoLogic logic;
	public static final int WALLPICINFO = 2;
	public static final int UPDATE_LAYOUT = 1;
	private ImageView imgView;
	private TextView txtLocation;

	@Override
	public void initData() {
		logic = WallPicInfoLogic.getIns();
	}

	@Override
	public void initLayout() {
		setContentView(R.layout.wall_info_layout);
		requestWallPicInfo(getIntent().getExtras().getString("pid"));
		imgView = (ImageView) findViewById(R.id.imgView);
		txtLocation = (TextView) findViewById(R.id.txtLocation);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void clearData() {

	}

	@Override
	public void handleMsg(Message msg) {
		switch (msg.what) {
		case UPDATE_LAYOUT: {
			updateView();
			break;
		}
		default:
			break;
		}
		super.handleMsg(msg);
	}

	public void updateView() {
		if (null != logic.getWp().getLocation()) {
			txtLocation.setText(logic.getWp().getLocation());
		}
		updateImage();
	}

	Bitmap bitmap;

	public void updateImage() {
		String purl = logic.getWp().getPurl();
		bitmap = ImageUtil.getDownloadBitmap(purl, true, getScreenW(), false);
		if (null != bitmap) {
			imgView.setImageBitmap(bitmap);
		} else {
			ad_imgLoader.new DownloadImagesTask().execute(new DownloadEntity(
					purl, DownloadEntity.TYPE_PHOTO));
		}
	}

	private HttpRequest requestWPI;

	/**
	 * 图片详情查看 requestWallPicInfo()
	 * 
	 * @param pid
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public void requestWallPicInfo(String pid) {
		AppLog.out(TAG, "requestWallPicInfo()", AppLog.LEVEL_INFO);
		requestWPI = new HttpRequest(this, this, RequestId.WALL_PIC_INFO);
		showProgressDialog("", getString(R.string.send_request_ing), true,
				new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						requestWPI.cancel();
						requestWPI = null;
					}
				});
		HttpSender.requestWallPicInfo(getService(), getSessionString(),
				requestWPI, pid);

	}

	@Override
	public void handleHttpResponse(String response, int netErrorCode,
			int requestId) {
		if (0 != netErrorCode) {
			super.handleHttpResponse(response, netErrorCode, requestId);
		} else {
			handleResponse(requestId, response);
		}
		dismissProgress();
	}

	public void handleResponse(int requestId, String response) {

		switch (requestId) {
		case RequestId.WALL_PIC_INFO:
			WallPicInfo wp = HttpXml.parseWallPicInfoXML(response.getBytes());
			if (null != wp) {
				logic.setWp(wp);
			}
			mHandler.sendEmptyMessage(UPDATE_LAYOUT);
			break;
		default:
			break;
		}
	}

	private ICallBack ad_callback = new ICallBack() {

		@Override
		public void invoke(String name, int code) {
			if (0 == code) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						updateImage();
					}
				});
			}
		}
	};

	private UnSyncImageLoader ad_imgLoader = new UnSyncImageLoader(ad_callback);

	@Override
	public void finish() {
		if (null != bitmap) {
			bitmap = null;
		}
		super.finish();
	}
}
