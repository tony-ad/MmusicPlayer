package com.adui.musicplayer.activity;

import java.util.ArrayList;
import java.util.List;

import com.adui.mmusic.R;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.layout.HScrollView.HorizontalScroll;
import com.adui.musicplayer.layout.HScrollView.MyPager;
import com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_1;
import com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_2;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
	private Button b;
	private Button b2;
	private Fragment f1;
	private Fragment f2;
	private ViewGroup ArcMenu;
	
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		b=(Button) findViewById(R.id.ceButon);
		b2=(Button) findViewById(R.id.ceButon2);
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MmainActivity.this, "you clicked this button", Toast.LENGTH_LONG).show();
			}
		});
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HScroll.goToMenu_2();
			}
		});
		init();
		initViewPager();
		initOtherView();
	}

	private void initOtherView() {
		// TODO Auto-generated method stub
		HScroll = (HorizontalScroll) findViewById(R.id.id_HScroll);
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
		Log.d("CCC", "f2 is null? " + (f2==null));
		
		
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
	
	public void aaa(){
		ArcMenu = ((frag_2) f2).giveArcMenu();
		mViewPager.giveArcMenu(ArcMenu);
	}
	
	
	/**
	 * 按下键位
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (HScroll.getisOpen1()==true) {
				if (HScroll.getisOpen2() == true) {
					menu_2_OpenAndClose();
					Log.d("view", "close menu_2");
					return true;
				}
				menu_1_OpenAndClose();
				Log.d("view", "close menu_1");
				return true;
			}
			Log.d("view", "show ExitDialog");
			ExitDialog(this).show();
			return true;
		}

		return super.onKeyDown(keyCode, event);
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

}
