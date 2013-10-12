package com.yingqida.richplay.activity;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.fragment.MyFragmentPagerAdapter;
import com.yingqida.richplay.fragment.SuperFragment;
import com.yingqida.richplay.fragment.YuansuFragment;

public class SearchActivity extends SuperActivity {

	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;

	private MyFragmentPagerAdapter adapter;

	@Override
	public void initData() {

	}

	@Override
	public void initLayout() {
		setContentView(R.layout.search_layout);
		findViewById(R.id.btnFayan).setOnClickListener(this);
		findViewById(R.id.btnYonghu).setOnClickListener(this);
		initViewPager();
	}

	private void initViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		fragmentsList = new ArrayList<Fragment>();

		SuperFragment yuansufragment = YuansuFragment
				.getInstance("yuansufragment");
		SuperFragment userFragment = YuansuFragment.getInstance("userFragment");
		if (null == adapter) {
			adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
					fragmentsList);
			mPager.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		fragmentsList.add(yuansufragment);
		fragmentsList.add(userFragment);
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			if (0 == arg0) {
				setBg(true);
			} else {
				setBg(false);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	public class MyOnClickListener implements View.OnClickListener {

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	private int index = 0;

	public void setBg(boolean fayan) {
		if (fayan) {
			findViewById(R.id.btnFayan).setBackgroundResource(
					R.drawable.fayan_g);
			findViewById(R.id.btnYonghu).setBackgroundResource(
					R.drawable.fayan_p);
		} else {
			findViewById(R.id.btnYonghu).setBackgroundResource(
					R.drawable.fayan_g);
			findViewById(R.id.btnFayan).setBackgroundResource(
					R.drawable.fayan_p);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnFayan: {
			setBg(true);
			index = 0;
			mPager.setCurrentItem(index);
			break;
		}
		case R.id.btnYonghu: {
			setBg(false);
			index = 1;
			mPager.setCurrentItem(index);
			break;
		}
		}
	}

	@Override
	public void clearData() {

	}

	public void btnSearch(View view) {
		adapter.getItem(mPager.getCurrentItem()).updateView();
	}
}
