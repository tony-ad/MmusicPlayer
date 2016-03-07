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
	 * VPnoplay=false - VP动，HSV不动
	 */
	private boolean VPnoplay = false;

	private MyPager myPager;

	public void setMyPager(MyPager myPager) {
		this.myPager = myPager;
	}

	/**
	 * 是否打开菜单 false-未打开-未显示 true -打开了-显示菜单
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
	 // 如果菜单打开了，就不能使用ViewPager滑动，即拦截活动
		 // 即（ViewPager不能滑动），防止通过滑动，把ViewPager显示到第二页
		 if (isOpen == true) {
		 this.onInterceptTouchEvent(ev);
		 ViewGroup child = (ViewGroup) getChildAt(0);
		 View menu = child.getChildAt(0);
		 requestDisallowInterceptTouchEvent(true);
		 menu.dispatchTouchEvent(ev);
		 }
		 // 如果菜单未打开，则判断当前ViewPager的页面是多少（0 or 1）
		 else {
		 if (myPager.getCurrentItem() == 0) {
		 // 如果页面显示的是1，则
		 // 把事件分发到子View中（可以滑动ViewPager），不返回参数（可滑动Scroll）
		 // 即可以滑动ViewPager（去页面2），又可以滑动Scroll（到菜单）
		 ViewGroup child = (ViewGroup) getChildAt(0);
		 View childPager = child.getChildAt(1);
		 requestDisallowInterceptTouchEvent(true);
		 childPager.dispatchTouchEvent(ev);
		 } else {
		 // 如果页面显示的是2，
		 // 就把事件分发到子View中 - 即ViewPager可以滑动
		 // 并返回true，消费事件 - 即Scroll不可滑动，防止从页面2打开菜单
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
	 // 隐藏在左边的宽度
	 int scrollX = getScrollX();
	 if (scrollX >= MenuWidth / 2) {
	 //关闭
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
	 * 打开菜单 先判断isOpen=？ 若为false，则表示没有打开 则打开 然后状态为-打开了菜单
	 */
	public void openMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
		// mMenu.setVisibility(View.VISIBLE);
	}

	// 关闭
	public void closeMenu() {
		if (!isOpen)
			return;
		this.smoothScrollTo(MenuWidth, 0);
		isOpen = false;
		// mMenu.setVisibility(View.GONE);
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

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);

		float scale = l * 1.0f / MenuWidth;

		// float ContentScale = scale * 0.3f + 0.7f; // 1~0.7
		// ViewHelper.setPivotX(mContent, 0); // 设置内容View的中心点 ， x的为0
		// ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);//
		// 设置内容View的中心点
		// // ， y的为高的中点
		// ViewHelper.setScaleX(mContent, ContentScale);// 内容区域渐变x，从1~0.7
		// ViewHelper.setScaleY(mContent, ContentScale);

		float leftScale = 1.0f - scale * 0.3f; // 菜单区域的变化
		float leftAlpha = 1f - scale; //

		// 调用属性动画，设置TranslationX 位移x
		// 滚动时，菜单的x 进行位移，位移x=0.8菜宽 -> 0
		ViewHelper.setTranslationX(mMenu, MenuWidth * scale * 0.8f);
		ViewHelper.setScaleX(mMenu, leftScale); // 菜单渐变x，0.7~1；即从0.7大小到完成显示
		ViewHelper.setScaleY(mMenu, leftScale); // 菜单渐变y，0.7~1；
		ViewHelper.setAlpha(mMenu, leftAlpha); // 菜单透明度，0。6~1，从半透明到全透明
	}

}
