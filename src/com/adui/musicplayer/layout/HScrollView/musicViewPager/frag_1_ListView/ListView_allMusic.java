package com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_1_ListView;

import java.util.ArrayList;
import java.util.List;

import com.adui.mmusic.R;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.model.Music;
import com.adui.musicplayer.other.Adapterfor2;
import com.adui.musicplayer.service.MusicPlayService;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListView_allMusic extends Fragment{
	
	private Context context;
	private View view;
	private ListView lv;
	private static List<Music> musicList_all;
	private Adapterfor2 adapter;
	
	public ListView_allMusic(Context context){
		this.context=context;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.layout_allmusic_listview,container,false);
		initData();
		showView(musicList_all);
		return view;
	}
	
	private void showView(List<Music> musicList){
		if(adapter==null || lv==null){
			initView(musicList);
		}else {
			adapter.Update(musicList);
		}
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {
		// TODO Auto-generated method stub
		musicList_all = MusicForDB.getList();
	}
	
	public static List<Music> giveList0(){
		return musicList_all;
	}
	
	/**
	 * 初始化适配器、ListView
	 */
	private void initView(List<Music> musics) {
		// TODO Auto-generated method stub
		
		adapter = new Adapterfor2(context, R.layout.listview_item, musics);
		lv = (ListView) view.findViewById(R.id.listview_allMusic);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Log.d("item click", "postion :"+position);
				MusicPlayService.openService(context,position,musicList_all.size(),0);
			}
		});
	}
}
