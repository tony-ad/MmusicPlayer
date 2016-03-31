package com.adui.musicplayer.layout.HScrollView.Menu_1;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * ͨ�õ�Adapter
 * @param <T> - ʵ����
 */
public abstract class Adapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> mDatas;
	protected LayoutInflater inflater;
	protected int itemLayoutId; 
	
	public Adapter(Context context,List<T> datas,int itemLayoutId){
		this.mContext=context;
		this.mDatas=datas;
		inflater=LayoutInflater.from(mContext);
		this.itemLayoutId=itemLayoutId;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder =ViewHolder.get(mContext, convertView, parent, itemLayoutId, position);
		
		convert(holder,getItem(position));
		
		return holder.getConvertView();
	}
	
	/**
	 * �ṩViewHolder������ʹ�����еķ���(�磺setText)
	 * �ṩ���ݼ�����Ϊ�ؼ���ֵ
	 */
	public abstract void convert(ViewHolder holder,T t);
}
