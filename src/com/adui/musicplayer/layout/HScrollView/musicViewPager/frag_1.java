package com.adui.musicplayer.layout.HScrollView.musicViewPager;


import java.util.ArrayList;
import java.util.List;

import com.adui.mmusic.R;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.model.Music;
import com.adui.musicplayer.other.Adapterfor2;
import com.adui.musicplayer.service.MusicPlayService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 音乐选择界面
 * @author user
 *
 */
public class frag_1 extends Fragment {
	private View view;
	private Context mContext;
	public static List<Music> musicL;
	private ListView lv;
	private int model=2;
	private boolean First_Click_ListView_Item=true;
	
	public frag_1(Context context) {
		mContext=context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.activity_musicchoose, container,false);
		initData();
		initView();
		return view;
	}

	/**
	 * 初始化适配器、ListView
	 */
	private void initView() {
		// TODO Auto-generated method stub
		Adapterfor2 adapter = new Adapterfor2(mContext, R.layout.listview_item, musicL);
		lv = (ListView) view.findViewById(R.id.listview);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if(First_Click_ListView_Item){
					Toast.makeText(mContext, "向左滑切换页面！", Toast.LENGTH_SHORT).show();
					First_Click_ListView_Item=false;
					
				}
				Log.d("item click", "postion :"+position);
				MusicPlayService.openService(mContext,position);
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// TODO Auto-generated method stub
		musicL = MusicForDB.getList();
	}
	
	



}
