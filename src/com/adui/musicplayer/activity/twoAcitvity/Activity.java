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
 * �˻
 * HorizontalScrollView���в˵�����ViewPager
 * ViewPager����2����Ƭ
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
	 * ��ʼ���˵���Ƭ
	 */
	private void initMenuFragment() {
		// TODO Auto-generated method stub
		Fragment menu_frag = new menubar(Activity.this);
		getSupportFragmentManager().beginTransaction().add(R.id.layout_menu, menu_frag).commit();
	}
	
	/**
	 * �л��˵�
	 * @param view
	 */
	public void toggleMenu(View view)
	{
		Hview.toggle();
	}
	
	/**
	 * ��ʼ����Ƭ��������
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
	 * ��ʼ��ViewPager
	 */
	private void initViewPager() {
		// TODO Auto-generated method stub
		mViewPager=(MyPager) findViewById(R.id.mViewPager);
		mViewPager.setAdapter(mAdapter);
		Hview.setMyPager(mViewPager);
		
	}

	/**
	 * ���¼�λ
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ExitDialog(this).show();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * �����Ի���
	 * 
	 * @param context
	 * @return
	 */
	private Dialog ExitDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Mmusic").setMessage("ȷ���˳��˳�����");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				finish();
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		return builder.create();
	}
}
