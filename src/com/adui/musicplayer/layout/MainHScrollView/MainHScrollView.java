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
	 * �󲼾�
	 */
	private ViewGroup left;
	/**
	 * �Ҳ���
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
	 * �򿪲˵� ���ж�isOpen=�� ��Ϊfalse�����ʾû�д� ��� Ȼ��״̬Ϊ-���˲˵�
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

	// �ر�
	public void closeMenu() {
		Log.d("CAOLL", "is closeMenu");
		Log.d("HZ", "closeMenu + " + isOpen);
		if (!isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = false;
		
		// mMenu.setVisibility(View.GONE);
	}
	
	//����true - ���ӵĲ��ܻ�����������Զ�
	//����false- ���ӵĿ��Ի����������ɶ�
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub	
		//����˵�item���ˣ���isOpen��true���������ܶ����򷵻�false
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
	 * �л��˵�
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}
}
