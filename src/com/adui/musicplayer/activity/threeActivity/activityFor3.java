package com.adui.musicplayer.activity.threeActivity;

import com.adui.mmusic.R;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.layout.MainHScrollView.MainHScrollView;
import com.adui.musicplayer.layout.MainHScrollView.mainleft_frag;
import com.adui.musicplayer.layout.MainHScrollView.mainright_frag;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

/**
 * HScrollView包含
 * 子HScrollView+MenuItemLayout
 * 子HScrollView包含Menu+ViewPager
 * @author user
 *
 */
public class activityFor3 extends FragmentActivity {
	private Fragment fragment1;
	private Fragment fragment2;
	private MainHScrollView mainHScroll;
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_formain);
		MusicForDB.loadMusic(this);
		mainHScroll=(MainHScrollView) findViewById(R.id.id_mainHSV);
		initView();
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		fragment1 = new mainleft_frag(activityFor3.this);
		fragment2 = new mainright_frag(activityFor3.this);
		getSupportFragmentManager().beginTransaction().add(R.id.id_main_left, fragment1).commit();
		getSupportFragmentManager().beginTransaction().add(R.id.id_main_right, fragment2).commit();
	}

	/**
	 * 切换mainHS -> 菜单
	 * 并加载相应的内容
	 * @param view
	 */
	public void toggleMenu(View view)
	{
		mainHScroll.toggle();
		((mainright_frag) fragment2).showFrag();
	}
	
	/**
	 *  ViewPager->菜单栏
	 */
	
	
	/**
	 * 按下键位
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		boolean HSVzisOpen = ((mainleft_frag) fragment1).getisOpen();
		
		boolean HSVisOpen = mainHScroll.isOpen();
		
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				if(HSVisOpen){
					mainHScroll.toggle();
					return true;
				}
				if(HSVzisOpen){
					((mainleft_frag) fragment1).changeMenu();
					return true;
				}
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
