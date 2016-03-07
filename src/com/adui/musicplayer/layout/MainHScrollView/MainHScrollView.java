package com.adui.musicplayer.layout.MainHScrollView;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class MainHScrollView extends HorizontalScrollView {

	private int mSreeWidth;
	private boolean once=false;
	
	
	private int mLeft;

	private int mRight;
	private LinearLayout mWapper;
	/**
	 * 左布局
	 */
	private ViewGroup left;
	/**
	 * 右布局
	 */
	private ViewGroup right;
	private boolean isOpen;
	
	
	
	public boolean isOpen() {
		return isOpen;
	}

	public MainHScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		mSreeWidth = dm.widthPixels;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if (!once) {
			mWapper = (LinearLayout) getChildAt(0);
			left = (ViewGroup) mWapper.getChildAt(0);
			right = (ViewGroup) mWapper.getChildAt(1);
			mLeft = left.getLayoutParams().width = mSreeWidth;
			right.getLayoutParams().width=mLeft;
			once = true;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(0, 0);
		}
	}
	

	/**
	 * 打开菜单 先判断isOpen=？ 若为false，则表示没有打开 则打开 然后状态为-打开了菜单
	 */
	public void openMenu() {
		Log.d("CAOLL", "is openMenu");
		Log.d("HZ", "openMenu + " + isOpen);
		if (isOpen)
			return;
		this.smoothScrollTo(mLeft, 0);
		isOpen = true;
		// mMenu.setVisibility(View.VISIBLE);
	}

	// 关闭
	public void closeMenu() {
		Log.d("CAOLL", "is closeMenu");
		Log.d("HZ", "closeMenu + " + isOpen);
		if (!isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = false;
		
		// mMenu.setVisibility(View.GONE);
	}
	
	//返回true - 则子的不能滑动，自身可以动
	//返回false- 则子的可以滑动，自身不可动
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub	
		//如果菜单item打开了，即isOpen是true，则自身不能动，则返回false
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(isOpen==true){
			return true;
		}
		
		
		
		return super.onTouchEvent(ev);
	}
	
	/**
	 * 切换菜单
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}
}
