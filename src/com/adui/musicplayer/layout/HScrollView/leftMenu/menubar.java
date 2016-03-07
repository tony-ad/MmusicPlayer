package com.adui.musicplayer.layout.HScrollView.leftMenu;

import com.adui.mmusic.R;
import com.adui.musicplayer.activity.threeActivity.activityFor3;

import android.R.menu;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class menubar extends Fragment {

	private Context context;
	private View v;
	private Button b;
	private activityFor3 activity;
	
	public menubar(Context context){
		this.context=context;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v=inflater.inflate(R.layout.at, container,false);
		
		activity = (activityFor3) getActivity();
		
		b=(Button) v.findViewById(R.id.button);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("CAOLL", "is click");
				activity.toggleMenu(v);
			}
		});
		return v;
	}
}
