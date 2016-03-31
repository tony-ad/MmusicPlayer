package com.adui.musicplayer.layout.HScrollView.Menu_1;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 通用的Adapter
 * @param <T> - 实体类
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
	 * 提供ViewHolder，可以使用其中的方法(如：setText)
	 * 提供数据集，可为控件赋值
	 */
	public abstract void convert(ViewHolder holder,T t);
}
