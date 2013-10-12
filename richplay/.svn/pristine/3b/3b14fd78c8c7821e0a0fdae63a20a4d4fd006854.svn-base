package com.yingqida.richplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class SuperFragment extends Fragment implements
		View.OnClickListener {
	public int getScreenH() {
		return getActivity().getWindowManager().getDefaultDisplay().getHeight();
	}

	public int getScreenW() {
		return getActivity().getWindowManager().getDefaultDisplay().getWidth();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public abstract void updateView();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initData();
		return initLayout(inflater, container, savedInstanceState);
	}

	public abstract void initData();

	public abstract View initLayout(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

}
