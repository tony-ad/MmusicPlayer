package com.adui.musicplayer.layout.HScrollView.Menu_1;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * ͨ����ViewHolder
 */
public class ViewHolder {
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	
	public ViewHolder(Context context,ViewGroup parent,int layoutId,int position){
		this.mPosition=position;
		this.mViews=new SparseArray<View>();
		mConvertView=LayoutInflater.from(context).inflate(layoutId, parent,false);
		mConvertView.setTag(this);
	}
	
	/**
	 * �ж�convertView��new�����ģ�����getTag����
	 */
	public static ViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int position){
		if(convertView ==null){
			return new ViewHolder(context, parent, layoutId, position);
		}else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition=position;
			return holder;
		}
	}
	
	/**
	 * ͨ��ViewId��ȡ�ؼ�
	 * ��ȡViewHolder֮��ͨ���˷����õ���Ҫ�Ŀؼ�
	 */
	public <T extends View> T getView(int viewId){
		View view  = mViews.get(viewId);
		if(view == null){
			view =mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
	
	public View getConvertView(){
		return mConvertView;
	}
	
	/**
	 * Ϊid��TextView����text
	 */
	public ViewHolder setText(int viewId,String text){
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	
	/**
	 * Ϊid��ImageView ���� ͼƬ
	 */
	public ViewHolder setImageResource(int viewId,int resId){
		ImageView im = getView(viewId);
		im.setImageResource(resId);
		return this;
	}
	
}
