package com.adui.musicplayer.activity;

import com.adui.mmusic.R;
import com.adui.musicplayer.db.DBHelper;
import com.adui.musicplayer.db.Loader_User;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.model.Gongju;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ReceiverCallNotAllowedException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * 广告活动
 * @author user
 *
 */
public class ActivityOfgg extends Activity {

	private Receiver receiver;
	private static SQLiteDatabase db;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guanggao_layout);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.adui.musicplayer.aaa");
		receiver = new Receiver();
		registerReceiver(receiver, intentFilter);
		Log.d("wwwe", "注册广告完成");	
		
		final DBHelper dbHelper = new DBHelper(ActivityOfgg.this, "MusicStore.db", null, 1);
		
		db = dbHelper.getWritableDatabase();
		Gongju.setGongju(db);
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d("wwwe", "run");	
				MusicForDB.loadMusic(ActivityOfgg.this,dbHelper);
			}
		}).start();
		
	}

	private void start(){
		Log.d("wwwe", "start()");
		Intent i = new Intent(ActivityOfgg.this,MmainActivity.class);
		startActivity(i);
		finish();
	}
	
	private class Receiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.d("wwwe", "onReceive");
			if(MusicForDB.getList()!=null){
				start();
			}
			
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
}
