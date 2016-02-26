package com.adui.musicplayer.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> fragments = new ArrayList<Fragment>();
	private FragmentManager fm;

	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
		// TODO Auto-generated constructor stub
	}

	public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments) {
		super(fragmentManager);
		this.fm = fragmentManager;
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int index) {
		Log.d("WER", "，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，，");
		Log.d("WER", index+"");
		Log.d("WER", "fragments.get " + index + " is " + fragments.get(index));
		Log.d("WER", ""+fragments);
//		Fragment f = (Fragment) getAdapter().instantiateItem(this, getCurrentItem());
		return fragments.get(index);
	}

	@Override
	public Object instantiateItem(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		return super.instantiateItem(arg0, arg1);
	}
	
	public int getCount() {
		return fragments.size();
	}
	
	
}
