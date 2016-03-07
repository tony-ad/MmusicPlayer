package com.adui.musicplayer.layout.MainHScrollView;

import com.adui.mmusic.R;
import com.adui.musicplayer.layout.MainHScrollView.right_Frag.land_Frag;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 主——右碎片
 * 用来显示菜单栏点击item显示的页面
 *
 */
public class mainright_frag extends Fragment {

	private Context context;
	private View v;
	private Fragment land;
	
	public mainright_frag(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v=inflater.inflate(R.layout.menu_item_fragment, container,false);
		return v;
	}

	/**
	 * 初始化碎片
	 */
	private void initFrag() {
		// TODO Auto-generated method stub
		if(land == null ){
			land = new land_Frag(context);
		}
		
	}

	/**
	 * 显示子碎片
	 */
	public void showFrag() {
		// TODO Auto-generated method stub
		initFrag();	
		getFragmentManager().beginTransaction().add(R.id.menu_item_layout, land).commit();
	}
	
	
}
