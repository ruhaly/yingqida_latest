package com.yingqida.richplay.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yingqida.richplay.R;
import com.yingqida.richplay.activity.MenuActivity;
import com.yingqida.richplay.activity.common.SuperActivity;
import com.yingqida.richplay.entity.Menu;

public class MenuFragment extends SuperFragment {

	@ViewInject(R.id.listViewMenu)
	private ListView listViewMenu;

	private Adapter adapter;

	private List<Menu> menus = new ArrayList<Menu>();

	@ViewInject(R.id.btnToggle)
	private Button btnToggle;

	@Override
	public void onClick(View v) {

	}

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
		convertView = inflater.inflate(R.layout.menu_fragment_layout, null);
		ViewUtils.inject(this, convertView);
		if (null == adapter) {
			adapter = new Adapter();
			listViewMenu.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		return convertView;
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

}