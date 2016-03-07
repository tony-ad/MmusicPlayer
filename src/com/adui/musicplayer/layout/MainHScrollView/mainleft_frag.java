package com.adui.musicplayer.layout.MainHScrollView;

import java.util.ArrayList;
import java.util.List;

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
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * ����������Ƭ
 * ������һ��HScrollView
 * @author user
 *
 */
public class mainleft_frag extends Fragment {
	
	private HorizontalScroll Hview;
	private MyPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragemnts;
	private Context context;
	private View v;
	
	public mainleft_frag(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v=inflater.inflate(R.layout.main_2, container,false);
		Hview = (HorizontalScroll) v.findViewById(R.id.id_HScroll);
		init();
		initViewPager();
		initMenuFragment();
		
		return v;
	}

	
	/**
	 * ��ʼ���˵���Ƭ
	 */
	private void initMenuFragment() {
		// TODO Auto-generated method stub
		Fragment menu_frag = new menubar(context);
		getActivity().getSupportFragmentManager().beginTransaction().add(R.id.layout_menu, menu_frag).commit();
	}
	
	public boolean getisOpen(){
		Boolean a =Hview.isOpen();
		return a;
	}
	
	/**
	 * �л��˵�
	 */
	public void changeMenu(){
		Hview.toggle();
	}
	
	
	/**
	 * ��ʼ����Ƭ��������
	 */
	private void init() {
		// TODO Auto-generated method stub
		mFragemnts=new ArrayList<Fragment>();
		Fragment f1 = new frag_1(context);
		mFragemnts.add(f1);
		Fragment f2 = new frag_2(context);
		mFragemnts.add(f2);
		mAdapter=new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
			
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
		mViewPager=(MyPager) v.findViewById(R.id.mViewPager);
		mViewPager.setAdapter(mAdapter);
		Hview.setMyPager(mViewPager);
		
	}

	
}
