package com.adui.musicplayer.layout.HScrollView;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 方法goToMenu_2() - 打开关闭 menu2 方法toggle() - 打开关闭menu1
 * 
 * @author user
 *
 */
public class HorizontalScroll extends HorizontalScrollView {

	private int mSreeWidth;
	private boolean once;
	private LinearLayout mWapper;
	private int MenuWidth;
	private int ContentWidth;
	private int MenuLightPad;
	private MyPager myPager;

	public void setMyPager(MyPager myPager) {
		this.myPager = myPager;
	}

	/**
	 * 是否打开菜单 false-未打开-未显示 true -打开了-显示菜单
	 */
	private boolean isOpen;

	/**
	 * 是否打开菜单 false-未打开-未显示 true -打开了-显示菜单
	 */
	private boolean isOpen2;

	/**
	 * FrameLayout
	 */
	private ViewGroup mMenu_1;
	/**
	 * FrameLayout
	 */
	private ViewGroup mMenu_2;

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
		MenuLightPad = mSreeWidth / 3 / 2;
	}

	public boolean isOpen() {
		return isOpen;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if (!once) {
			mWapper = (LinearLayout) getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(0);
			mMenu_1 = (ViewGroup) mWapper.getChildAt(1);
			mMenu_2 = (ViewGroup) mWapper.getChildAt(2);
//			MenuWidth = mMenu_1.getLayoutParams().width = mSreeWidth - MenuLightPad;
			MenuWidth = mMenu_1.getLayoutParams().width = mSreeWidth;
			mMenu_2.getLayoutParams().width = mSreeWidth;
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
			this.scrollTo(0, 0);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action = ev.getAction();
		ViewGroup viewGroup = (ViewGroup) getChildAt(0);
		View view1 = viewGroup.getChildAt(0);
		View view2 = viewGroup.getChildAt(1);
		View view3 = viewGroup.getChildAt(2);
		if (isOpen == false && isOpen2 == false) {
			Log.d("view", "isOpen is + " + isOpen + ";isOpen2 is " +isOpen2);
			//如果2个menu都没有打开，则显示的是ViewPager，ViewPager动
			view1.dispatchTouchEvent(ev);
			return true;
		} else if (isOpen == true) {
			//如果menu1打开时
			if (isOpen2 == true) {
				Log.d("view", "isOpen is + " + isOpen + ";isOpen2 is " +isOpen2);
				//同时，menu2打开时，menu2可以动
				view3.dispatchTouchEvent(ev);
				return true;
			}
			Log.d("view", "isOpen is + " + isOpen + ";isOpen2 is " +isOpen2);
			
			view2.dispatchTouchEvent(ev);
			return true;
		}

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;

		case MotionEvent.ACTION_MOVE:
			break;

		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 打开菜单 先判断isOpen=？ 若为false，则表示没有打开 则打开 然后状态为-打开了菜单
	 */
	public void openMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(mSreeWidth, 0);
		isOpen = true;
		// mMenu.setVisibility(View.VISIBLE);
	}

	// 关闭
	public void closeMenu() {
		if (!isOpen)
			return;
		this.smoothScrollTo(0, 0);
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

	public boolean getisOpen1() {
		return isOpen;
	}

	/**
	 * 使滚动条在某个位置
	 */
	public void onMenu2(){
		this.smoothScrollTo(mSreeWidth + mSreeWidth, 0);
		isOpen2=true;
		isOpen=true;
	}
	
	public boolean getisOpen2() {
		return isOpen2;
	}

	public void openMenu2() {
		if (isOpen2)
			return;
		this.smoothScrollTo(mSreeWidth + mSreeWidth, 0);
		isOpen2 = true;
	}

	
	
	// 关闭
	public void closeMenu2() {
		if (!isOpen)
			return;
		this.smoothScrollTo(mSreeWidth, 0);
		isOpen2 = false;
		// mMenu.setVisibility(View.GONE);
	}

	/**
	 * 栏目2的开关
	 */
	public void goToMenu_2() {
		if (isOpen2) {
			closeMenu2();
		} else {
			openMenu2();
		}
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		//
		// float scale = l * 1.0f / MenuWidth;
		//
		// // float ContentScale = scale * 0.3f + 0.7f; // 1~0.7
		// // ViewHelper.setPivotX(mContent, 0); // 设置内容View的中心点 ， x的为0
		// // ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);//
		// // 设置内容View的中心点
		// // // ， y的为高的中点
		// // ViewHelper.setScaleX(mContent, ContentScale);// 内容区域渐变x，从1~0.7
		// // ViewHelper.setScaleY(mContent, ContentScale);
		//
		// float leftScale = 1.0f - scale * 0.3f; // 菜单区域的变化
		// float leftAlpha = 1f - scale; //
		//
		// // 调用属性动画，设置TranslationX 位移x
		// // 滚动时，菜单的x 进行位移，位移x=0.8菜宽 -> 0
		// ViewHelper.setTranslationX(mMenu, MenuWidth * scale * 0.8f);
		// ViewHelper.setScaleX(mMenu, leftScale); // 菜单渐变x，0.7~1；即从0.7大小到完成显示
		// ViewHelper.setScaleY(mMenu, leftScale); // 菜单渐变y，0.7~1；
		// ViewHelper.setAlpha(mMenu, leftAlpha); // 菜单透明度，0。6~1，从半透明到全透明
	}

}
