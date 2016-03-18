package com.adui.musicplayer.layout.HScrollView.musicViewPager;



import java.util.List;

import com.adui.mmusic.R;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.model.Music;

import android.content.Context;
import android.content.res.TypedArray;
import android.opengl.Visibility;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class ArcMenu extends ViewGroup implements OnClickListener {

	// 主按钮的位置标识
	private static final int POS_LEFT_TOP = 0;
	private static final int POS_LEFT_BOTTOM = 1;
	private static final int POS_RIGHT_TOP = 2;
	private static final int POS_RIGHT_BOTTOM = 3;

	
	private ImageView iv;
	private List<Music> musicList;
	private frag_2 f;
	private boolean isOpenMenu=false;
	
	
	/**
	 * 指定主按钮的位置，与布局的控件的position的位置要一致
	 */
	private Position mPosition = Position.RIGHT_BOTTOM;
	private int mRadius;

	/**
	 * 菜单的状态
	 */
	private Status mCurrentStatus = Status.CLOSE;

	/**
	 * 菜单的主按钮
	 */
	private View mCButton;

	/**
	 * 菜单的开关
	 */
	public enum Status {
		OPEN, CLOSE
	}

	private OnMenuItemClickListener mMenuItemClickListener;

	/**
	 * 菜单的位置枚举类
	 */
	public enum Position {
		LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
	}

	/**
	 * 点击子菜单的回调接口
	 * 
	 * @author user
	 *
	 */
	public interface OnMenuItemClickListener {
		void Click(View view, int pos);
	}

	public void setOnMenuItemClickListener(OnMenuItemClickListener mMenuItemClickListener) {
		this.mMenuItemClickListener = mMenuItemClickListener;
	}

	public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
		// 获取自定义属性
		musicList= MusicForDB.getList();
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);
		
		int pos = a.getInt(R.styleable.ArcMenu_position, POS_RIGHT_BOTTOM);
		switch (pos) {
		case POS_LEFT_TOP:
			mPosition = Position.LEFT_TOP;
			break;
		case POS_LEFT_BOTTOM:
			mPosition = Position.LEFT_BOTTOM;
			break;
		case POS_RIGHT_TOP:
			mPosition = Position.RIGHT_TOP;
			break;
		case POS_RIGHT_BOTTOM:
			mPosition = Position.RIGHT_BOTTOM;
			break;
		}
		mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius,
				TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
		a.recycle();
	}
	
	public ArcMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public ArcMenu(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public void setFrag(frag_2 f){
		this.f=f;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int count = getChildCount();
		iv = (ImageView) getChildAt(1);
		for (int i = 0; i < count; i++) {
			// 测量child
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		if (changed) {
			layoutCButton();
			layoutItemButton();
		}
	}

	/**
	 * 定位各item的位置
	 */
	private void layoutItemButton() {
		// count是包含主按钮的按钮数
		int count = getChildCount();
		for (int i = 0; i < count - 1; i++) {
			// getChildAt(0)是主控件，所以从1开始是item
			View child = getChildAt(i + 1);
			child.setVisibility(View.GONE);
			// 计算item相对主控件的宽-cl和高-ct的值
			// 第一个item的 x-0，y-mRadius
			// Math。PI=180°
			// （COUNT-2）= 控件的个数
			// Math.PI/2/(count-2) = 各个控件之间的角度
			int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
			int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));
			int cWidth = child.getMeasuredWidth();
			int cHeight = child.getMeasuredHeight();
			// 如果菜单位置在底部
			if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
				ct = getMeasuredHeight() - cHeight - ct;
			}
			// 右上，右下
			if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
				cl = getMeasuredWidth() - cWidth - cl;
			}
			child.layout(cl, ct, cl + cWidth, ct + cHeight);
		}
	}

	/**
	 * 定位主菜单按钮的位置
	 */
	private void layoutCButton() {
		// TODO Auto-generated method stub
		mCButton = getChildAt(0);
		mCButton.setOnClickListener(this);
		int l = 0;
		int t = 0;
		int width = mCButton.getMeasuredWidth();
		int height = mCButton.getMeasuredHeight();
		switch (mPosition) {
		case LEFT_TOP:
			l = 0;
			t = 0;
			break;
		case LEFT_BOTTOM:
			l = 0;
			t = getMeasuredHeight() - height;
			break;
		case RIGHT_TOP:
			l = getMeasuredWidth() - width;
			t = 0;
			break;
		case RIGHT_BOTTOM:
			l = getMeasuredWidth() - width;
			t = getMeasuredHeight() - height;
			break;
		}
		mCButton.layout(l, t, l + width, t + height);
	}

	/**
	 * 主按钮的点击事件
	 * 点击时，主按钮旋转，其它按钮旋转移动出现
	 */
	public void onClick(View v) {
		// mCButton=findViewById(R.id.id_button);
		Music m = f.getMusicNow();
		if(m.isIlike()==true){
			iv.setBackgroundResource(R.drawable.ilike);
		}else {
			iv.setBackgroundResource(R.drawable.morenilike);
		}
		rotateCButton(v, 0f, 360f, 300);
		ToggleMenu(300);
	}

	
	/**
	 * 切换菜单:点击菜单时，有动画出现
	 */
	public void ToggleMenu(int duration) {
		// TODO Auto-generated method stub
		// 为menuItem添加平移动画、旋转动画
		int count = getChildCount();
		for (int i = 0; i < count - 1; i++) {
			final View childView = getChildAt(i + 1);
			childView.setVisibility(View.VISIBLE);
			// 按钮的位置end- 0 , 0 ；是因为按钮本身的位置就在这里，因为要有点击事件
			// 按钮的位置start-cl,ct
			int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
			int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));
			int xflag = 1;
			int yflag = 1;
			if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
				xflag = -1;
			}
			if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
				yflag = -1;
			}
			// 平移动画
			AnimationSet animationSet = new AnimationSet(true);
			Animation tranA = null;

			// 当状态时close时点击，是打开item
			if (mCurrentStatus == Status.CLOSE) {
				tranA = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
				childView.setClickable(true); // 可点击
				childView.setFocusable(true);

			} else {
				tranA = new TranslateAnimation(0, xflag * cl, 0, yflag * ct);
				childView.setClickable(false);
				childView.setFocusable(false);
			}
			tranA.setFillAfter(true);
			tranA.setDuration(duration);
			tranA.setStartOffset(i * 100 / count);
			tranA.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					if (mCurrentStatus == Status.CLOSE) {
						childView.setVisibility(View.GONE);
					}
				}
			});
			// 旋转动画
			RotateAnimation raa = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			raa.setDuration(duration);
			raa.setFillAfter(true);

			animationSet.addAnimation(raa);
			animationSet.addAnimation(tranA);
			childView.startAnimation(animationSet);

			final int pos = i + 1;
			childView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mMenuItemClickListener != null) {
						mMenuItemClickListener.Click(childView, pos);
					}
					menuItemAnim(pos - 1);
					changeStatus();
				}

			});
		}
		// 切换菜单状态
		changeStatus();
	}

	/**
	 * 添加menuItem的点击动画
	 * 
	 * @param i
	 */
	private void menuItemAnim(int pos) {
		// TODO Auto-generated method stub
		for (int i = 0; i < getChildCount() - 1; i++) {
			View childView = getChildAt(i + 1);
			if (i == pos) {
				childView.startAnimation(scaleBigAnim(300));
			} else {
				childView.startAnimation(scaleSmallAnim(300));
			}

			childView.setClickable(true);
			childView.setFocusable(true);
		}
	}

	/**
	 * 为除点击的item设置
	 * 
	 * @param dur
	 * @return
	 */
	private Animation scaleSmallAnim(int dur) {
		// TODO Auto-generated method stub
		AnimationSet set = new AnimationSet(true);
		ScaleAnimation sa = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		AlphaAnimation aa = new AlphaAnimation(1f, 0.0f);
		set.addAnimation(sa);
		set.addAnimation(aa);

		set.setDuration(dur);
		set.setFillAfter(true);
		return set;
	}

	/**
	 * 为当前点击的item设置变大和透明度降低动画
	 * 
	 * @param dur
	 * @return
	 */
	private Animation scaleBigAnim(int dur) {
		// TODO Auto-generated method stub\
		AnimationSet set = new AnimationSet(true);
		ScaleAnimation sa = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		AlphaAnimation aa = new AlphaAnimation(1f, 0.0f);
		set.addAnimation(sa);
		set.addAnimation(aa);

		set.setDuration(dur);
		set.setFillAfter(true);
		return set;
	}

	/**
	 * 切换菜单状态
	 */
	private void changeStatus() {
		// TODO Auto-generated method stub
		if(isOpenMenu==true){
			isOpenMenu=false;
			mCurrentStatus = Status.CLOSE;
		}else {
			isOpenMenu=true;
			mCurrentStatus = Status.OPEN;
		}
//		mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
	}

	/**
	 * @return 
	 * 
	 */
	public boolean returnMenuIsOpen(){
		return isOpenMenu;
	}
	
	/**
	 * 主按钮的旋转动画
	 */
	private void rotateCButton(View v, float start, float end, int duration) {
		// TODO Auto-generated method stub
		RotateAnimation ra = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		ra.setDuration(duration);
		ra.setFillAfter(true);
		v.startAnimation(ra);
	}

	public boolean isOpen() {
		return mCurrentStatus == Status.OPEN;
	}

	
	
}
