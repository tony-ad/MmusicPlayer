package com.adui.musicplayer.activity;

import java.util.ArrayList;
import java.util.List;

import com.adui.mmusic.R;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.layout.HScrollView.HorizontalScroll;
import com.adui.musicplayer.layout.HScrollView.MyPager;
import com.adui.musicplayer.layout.HScrollView.Menu_1.Menu_1;
import com.adui.musicplayer.layout.HScrollView.Menu_2.Frag_AboutThis;
import com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_1;
import com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_2;
import com.adui.musicplayer.layout.Other.frag_login;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MmainActivity extends FragmentActivity {

	private HorizontalScroll HScroll;
	private MyPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragemnts;
	private Fragment f1;
	private Fragment f2;
	private ViewGroup ArcMenu;

	private Fragment frag_menu1;

	private Fragment frag_login;
	private Fragment frag_aboutMe;

	private ReceiverTo_OpenMenu2 receiver_openMenu2;

	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
		initViewPager();
		initOtherView();
		initReceiver();
	}

	private void initReceiver() {
		// TODO Auto-generated method stub
		IntentFilter intentFilter = new IntentFilter("Open_To.Menu2");
		receiver_openMenu2 = new ReceiverTo_OpenMenu2();
		registerReceiver(receiver_openMenu2, intentFilter);
	}

	private void initOtherView() {
		// TODO Auto-generated method stub
		HScroll = (HorizontalScroll) findViewById(R.id.id_HScroll);
		frag_menu1 = new Menu_1(MmainActivity.this);
		getSupportFragmentManager().beginTransaction().add(R.id.HScroll_1, frag_menu1).commit();
	}

	/**
	 * 菜单1的开关
	 */
	public void menu_1_OpenAndClose() {
		HScroll.toggle();
	}

	/**
	 * 菜单2的开关
	 */
	public void menu_2_OpenAndClose() {
		HScroll.goToMenu_2();
	}

	/**
	 * 初始化碎片、适配器
	 */
	private void init() {
		// TODO Auto-generated method stub
		mFragemnts = new ArrayList<Fragment>();
		f1 = new frag_1(this);
		mFragemnts.add(f1);
		f2 = new frag_2(this);
		Log.d("CCC", "f2 is null? " + (f2 == null));

		mFragemnts.add(f2);
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mFragemnts.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mFragemnts.get(arg0);
			}
		};
	}

	/**
	 * 初始化ViewPager
	 */
	private void initViewPager() {
		// TODO Auto-generated method stub
		mViewPager = (MyPager) findViewById(R.id.mViewPager);
		mViewPager.setAdapter(mAdapter);
	}

	public void aaa() {
		ArcMenu = ((frag_2) f2).giveArcMenu();
		mViewPager.giveArcMenu(ArcMenu);
	}

	/**
	 * 按下键位
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (Back_one() == false) {
				Log.d("view", "show ExitDialog");
				ExitDialog(this).show();
				return true;
			} else {
				return true;
			}

		}

		return super.onKeyDown(keyCode, event);
	}

	private boolean Back_one() {
		if (HScroll.getisOpen1() == true) {
			if (HScroll.getisOpen2() == true) {
				menu_2_OpenAndClose();
				Log.d("view", "close menu_2");
				return true;
			}
			menu_1_OpenAndClose();
			Log.d("view", "close menu_1");
			return true;
		}
		return false;
	}

	/**
	 * 弹出对话框
	 * 
	 * @param context
	 * @return
	 */
	private Dialog ExitDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Mmusic").setMessage("确定退出此程序吗？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		return builder.create();
	}

	private class ReceiverTo_OpenMenu2 extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			String values = intent.getStringExtra("values");
			String vString = intent.getStringExtra("GoOrBack");
			Log.d("监听跳转菜单2", "GoOrBack is " + vString);
			if (vString.equals("back")) {
				Back_one();
			} else if (vString.equals("go")) {
				menu_2_OpenAndClose();
				Log.d("TTT", "values is " + values);
				add_Menu2_Frag(values);
			}else if (vString.equals("not")) {
				add_Menu2_Frag(values);
			}
		}

	}

	/**
	 * 加载菜单2的碎片
	 * 
	 * @param values
	 */
	private void add_Menu2_Frag(String values) {
		FragmentManager fManager = getSupportFragmentManager();
		FragmentTransaction fTransaction = fManager.beginTransaction();
		Hide_Menu2_Frag(fTransaction);

		switch (values) {
		case "login":
			if (frag_login == null) {
				frag_login = new frag_login(MmainActivity.this);
				fTransaction.add(R.id.HScroll_2, frag_login);
			} else {
				fTransaction.show(frag_login);
			}
			break;

		case "aboutMe":
			if (frag_aboutMe == null) {
				frag_aboutMe = new Frag_AboutThis(MmainActivity.this);
				fTransaction.add(R.id.HScroll_2, frag_aboutMe);
			} else {
				fTransaction.show(frag_aboutMe);
			}
			break;
		case "TimingClosing":
			showDialog(1);

			break;

		default:
			break;
		}

		fTransaction.commit();
	}

	private void Hide_Menu2_Frag(FragmentTransaction fTransaction) {
		// TODO Auto-generated method stub
		if (frag_login != null) {
			fTransaction.hide(frag_login);
		}
		if (frag_aboutMe != null) {
			fTransaction.hide(frag_aboutMe);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver_openMenu2);
	}

	public void onMenu2() {
		HScroll.onMenu2();
	}

	
	
	/**
	 * 1是睡眠时间的DiaLog
	 */
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case 1:
			Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("睡眠时间");
			final ChoiceOnClickListener choiceListener = new ChoiceOnClickListener();
			builder.setSingleChoiceItems(R.array.time, 0, choiceListener);
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {

						//按确定之后触发
						
						// 位置
						int choiceWhich = choiceListener.getWhich();

						// 按钮的内容
//						String hobbyStr = getResources().getStringArray(R.array.time)[choiceWhich];
						switch (choiceWhich) {
						case 0:
							setMsg(1);
							break;
						case 1:
							setMsg(15);
							break;
						case 2:
							setMsg(30);
							break;
						case 3:
							setMsg(60);
							break;
						case 4:
							setMsg(0);
							break;

						default:
							break;
						}
						Log.d("timeee", "onClick -1");
					}
				
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				}
			});
			
			dialog = builder.create();
			break;
		}
		return dialog;
	}

	/**
	 * 睡眠时间的设定
	 * @param min
	 */
	private void setMsg(int min){
		int time = 1000 * 60 * min;
		Log.d("timeee", "time is " + time);
		handler.removeMessages(1);
		
		
		if(time > 0){
			Log.d("定时关闭", min+"分钟后关闭！");
		Toast.makeText(MmainActivity.this, min+"分钟后关闭！", Toast.LENGTH_SHORT).show();
		handler.sendEmptyMessageDelayed(1, time); 
		}else {
			Log.d("定时关闭", "定时关闭已取消！");
			Toast.makeText(MmainActivity.this, "定时关闭已取消！", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 点击 - 睡眠时间 - 选择框内的按钮时触发
	 * @author user
	 *
	 */
	private class ChoiceOnClickListener implements DialogInterface.OnClickListener {
		private int which = 0;
		@Override
		public void onClick(DialogInterface dialogInterface, int which) {
			//选择框内的按钮时触发
			this.which = which;
			Log.d("timeee", "onClick -2");
		}
		public int getWhich() {
			return which;
		}
	}

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
				MmainActivity.this.finish();
			}
		};
	};
	
}
