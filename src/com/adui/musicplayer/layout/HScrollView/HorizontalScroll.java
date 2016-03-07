package com.adui.musicplayer.layout.HScrollView;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class HorizontalScroll extends HorizontalScrollView {

	private int mSreeWidth;
	private boolean once;
	private LinearLayout mWapper;
	private int MenuWidth;
	private int ContentWidth;
	private int MenuLightPad;

	/**
	 * VPnoplay=false - VP����HSV����
	 */
	private boolean VPnoplay = false;

	private MyPager myPager;

	public void setMyPager(MyPager myPager) {
		this.myPager = myPager;
	}

	/**
	 * �Ƿ�򿪲˵� false-δ��-δ��ʾ true -����-��ʾ�˵�
	 */
	private boolean isOpen;
	/**
	 * Fragment
	 */
	private ViewGroup mMenu;
	/**
	 * LinearLayout - ViewPgaer
	 */
	private ViewGroup mContent;

	public HorizontalScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		mSreeWidth = dm.widthPixels;
		MenuLightPad = mSreeWidth / 3;
	}

	public boolean isOpen() {
		return isOpen;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if (!once) {
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			MenuWidth = mMenu.getLayoutParams().width = mSreeWidth - MenuLightPad;
			mContent.getLayoutParams().width = mSreeWidth;
			once = true;

		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(MenuWidth, 0);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		return true;
	}


	 public boolean onTouchEvent(MotionEvent ev) {

	 int action = ev.getAction();
	 // ����˵����ˣ��Ͳ���ʹ��ViewPager�����������ػ
		 // ����ViewPager���ܻ���������ֹͨ����������ViewPager��ʾ���ڶ�ҳ
		 if (isOpen == true) {
		 this.onInterceptTouchEvent(ev);
		 ViewGroup child = (ViewGroup) getChildAt(0);
		 View menu = child.getChildAt(0);
		 requestDisallowInterceptTouchEvent(true);
		 menu.dispatchTouchEvent(ev);
		 }
		 // ����˵�δ�򿪣����жϵ�ǰViewPager��ҳ���Ƕ��٣�0 or 1��
		 else {
		 if (myPager.getCurrentItem() == 0) {
		 // ���ҳ����ʾ����1����
		 // ���¼��ַ�����View�У����Ի���ViewPager���������ز������ɻ���Scroll��
		 // �����Ի���ViewPager��ȥҳ��2�����ֿ��Ի���Scroll�����˵���
		 ViewGroup child = (ViewGroup) getChildAt(0);
		 View childPager = child.getChildAt(1);
		 requestDisallowInterceptTouchEvent(true);
		 childPager.dispatchTouchEvent(ev);
		 } else {
		 // ���ҳ����ʾ����2��
		 // �Ͱ��¼��ַ�����View�� - ��ViewPager���Ի���
		 // ������true�������¼� - ��Scroll���ɻ�������ֹ��ҳ��2�򿪲˵�
		 ViewGroup child = (ViewGroup) getChildAt(0);
		 View childPager = child.getChildAt(1);
		 requestDisallowInterceptTouchEvent(true);
		 childPager.dispatchTouchEvent(ev);
		 return true;
		 }
		 }
	 switch (action) {
	
	 case MotionEvent.ACTION_DOWN:

	
	
	 break;
	
	 case MotionEvent.ACTION_MOVE:


	
	 break;
	
	 case MotionEvent.ACTION_UP:
	 // ��������ߵĿ��
	 int scrollX = getScrollX();
	 if (scrollX >= MenuWidth / 2) {
	 //�ر�
	 this.smoothScrollTo(MenuWidth, 0);
	 isOpen = false;
	 } else {
	 this.smoothScrollTo(0, 0);
	 isOpen = true;
	 }
	 return true;
	 }
	
	 return super.onTouchEvent(ev);
	 }

	/**
	 * �򿪲˵� ���ж�isOpen=�� ��Ϊfalse�����ʾû�д� ��� Ȼ��״̬Ϊ-���˲˵�
	 */
	public void openMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
		// mMenu.setVisibility(View.VISIBLE);
	}

	// �ر�
	public void closeMenu() {
		if (!isOpen)
			return;
		this.smoothScrollTo(MenuWidth, 0);
		isOpen = false;
		// mMenu.setVisibility(View.GONE);
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

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);

		float scale = l * 1.0f / MenuWidth;

		// float ContentScale = scale * 0.3f + 0.7f; // 1~0.7
		// ViewHelper.setPivotX(mContent, 0); // ��������View�����ĵ� �� x��Ϊ0
		// ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);//
		// ��������View�����ĵ�
		// // �� y��Ϊ�ߵ��е�
		// ViewHelper.setScaleX(mContent, ContentScale);// �������򽥱�x����1~0.7
		// ViewHelper.setScaleY(mContent, ContentScale);

		float leftScale = 1.0f - scale * 0.3f; // �˵�����ı仯
		float leftAlpha = 1f - scale; //

		// �������Զ���������TranslationX λ��x
		// ����ʱ���˵���x ����λ�ƣ�λ��x=0.8�˿� -> 0
		ViewHelper.setTranslationX(mMenu, MenuWidth * scale * 0.8f);
		ViewHelper.setScaleX(mMenu, leftScale); // �˵�����x��0.7~1������0.7��С�������ʾ
		ViewHelper.setScaleY(mMenu, leftScale); // �˵�����y��0.7~1��
		ViewHelper.setAlpha(mMenu, leftAlpha); // �˵�͸���ȣ�0��6~1���Ӱ�͸����ȫ͸��
	}

}
