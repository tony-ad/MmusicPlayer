package com.adui.musicplayer.layout.HScrollView.Menu_1;

import java.util.ArrayList;
import java.util.List;

import com.adui.mmusic.R;
import com.mob.tools.network.StringPart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Menu_1 extends Fragment {
	private Context context;
	private View view;
	private ListView listView;
	private Button button;
	private MyAdapter myAdapter;
	private List<String> list;
	private String[] menu_names = new String[] { "设置", "this - 定时关闭", "Wi-Fi联网", "清理空间", "this - 关于Mmusic" };

	private String menu_name = "菜单栏_栏目_";

	public Menu_1() {
	}

	public Menu_1(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_menu, container, false);

		initDatas();
		initView();

		return view;
	}

	/**
	 * 初始化菜单栏数据
	 */
	private void initDatas() {
		// TODO Auto-generated method stub
		list = new ArrayList<String>();
		for (int i = 0; i < menu_names.length; i++) {
			list.add(menu_names[i]);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		button = (Button) view.findViewById(R.id.button_menu_login);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 跳转到menu_2(Menu2碎片换为登陆页面)
				sendBroadcast_OpenToMenu2("login","go");
			}
		});

		listView = (ListView) view.findViewById(R.id.menu_listview);
		myAdapter = new MyAdapter(context, list);
		listView.setAdapter(myAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					
					break;
				case 1:
					sendBroadcast_OpenToMenu2("TimingClosing","not");
					break;
				case 2:

					break;
				case 3:

					break;
				case 4:
					sendBroadcast_OpenToMenu2("aboutMe","go");
					break;

				default:
					break;
				}
			}
		});
	}

	private void sendBroadcast_OpenToMenu2(String values,String goOrback){
		Intent intent = new Intent("Open_To.Menu2");
		intent.putExtra("values", values);
		intent.putExtra("GoOrBack", goOrback);
		context.sendBroadcast(intent);
	}
	
	
}
