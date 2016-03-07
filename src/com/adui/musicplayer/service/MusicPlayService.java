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

	private MediaPlayer mediaPlayer = new MediaPlayer();  //����MediaPlayer
	private int currIndex = -1; // ���ڲ��Ÿ���������
	private int choose = -2; // ׼�����Ÿ���������
	private int musicLsize; //�����б����Ŀsize
	private DownloadBinder mBinder = new DownloadBinder(); //���ڰ󶨻ʱ���ṩ���ַ���
	private Intent itml; //to MusicLokk
	private Intent itbr; //to Broadcast
	private String path; //���Ÿ�����·��
	private List<Music> listForMusic; //�����б�����
	private int ListenerForModel=0; //����ģʽ
	private boolean isNext=true;
	private ServiceBR sbr; //�㲥
	
	/**
	 * ����֮�󣬿��ṩ����
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	public class DownloadBinder extends Binder {
		/**
		 * UI��ť-�����һ��ʱ
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
		 * �Զ�����ʱ��������ϣ�����һ�׸裩
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
		 * ������һ�׸�
		 */
		public void last() {
			Log.d("item click", "last");
			if (currIndex - 1 > -1) {
				choose = currIndex - 1;
				start();
			} else {
				Toast.makeText(MusicPlayService.this, "Ŀǰ�Ѿ��ǵ�һ�׸���", Toast.LENGTH_SHORT).show();
			}
		}

		/**
		 * �������ɲ��󶨷������ʱ�����͹㲥-����UI
		 */
		public void sendgb(){
			Log.d("ZZ", "sendgb");
			itbr = new Intent("com.adui.musicplayer.CHANGE_MUSIC_CONTENT");
			itbr.putExtra("urll", currIndex); //����music��λ��
			sendBroadcast(itbr);
		}

		/**
		 * �϶����任ʱ���������ű�
		 */
		public void changeSeekBar(int progress) {
			mediaPlayer.seekTo(progress * 1000);
		}

		/**
		 * ������ǰʱ��
		 * 
		 * @return
		 */
		public int musicTime() {
			return mediaPlayer.getCurrentPosition();
		}

		/**
		 * ��ʼ���ź���ͣ����
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
	 * ���񴴽�ʱ���ã�����ǰ̨����
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
//		.setTicker("��ӭʹ��Mmusic")
//		.setAutoCancel(false)
//		.setContentTitle("Mmusic")
//		.setContentText("");
//
//		n = builder.getNotification();
//
//		startForeground(1, n);

		//ע��㲥
		IntentFilter br = new IntentFilter();
		br.addAction("com.adui.musicService.COME");
		sbr = new ServiceBR();
		registerReceiver(sbr, br);
	}

	/**
	 * ��������ʱ����
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
	 * ģʽ1��2�ġ���һ�׸衱�Ĳ����߼�
	 */
	private void onModel12(){
		
		//�������һ�ף��Ͳ�����һ�ף�û����ӵ�һ�׿�ʼ
		
		Log.d("item click", "onModel");
		if (currIndex + 1 < musicLsize) {
			choose = currIndex + 1;
			start();
		} else {
			choose=0;
			start();
			Toast.makeText(MusicPlayService.this, "��������һ�׸裬�Ѵӵ�һ�׿�ʼ���ţ�", Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * ��������ʱ����
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
		//��ȡ�б�size + ��������ֵ�λ��pos + �����б�
		int pos = intent.getIntExtra("pos",0);
		isNext=false;
		choose = pos;		 
		Log.d("item click", "choose :"+choose);
		start();
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * ���������߼�
	 */
	private void start() {
		Log.d("item click", "���� - start");
				if (choose == currIndex) {
				// ����true����ʾ��ͬһ�׸�
					if(mediaPlayer.isPlaying()){
						
					}else {
						mBinder.sendgb();
						mediaPlayer.start();
					}
				} else {
				// ����ͬһ�������������¸���

					currIndex = choose;				
					//��ȡ·��
					Log.d("item click", "currIndex :"+currIndex);
					path = listForMusic.get(currIndex).getUrl();	
					Log.d("item click", "path :"+path);
					Log.d("ZZ", "start - path" + path);
					//��ʼ��
					mediaPlayer.reset();
					//���͹㲥�����ѻ����UI
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
	 * ���������ʱ����
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
	 * ��������
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
