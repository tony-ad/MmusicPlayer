package com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_1_ListView;

import java.util.ArrayList;
import java.util.List;

import com.adui.mmusic.R;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_2.MusicContentReceiver;
import com.adui.musicplayer.model.Music;
import com.adui.musicplayer.other.Adapterfor2;
import com.adui.musicplayer.service.MusicPlayService;
import com.mob.commons.logcollector.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

public class ListView_ilikemusic extends Fragment{
	
	private Context context;
	private View view;
	private ListView lv;
	private List<Music> musicList_all;
	private static List<Music> musicList_ilike;
	private Adapterfor2 adapter;
	private ServiceBR serviceBR;
	private int pos;
	private boolean islike;
	private int model;
	
	public ListView_ilikemusic(Context context){
		this.context=context;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.layout_ilikemusic_listview2,container,false);
		musicList_ilike=new ArrayList<Music>();
		initData();
		showView(musicList_ilike);
		initService();
		return view;
	}
	
	private void initService() {
		// TODO Auto-generated method stub
		IntentFilter i = new IntentFilter();
		i.addAction("com.adui.musicplayer.changeList");
		serviceBR = new ServiceBR();
		context.registerReceiver(serviceBR, i);
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
		findMusic();
	}
	
	private void findMusic(){
		for(int i=0;i<musicList_all.size();i++){
			Boolean a = musicList_all.get(i).isIlike();
			if(a==true){
				musicList_ilike.add(musicList_all.get(i));
			}
		}
	}
	
	public static List<Music> giveList1(){
		return musicList_ilike;
	}
	
	/**
	 * 初始化适配器、ListView
	 */
	private void initView(List<Music> musics) {
		// TODO Auto-generated method stub
		
		adapter = new Adapterfor2(context, R.layout.listview_item, musics);
		lv = (ListView) view.findViewById(R.id.listview_ilikeMusic);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Log.d("item click", "postion :"+position);
				MusicPlayService.openService(context,position,musicList_ilike.size(),1);
			}
		});
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		context.unregisterReceiver(serviceBR);
	}
	
	
	public class ServiceBR extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			Log.d("mmm", "onReceive");
			
			pos = intent.getIntExtra("posa", 0);
			islike = intent.getBooleanExtra("isopen", true);
			model = intent.getIntExtra("mmodel", 0);
			Log.d("mmm", "pos is " + pos);
			Log.d("mmm", "model is " + model);
			Log.d("mmm", "islike is " + islike);
			List<Music> musics=new ArrayList<Music>();
			if(model==0){
				musics=musicList_all;
			}else if (model==1) {
				musics=musicList_ilike;
			}
			if(islike){
				musicList_ilike.add(musics.get(pos));
			}else {
				musicList_ilike.remove(musics.get(pos));
			}
			
			showView(musicList_ilike);
		}
	}
	
	
}
