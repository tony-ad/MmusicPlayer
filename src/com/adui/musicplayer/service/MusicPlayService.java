package com.adui.musicplayer.service;

import java.util.List;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.model.Music;
import com.adui.mmusic.R;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.Log;
import android.widget.Toast;

public class MusicPlayService extends Service implements OnCompletionListener {

	private MediaPlayer mediaPlayer = new MediaPlayer();  //创建MediaPlayer
	private int currIndex = -1; // 正在播放歌曲的索引
	private int choose = -2; // 准备播放歌曲的索引
	private int musicLsize; //音乐列表的数目size
	private DownloadBinder mBinder = new DownloadBinder(); //用于绑定活动时，提供部分方法
	private Intent itml; //to MusicLokk
	private Intent itbr; //to Broadcast
	private String path; //播放歌曲的路径
	private List<Music> listForMusic; //音乐列表内容
	private int ListenerForModel=0; //播放模式
	private boolean isNext=true;
	private ServiceBR sbr; //广播
	
	/**
	 * 与活动绑定之后，可提供方法
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	public class DownloadBinder extends Binder {
		/**
		 * UI按钮-点击下一首时
		 */
		public void nextt(){
			isNext=true;
			Log.d("item click", "nextt");
			if(ListenerForModel==2){
				choose= (int) (Math.random()*musicLsize);
				start();
			}else {
				onModel12();
			}
		}
		
		/**
		 * 自动播放时（播放完毕，放下一首歌）
		 */
		public void next() {
			Log.d("item click", "next");
			switch(ListenerForModel){
			case 1:
				start();
				break;
			case 0:
				onModel12();
				break;
			case 2:
				choose= (int) (Math.random()*musicLsize);
				start();
				break;
			}
		}

		
		/**
		 * 播放上一首歌
		 */
		public void last() {
			Log.d("item click", "last");
			if (currIndex - 1 > -1) {
				choose = currIndex - 1;
				start();
			} else {
				Toast.makeText(MusicPlayService.this, "目前已经是第一首歌曲", Toast.LENGTH_SHORT).show();
			}
		}

		/**
		 * 活动创建完成并绑定服务完成时，发送广播-更新UI
		 */
		public void sendgb(){
			Log.d("ZZ", "sendgb");
			itbr = new Intent("com.adui.musicplayer.CHANGE_MUSIC_CONTENT");
			itbr.putExtra("urll", currIndex); //传递music的位置
			sendBroadcast(itbr);
		}

		/**
		 * 拖动条变换时，歌曲跟着变
		 */
		public void changeSeekBar(int progress) {
			mediaPlayer.seekTo(progress * 1000);
		}

		/**
		 * 歌曲当前时间
		 * 
		 * @return
		 */
		public int musicTime() {
			return mediaPlayer.getCurrentPosition();
		}

		/**
		 * 开始播放和暂停播放
		 */
		public void StartOrPause() {
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
			} else if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
			}
		}
		
		public boolean isPlaying(){
			Boolean a = null;
			if (!mediaPlayer.isPlaying()) {
				a=false;
			} else if (mediaPlayer.isPlaying()) {
				a=true;
			}
			return a;
		}

	}

	/**
	 * 服务创建时调用，创建前台服务
	 */
	@Override
	public void onCreate() {
		
		// TODO Auto-generated method stub
		super.onCreate();

		mediaPlayer.setOnCompletionListener(this);

//		Notification n = null;
//
//		Builder builder = new Notification.Builder(this);
//
//		itml = new Intent(this, MusicLook.class);
//		itml.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, itml, 0);
//
//		builder
//		.setContentIntent(contentIntent)
//		.setSmallIcon(R.drawable.logo)
//		.setWhen(System.currentTimeMillis())
//		.setAutoCancel(false)
//		.setTicker("欢迎使用Mmusic")
//		.setAutoCancel(false)
//		.setContentTitle("Mmusic")
//		.setContentText("");
//
//		n = builder.getNotification();
//
//		startForeground(1, n);

		//注册广播
		IntentFilter br = new IntentFilter();
		br.addAction("com.adui.musicService.COME");
		sbr = new ServiceBR();
		registerReceiver(sbr, br);
	}

	/**
	 * 服务销毁时调用
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
//		unregisterReceiver(sbr);
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		if(sbr !=null){
			unregisterReceiver(sbr);
		}
		super.onDestroy();
	}

	/**
	 * 模式1、2的“下一首歌”的播放逻辑
	 */
	private void onModel12(){
		
		//如果有下一首，就播放下一首；没有则从第一首开始
		
		Log.d("item click", "onModel");
		if (currIndex + 1 < musicLsize) {
			choose = currIndex + 1;
			start();
		} else {
			choose=0;
			start();
			Toast.makeText(MusicPlayService.this, "不存在下一首歌，已从第一首开始播放！", Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * 服务启动时调用
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("ZZ", "onStartCommand");
		
		if(mediaPlayer.isPlaying()){
		mediaPlayer.pause();
		}
		listForMusic=MusicForDB.getList();
		musicLsize = listForMusic.size();
		//获取列表size + 点击的音乐的位置pos + 音乐列表
		int pos = intent.getIntExtra("pos",0);
		isNext=false;
		choose = pos;		 
		Log.d("item click", "choose :"+choose);
		start();
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 播放音乐逻辑
	 */
	private void start() {
		Log.d("item click", "服务 - start");
				if (choose == currIndex) {
				// 若是true，表示是同一首歌
					if(mediaPlayer.isPlaying()){
						
					}else {
						mBinder.sendgb();
						mediaPlayer.start();
					}
				} else {
				// 不是同一个歌曲，播放新歌曲

					currIndex = choose;				
					//获取路径
					Log.d("item click", "currIndex :"+currIndex);
					path = listForMusic.get(currIndex).getUrl();	
					Log.d("item click", "path :"+path);
					Log.d("ZZ", "start - path" + path);
					//初始化
					mediaPlayer.reset();
					//发送广播，提醒活动更新UI
					mBinder.sendgb();
					try {
						mediaPlayer.setDataSource(path);
						mediaPlayer.prepare();
						mediaPlayer.start();

					} catch (Exception e) {
						// TODO: handle exception
					}
				}

			}
		

	/**
	 * 当歌曲完毕时调用
	 */
	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		if(isNext){
			mBinder.next();
		}else {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!arg0.isPlaying()){
				mBinder.next();
				isNext=true;
			}
		}
	}
	
	/**
	 * 开启服务
	 * @param context
	 * @param Size
	 * @param pos
	 */
	public static void openService(Context context,int pos){
		Intent i = new Intent(context, MusicPlayService.class);
		i.putExtra("pos", pos);
		i.putExtra("isNext", false);
		context.startService(i);
	}
	
	public class ServiceBR extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			ListenerForModel=intent.getIntExtra("model", 0);
		}
	}

	
}
