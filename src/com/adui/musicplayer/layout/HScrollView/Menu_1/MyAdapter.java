package com.adui.musicplayer.layout.HScrollView.Menu_1;

import java.util.List;

import com.adui.mmusic.R;

import android.content.Context;

/**
 * ���Ӧ������������ �̳���������������<T>
 */
public class MyAdapter extends Adapter<String> {

	public MyAdapter(Context context, List<String> datas) {
		super(context, datas,R.layout.layout_menu_item);
	}

	@Override
	public void convert(ViewHolder holder, String bean) {
		holder.setText(R.id.menu_itemName, bean);
	}

}
