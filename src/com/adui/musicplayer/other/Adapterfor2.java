package com.adui.musicplayer.other;

import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import com.adui.mmusic.R;
import com.adui.musicplayer.model.Music;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapterfor2 extends ArrayAdapter<Music>{
	private int resourceId;
	public Adapterfor2(Context context, int textViewResourceId, List<Music> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		resourceId=textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Music music = getItem(position);
		View v = null;
		ViewHolder vh;
		if(convertView==null){
			v=LayoutInflater.from(getContext()).inflate(resourceId,null);
			vh = new ViewHolder();
			vh.tv1 = (TextView)v.findViewById(R.id.item_tv1);
			vh.tv2 = (TextView)v.findViewById(R.id.item_tv2);
			vh.iv = (ImageView) v.findViewById(R.id.item_iv);
			v.setTag(vh);
		
		}else{
			v=convertView;
			vh=(ViewHolder)v.getTag();
		}
		vh.tv1.setText(music.getMusicN());
		vh.tv2.setText(music.getName());
		Bitmap bm = music.getBm();
		if(bm==null){
			vh.iv.setImageResource(R.drawable.moren);
		}else{
			vh.iv.setImageBitmap(bm);
		}
		
		return v;
		
	}
	
	class ViewHolder{
		TextView tv1;
		TextView tv2;
		ImageView iv;
	}
	
}
