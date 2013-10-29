package com.yingqida.richplay.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.ModifyPWDActivity;
import com.yingqida.richplay.activity.common.SuperActivityForFragment;
import com.yingqida.richplay.entity.Menu;

public class MenuFragment extends SuperFragment implements OnItemClickListener {

	@ViewInject(R.id.listViewMenu)
	private ListView listViewMenu;

	@ViewInject(R.id.etSearch)
	private EditText etSearch;

	private Adapter adapter;

	private List<Menu> menus = new ArrayList<Menu>();

	@ViewInject(R.id.tvName)
	private TextView tvName;

	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	@Override
	public void updateView() {

	}

	@Override
	public void initData() {
		initMenus();
	}

	/**
	 * 
	 * Function:初始化菜单
	 * 
	 * @author ruhaly DateTime 2013-10-16 下午3:18:07
	 */
	public void initMenus() {

		String[] array = getActivity().getResources().getStringArray(
				R.array.menus);

		for (int i = 0; i < array.length; i++) {
			Menu menu = new Menu();
			menu.setName(array[i]);
			menus.add(menu);
		}

	}

	View convertView;

	@Override
	public View initLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.menu_fragment_layout, null);
		ViewUtils.inject(this, convertView);
		listViewMenu.setOnItemClickListener(this);
		if (null == adapter) {
			adapter = new Adapter();
			listViewMenu.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					hiddenSoft(etSearch);
				}
			}
		});
		etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@SuppressLint("NewApi")
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if (etSearch.getText().toString().trim().isEmpty()) {
					return false;
				} else if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					InputMethodManager imm = (InputMethodManager) v
							.getContext().getSystemService(
									Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					etSearch.setFocusable(true);
					etSearch.setFocusableInTouchMode(true);
					etSearch.requestFocus();
					requestSearch();
					return true;
				}
				return false;

			}
		});
		tvName.setText(getUser().getName());
		return convertView;
	}

	SuperFragment fragment;

	/**
	 * 
	 * Function:跳转到搜索界面
	 * 
	 * @author ruhaly DateTime 2013-10-21 下午7:15:53
	 */
	public void requestSearch() {
		toggle();
		fragment = SearchFragment.getIns(etSearch.getText().toString().trim());
		etSearch.setText("");
		switchFragment(fragment);
	}

	class Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return menus.size();
		}

		@Override
		public Menu getItem(int arg0) {
			return menus.get(arg0);
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
						R.layout.menu_item_layout, null);
				holder.menuName = (TextView) convertView
						.findViewById(R.id.menuName);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.menuName.setText(getItem(position).getName());
			return convertView;
		}

		class Holder {
			TextView menuName;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SuperFragment fragment = null;
		switch (position) {
		case 0: {
			fragment = PageHomeFragment.getIns();
			break;
		}
		case 1: {
			break;
		}
		case 5: {
			toggle();
			getActivity().startActivity(
					new Intent(getActivity().getBaseContext(),
							ModifyPWDActivity.class));
			break;
		}
		case 6: {
			((SuperActivityForFragment) getActivity()).showLogoutDialog();
			break;
		}
		}
		if (fragment != null)
			switchFragment(fragment);
	}

	@OnClick(R.id.tvMain)
	public void tvMainClick(View view) {
		SuperFragment fragment = PageHomeFragment.getIns();
		if (fragment != null)
			switchFragment(fragment);
	}

	@OnClick(R.id.framePcenter)
	public void framePcenterClick(View view) {
		SuperFragment fragment = PCenterFragment.getIns();
		if (fragment != null)
			switchFragment(fragment);
	}

	@OnClick(R.id.tvPersoninfo)
	public void tvPersoninfoClick(View view) {
		SuperFragment fragment = PCenterFragment.getIns();
		if (fragment != null)
			switchFragment(fragment);
	}

	@OnClick(R.id.tvGuanyu)
	public void tvGuanyuClick(View view) {
		SuperFragment fragment = GuanyuFragment.getIns();
		if (fragment != null)
			switchFragment(fragment);
	}

	@OnClick(R.id.tvUpdate)
	public void tvUpdateClick(View view) {
		SuperFragment fragment = PageHomeFragment.getIns();
		if (fragment != null)
			switchFragment(fragment);
	}

	@OnClick(R.id.tvClearCache)
	public void tvClearCacheClick(View view) {
		((SuperActivityForFragment) getActivity()).showCleanCacheDialog();
	}

	@OnClick(R.id.tvZhuxiao)
	public void tvZhuxiaoClick(View view) {
		((SuperActivityForFragment) getActivity()).showLogoutDialog();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
