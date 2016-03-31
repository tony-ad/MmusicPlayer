package com.adui.musicplayer.model;

import java.util.ArrayList;
import java.util.List;

import com.adui.musicplayer.activity.ActivityOfgg;
import com.adui.musicplayer.activity.MmainActivity;
import com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_1_ListView.ListView_allMusic;
import com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_1_ListView.ListView_ilikemusic;

import android.database.sqlite.SQLiteDatabase;

/**
 * 获取播放列表
 * 全部、我喜欢的
 * @author user
 *
 */
public class Gongju {
	
	private static SQLiteDatabase database;
	private static Gongju gongju;
	
	public static List<Music> whatModelForList(int model) {
		List<Music> musics = new ArrayList<Music>();
		switch (model) {
		case 0:
			musics = ListView_allMusic.giveList0();
			break;
		case 1:
			musics = ListView_ilikemusic.giveList1();
			break;
		case 2:

			break;
		case 3:

			break;

		default:
			break;
		}
		return musics;
	}
	
	public static Gongju setGongju(SQLiteDatabase db){
		if(database==null){
			database = db;
		}
		if(gongju==null){
			synchronized (Gongju.class) {
				if(gongju==null){
					gongju=new Gongju();
				}
			}
		}
		return gongju;
	}
	
	public static SQLiteDatabase getDB(){
		return database;
	}
	
}
