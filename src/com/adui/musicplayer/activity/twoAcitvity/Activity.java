package com.adui.musicplayer.activity.twoAcitvity;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import com.adui.mmusic.R;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.layout.HScrollView.HorizontalScroll;
import com.adui.musicplayer.layout.HScrollView.MyPager;
import com.adui.musicplayer.layout.HScrollView.leftMenu.menubar;
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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * 此活动
 * HorizontalScrollView中有菜单栏和ViewPager
 * ViewPager中有2个碎片
 * 
 * @author user
 *
 */
public class Activity extends FragmentActivity{
	
	private HorizontalScroll Hview;
	private MyPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragemnts;
		
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_2);
		Hview = (HorizontalScroll) findViewById(R.id.id_HScroll);
		MusicForDB.loadMusic(this);
		init();
		initViewPager();
		initMenuFragment();
	}
	
	/**
	 * 初始化菜单碎片
	 */
	private void initMenuFragment() {
		// TODO Auto-generated method stub
		Fragment menu_frag = new menubar(Activity.this);
		getSupportFragmentManager().beginTransaction().add(R.id.layout_menu, menu_frag).commit();
	}
	
	/**
	 * 切换菜单
	 * @param view
	 */
	public void toggleMenu(View view)
	{
		Hview.toggle();
	}
	
	/**
	 * 初始化碎片、适配器
	 */
	private void init() {
		// TODO Auto-generated method stub
		mFragemnts=new ArrayList<Fragment>();
		Fragment f1 = new frag_1(this);
		mFragemnts.add(f1);
		Fragment f2 = new frag_2(this);
		mFragemnts.add(f2);
		mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
			
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
		mViewPager=(MyPager) findViewById(R.id.mViewPager);
		mViewPager.setAdapter(mAdapter);
		Hview.setMyPager(mViewPager);
		
	}

	/**
	 * 按下键位
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
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
