package com.adui.musicplayer.layout.MainHScrollView.right_Frag;

import com.adui.mmusic.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class land_Frag extends Fragment {
	
	private Context mContext;
	private View v;
	
	public land_Frag(Context mContext) {
		this.mContext=mContext;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v=inflater.inflate(R.layout.land_frag, container,false);
		
		
		return v;
	}
}
