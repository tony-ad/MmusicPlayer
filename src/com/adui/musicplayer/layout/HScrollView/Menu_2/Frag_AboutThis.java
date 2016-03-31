package com.adui.musicplayer.layout.HScrollView.Menu_2;

import com.adui.mmusic.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * πÿ”⁄Œ“
 * @author user
 *
 */
public class Frag_AboutThis extends Fragment{
	
	private Context context;
	private View view;
	
	public Frag_AboutThis(){}
	public Frag_AboutThis(Context context){
		this.context=context;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.layout_aboutthis, container,false);
		return view;
	}
	
}
