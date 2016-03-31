package com.adui.musicplayer.layout.HScrollView.musicViewPager;

import java.util.ArrayList;
import java.util.List;

import com.adui.mmusic.R;
import com.adui.musicplayer.activity.MmainActivity;
import com.adui.musicplayer.db.MusicForDB;
import com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_1_ListView.ListView_allMusic;
import com.adui.musicplayer.layout.HScrollView.musicViewPager.frag_1_ListView.ListView_ilikemusic;
import com.adui.musicplayer.model.Music;
import com.adui.musicplayer.other.Adapterfor2;
import com.adui.musicplayer.service.MusicPlayService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * “Ù¿÷—°‘ÒΩÁ√Ê
 * 
 * @author user
 *
 */
public class frag_1 extends Fragment implements OnClickListener {
	private View view;
	private Context mContext;
	public static List<Music> musicList_all;
	public List<Music> musicList_ilike;
	private ImageButton imageButton;
	private MmainActivity mActivity;
	
	private int[] R_Id_Button = new int[]{R.id.music_all,R.id.music_ilike};
	private Button[] buttons = new Button[R_Id_Button.length];
	
	private Button music_ilike;
	private Button music_all;
	private ImageButton imageButton2;

	private Fragment fragment_allmusic;
	private Fragment fragment_ilikemusic;
	private FragmentManager fm;
	private FragmentTransaction ft;

	public frag_1(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("taa", "frag_1 - onCreateView");
		view = inflater.inflate(R.layout.activity_musicchoose, container, false);
		initView();
		initFrag(0);
		return view;
	}

	private void initFrag(int i) {
		// TODO Auto-generated method stub
		fm = getChildFragmentManager();
		ft = fm.beginTransaction();
		hide(ft);
		switch (i) {
		case 0:
			if(fragment_allmusic==null){
			   fragment_allmusic = new ListView_allMusic(mContext);
				ft.add( R.id.listview_FrameLayout,fragment_allmusic);
			}else {
				ft.show(fragment_allmusic);
			}
			break;
		case 1:
			if(fragment_ilikemusic==null){
				fragment_ilikemusic = new ListView_ilikemusic(mContext);
					ft.add( R.id.listview_FrameLayout,fragment_ilikemusic);
				}else {
					ft.show(fragment_ilikemusic);
				}
			break;
		case 2:

			break;
		case 3:

			break;

		default:
			break;
		}
		ft.commit();
	}
	private void hide(FragmentTransaction ft) {
		if(fragment_ilikemusic!= null){
			ft.hide(fragment_ilikemusic);
		}
		if(fragment_allmusic!=null){
			ft.hide(fragment_allmusic);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		imageButton = (ImageButton) view.findViewById(R.id.menu_ImageButton);
		imageButton.setOnClickListener(this);
		imageButton2 = (ImageButton) view.findViewById(R.id.menu_ImageButton_query);
		imageButton2.setOnClickListener(this);
		mActivity = (MmainActivity) getActivity();

		for(int i=0;i<R_Id_Button.length;i++){
			buttons[i] = (Button) view.findViewById(R_Id_Button[i]);
			buttons[i].setId(i);
			buttons[i].setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.menu_ImageButton:
			mActivity.menu_1_OpenAndClose();
			break;
			
		case R.id.menu_ImageButton_query:
			
			break;

		case R.id.music_all:
//			initFrag(0);
			break;

		case R.id.music_ilike:
//			initFrag(1);
			break;

		default:
			break;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
