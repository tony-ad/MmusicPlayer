package com.adui.musicplayer.activity;

import java.util.List;

import com.adui.mmusic.R;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.model.Music;
import com.adui.musicplayer.service.MusicPlayService;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class frag_2 extends Fragment implements OnClickListener, OnSeekBarChangeListener {

	private View v;
	private Context mContext;
	private Button music;
	private Button next;
	private Button last;
	private SeekBar seekBar;
	private TextView tv1;
	private TextView tv2;
	private TextView tvMusicName;
	private TextView tvSinger;
	private ImageView iv;
	private RotateAnimation ra;
	public List<Music> musicL;

	private final static int setSeekBar = 1; // ���ڸ��¸���ʱ��
	private Message msg; // ���ڸ��¸���ʱ��

	private Intent bindI; // �󶨷���󣬿ɵõ����еķ���

	private MyThread myThread = new MyThread(); // �������߳�
	private Thread thread; // �������߳�
	private boolean booleanForSeekBarThread = true;// ���ƽ������̱߳��

	// ����2����һ���ǹ㲥����һ���Ƿ��͹㲥������action
	private IntentFilter itbs;
	private MusicContentReceiver mcr;

	// musicSOP��ֵ��trueʱ����ʾ�������ڲ���
	private boolean musicStartOrPause = true;

	public frag_2(Context Context) {
		this.mContext = Context;
	}

	// �󶨷���
	private MusicPlayService.DownloadBinder binder;
	private ServiceConnection Connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			binder = (MusicPlayService.DownloadBinder) service;
			binder.sendgb();
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.activity_musicmaterial, container, false);
		init();
		musicL = MusicForDB.getList();
		return v;
	}

	/**
	 * �󶨷���ע��㲥������
	 */
	private void init(){
		// TODO Auto-generated method stub
		// ע��㲥���˹㲥mcr����ֵΪaction�Ĺ㲥
		itbs = new IntentFilter();
		itbs.addAction("com.adui.musicplayer.CHANGE_MUSIC_CONTENT");
		mcr = new MusicContentReceiver();
		mContext.registerReceiver(mcr, itbs);
		// �󶨷���
		bindI = new Intent(mContext, MusicPlayService.class);
		mContext.bindService(bindI, Connection, 0);
	}

	/**
	 * ��ʼ��UI�ؼ�
	 */
	private void initView() {
		music = (Button) v.findViewById(R.id.music);
		music.setOnClickListener(this);
		next = (Button) v.findViewById(R.id.next);
		next.setOnClickListener(this);
		last = (Button) v.findViewById(R.id.last);
		last.setOnClickListener(this);
		tv1 = (TextView) v.findViewById(R.id.timeF);
		tv2 = (TextView) v.findViewById(R.id.timeL);
		iv = (ImageView) v.findViewById(R.id.image);
		tvMusicName = (TextView) v.findViewById(R.id.text_musicname);
		tvSinger = (TextView) v.findViewById(R.id.text_singername);
		seekBar = (SeekBar) v.findViewById(R.id.seekbar);
		seekBar.setOnSeekBarChangeListener(this);
	}

	// ֹͣ�϶�
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		binder.StartOrPause();
	}

	// ��ʼ�϶�(�϶�ing)
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		// ֹͣˢ��
		binder.StartOrPause();
	}

	// �϶�֮��
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		if (fromUser == true) {
			binder.changeSeekBar(progress);
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.music:
			// �ж����ֵĲ���״̬��ִ����ͣ��ʼ
			binder.StartOrPause();
			if (!binder.isPlaying()) {
				music.setBackgroundResource(R.drawable.starttu);
				booleanForSeekBarThread = false;
				musicStartOrPause = false;
				ra.cancel();
			} else {
				music.setBackgroundResource(R.drawable.stoptu);
				refresh();
				musicStartOrPause = true;
				ra.start();
			}
			break;
		case R.id.last:
			// ������һ��
			booleanForSeekBarThread = false;
			binder.last();
			break;
		case R.id.next:
			// ������һ��
			booleanForSeekBarThread = false;
			binder.nextt();
			break;

		}
	}

	/**
	 * �Զ�����seekbar�϶���
	 */
	private void refresh() {
		Log.d("ZZ", "refresh");
		thread = new Thread(myThread);
		thread.start();
	}

	class MyThread implements Runnable {
		public void run() {
			shezhiSeekBar();
		}
	}

	private void shezhiSeekBar() {
		booleanForSeekBarThread = true;
		while (booleanForSeekBarThread) {
			if (seekBar.getProgress() < seekBar.getMax()) {
				seekBar.setProgress(binder.musicTime() / 1000);
				msg = handler.obtainMessage(setSeekBar, toTime(binder.musicTime()));
				handler.sendMessage(msg);
				try {
					// ��ͣ1��
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			} else {
				booleanForSeekBarThread = false;
			}
		}
	}

	/**
	 * �첽��Ϣ�������
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case setSeekBar:
				tv1.setText(msg.obj.toString());
				break;
			}
		}
	};

	/**
	 * ת��ʱ��Ϊ 00:00��ʽ
	 * 
	 * @param time
	 * @return
	 */
	public String toTime(int time) {
		int minute = time / 1000 / 60;
		int s = time / 1000 % 60;
		String mm = null;
		String ss = null;
		if (minute < 10) {
			mm = "0" + minute;
		} else {
			mm = minute + "";
		}
		if (s < 10) {
			ss = "0" + s;
		} else {
			ss = "" + s;
		}
		return mm + ":" + ss;
	}

	/**
	 * ����UI
	 */
	public void Set(Music Amusic) {
		// TODO Auto-generated method stub
		Log.d("ZZ", "Set");
		changeTextContent(Amusic);
		initSeekBar(Amusic);
		booleanForSeekBarThread = false;
		music.setBackgroundResource(R.drawable.stoptu);
		musicStartOrPause = true;
		refresh();
	}

	/**
	 * �����ı�����
	 */
	private void changeTextContent(Music maa) {
		Log.d("ZZ", "changeTextContent");
		tv2.setText(toTime(maa.getTime()));
		tvMusicName.setText(maa.getMusicN());
		tvSinger.setText(maa.getName());
		if (maa.getBm() == null) {
			iv.setImageResource(R.drawable.moren2);
		} else {
			iv.setImageBitmap(maa.getBm());
		}
		ra = new RotateAnimation(0, 359,iv.getWidth()/2,iv.getHeight()/2);
		ra.setDuration(30000);
		ra.setRepeatCount(maa.getTime() / 30000);
		LinearInterpolator lir = new LinearInterpolator();
		ra.setInterpolator(lir);
		iv.setAnimation(ra);
	}

	/**
	 * ��ʼ��SeekBar ��������Maxֵ������λ����0��ʼ������tv1���ı�����
	 */
	private void initSeekBar(Music aO) {
		Log.d("ZZ", "initSeekBar");
		seekBar.setMax(aO.getTime() / 1000);
		seekBar.setProgress(0);
		tv1.setText("00:00");
	}

	/**
	 * �㲥������������ʵ��UI�ĸ���
	 * 
	 * @author user
	 *
	 */
	public class MusicContentReceiver extends BroadcastReceiver {

		private Bundle bundle;

		@Override
		public void onReceive(Context context, Intent intent) {
			bundle = intent.getExtras();
//			String Url = bundle.getString("urll");
			int pos = bundle.getInt("urll"); //���ݽ���������λ��
		
//			Music musicA = MusicForDB.oneMusic(mContext, Url);
			
			Music musicB = musicL.get(pos); //����λ�û�ȡ��ǰ����Music
			initView();
			Set(musicB);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		booleanForSeekBarThread = false;
		mContext.unregisterReceiver(mcr);
		mContext.unbindService(Connection);
		Intent asd = new Intent(mContext, MusicPlayService.class);
		mContext.stopService(asd);
	}

}
