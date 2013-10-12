package com.yingqida.richplay.activity.common;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.yingqida.richplay.R;
import com.yingqida.richplay.baseapi.ImageUtil;

public class PhotoActivity extends SuperActivity {

	public static final String EXTRA_URL = "EXTRA_URL";
	public static final String ISDOWNPIC = "ISDOWNPIC";
	private ImageView img;
	private Bitmap photo;

	@Override
	public void initData() {

	}

	@Override
	public void initLayout() {
		setContentView(R.layout.msg_pic_layout);
		img = (ImageView) findViewById(R.id.img);
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		String url = getIntent().getStringExtra(EXTRA_URL);

		if (getIntent().getBooleanExtra(ISDOWNPIC, true)) {
			photo = ImageUtil.getDownloadBitmap(url, true, getScreenW(), false);
		} else {
			photo = ImageUtil.zoomBmp(getImage(url), 1 / 1f, true);
		}

		if (null == photo) {
			finish();
		} else {
			img.setImageBitmap(photo);
		}
	}

	@Override
	public void clearData() {

	}
}
