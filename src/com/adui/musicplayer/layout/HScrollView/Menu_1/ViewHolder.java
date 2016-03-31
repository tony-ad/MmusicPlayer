package com.adui.musicplayer.layout.HScrollView.Menu_1;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 通过的ViewHolder
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
	 * 判断convertView是new出来的，还是getTag来的
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
	 * 通过ViewId获取控件
	 * 获取ViewHolder之后，通过此方法拿到需要的控件
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
	 * 为id的TextView设置text
	 */
	public ViewHolder setText(int viewId,String text){
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	
	/**
	 * 为id的ImageView 设置 图片
	 */
	public ViewHolder setImageResource(int viewId,int resId){
		ImageView im = getView(viewId);
		im.setImageResource(resId);
		return this;
	}
	
}
