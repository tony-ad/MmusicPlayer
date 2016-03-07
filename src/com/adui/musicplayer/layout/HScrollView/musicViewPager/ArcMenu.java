package com.adui.musicplayer.layout.HScrollView.musicViewPager;



import com.adui.mmusic.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.opengl.Visibility;
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
import android.widget.ToggleButton;

public class ArcMenu extends ViewGroup implements OnClickListener {

	// ����ť��λ�ñ�ʶ
	private static final int POS_LEFT_TOP = 0;
	private static final int POS_LEFT_BOTTOM = 1;
	private static final int POS_RIGHT_TOP = 2;
	private static final int POS_RIGHT_BOTTOM = 3;

	/**
	 * ָ������ť��λ�ã��벼�ֵĿؼ���position��λ��Ҫһ��
	 */
	private Position mPosition = Position.RIGHT_BOTTOM;
	private int mRadius;

	/**
	 * �˵���״̬
	 */
	private Status mCurrentStatus = Status.CLOSE;

	/**
	 * �˵�������ť
	 */
	private View mCButton;

	/**
	 * �˵��Ŀ���
	 */
	public enum Status {
		OPEN, CLOSE
	}

	private OnMenuItemClickListener mMenuItemClickListener;

	/**
	 * �˵���λ��ö����
	 */
	public enum Position {
		LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
	}

	/**
	 * ����Ӳ˵��Ļص��ӿ�
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

		// ��ȡ�Զ�������
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

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			// ����child
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
	 * ��λ��item��λ��
	 */
	private void layoutItemButton() {
		// count�ǰ�������ť�İ�ť��
		int count = getChildCount();
		for (int i = 0; i < count - 1; i++) {
			// getChildAt(0)�����ؼ������Դ�1��ʼ��item
			View child = getChildAt(i + 1);
			child.setVisibility(View.GONE);
			// ����item������ؼ��Ŀ�-cl�͸�-ct��ֵ
			// ��һ��item�� x-0��y-mRadius
			// Math��PI=180��
			// ��COUNT-2��= �ؼ��ĸ���
			// Math.PI/2/(count-2) = �����ؼ�֮��ĽǶ�
			int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
			int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));
			int cWidth = child.getMeasuredWidth();
			int cHeight = child.getMeasuredHeight();
			// ����˵�λ���ڵײ�
			if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
				ct = getMeasuredHeight() - cHeight - ct;
			}
			// ���ϣ�����
			if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
				cl = getMeasuredWidth() - cWidth - cl;
			}
			child.layout(cl, ct, cl + cWidth, ct + cHeight);
		}
	}

	/**
	 * ��λ���˵���ť��λ��
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
	 * ����ť�ĵ���¼�
	 * ���ʱ������ť��ת��������ť��ת�ƶ�����
	 */
	public void onClick(View v) {
		// mCButton=findViewById(R.id.id_button);
		rotateCButton(v, 0f, 360f, 300);
		ToggleMenu(300);
	}

	/**
	 * �л��˵�:����˵�ʱ���ж�������
	 */
	public void ToggleMenu(int duration) {
		// TODO Auto-generated method stub
		// ΪmenuItem����ƽ�ƶ�������ת����
		int count = getChildCount();
		for (int i = 0; i < count - 1; i++) {
			final View childView = getChildAt(i + 1);
			childView.setVisibility(View.VISIBLE);
			// ��ť��λ��end- 0 , 0 ������Ϊ��ť������λ�þ��������ΪҪ�е���¼�
			// ��ť��λ��start-cl,ct
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
			// ƽ�ƶ���
			AnimationSet animationSet = new AnimationSet(true);
			Animation tranA = null;

			// ��״̬ʱcloseʱ������Ǵ�item
			if (mCurrentStatus == Status.CLOSE) {
				tranA = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
				childView.setClickable(true); // �ɵ��
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
			// ��ת����
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
		// �л��˵�״̬
		changeStatus();
	}

	/**
	 * ����menuItem�ĵ������
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
	 * Ϊ�������item����
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
	 * Ϊ��ǰ�����item���ñ���͸���Ƚ��Ͷ���
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
	 * �л��˵�״̬
	 */
	private void changeStatus() {
		// TODO Auto-generated method stub
		mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
	}

	/**
	 * ����ť����ת����
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