package com.adui.musicplayer.activityAddMenu;

import android.app.usage.UsageEvents.Event;
import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyPager extends ViewPager {  
    
	MyPager viewpager;  
    private int size = -1;  
    private HorizontalScroll hView;
    
    public void setHV(HorizontalScroll hView){
    	this.hView=hView;
    }
    
    public MyPager(Context context, AttributeSet attrs) {    
        super(context, attrs);    
        // TODO Auto-generated constructor stub    
    }    
      
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	int action = ev.getAction();
    	switch (action) {
case MotionEvent.ACTION_DOWN:
	Log.d("caobi", "pager - dispatchTouchEvent - ACTION_DOWN");
			break;
case MotionEvent.ACTION_MOVE:
	Log.d("caobi", "pager - dispatchTouchEvent - ACTION_MOVE");
	break;
case MotionEvent.ACTION_UP:
	Log.d("caobi", "pager - dispatchTouchEvent - ACTION_UP");
	break;

		default:
			break;
		}
    	
    	
            return super.dispatchTouchEvent(ev);
    }
    
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	int action = ev.getAction();
    	switch (action) {
case MotionEvent.ACTION_DOWN:
	Log.d("caobi", "pager - onInterceptTouchEvent - ACTION_DOWN");
			break;
case MotionEvent.ACTION_MOVE:
	Log.d("caobi", "pager - onInterceptTouchEvent - ACTION_MOVE");
	break;
case MotionEvent.ACTION_UP:
	Log.d("caobi", "pager - onInterceptTouchEvent - ACTION_UP");
	break;

		default:
			break;
		}
   
            return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
    	int action = ev.getAction();
    	switch (action) {
case MotionEvent.ACTION_DOWN:
	Log.d("caobi", "pager - onInterceptTouchEvent - ACTION_DOWN");
			break;
case MotionEvent.ACTION_MOVE:
	Log.d("caobi", "pager - onInterceptTouchEvent - ACTION_MOVE");
	break;
case MotionEvent.ACTION_UP:
	Log.d("caobi", "pager - onInterceptTouchEvent - ACTION_UP");
	break;

		default:
			break;
		}
    	Log.d("caobi", "pager - onTouchEvent");
            return super.onTouchEvent(ev);
    }
      
//    private PointF sp = new PointF();   
//    @Override  
//    public boolean onTouchEvent(MotionEvent arg0) {  
//        // TODO Auto-generated method stub  
//        int action = arg0.getAction(); 
//
//        
//        switch (action){  
//        case MotionEvent.ACTION_DOWN:  
//        	
//            sp = new PointF(arg0.getX(), arg0.getY());  
//            break;  
//        case MotionEvent.ACTION_MOVE:  
//        	PointF ep = new PointF(arg0.getX(), arg0.getY());  
//            float move_x = sp.x - ep.x;  
//          
//            //����ָ����ʱ�����жϣ�
//               //�����ǵ�һҳ�����һ�����Ҳ�������һҳ�����󻬶���
//               //����ACTION_DOWN��x��Ϊ50���ϵ�viewpager�����л���
//            if(!(move_x < 0 && getCurrentItem() == 0) && getParent() != null  
//                    && !(move_x > 0 && getCurrentItem() == getAdapter().getCount() - 1)   
//                    && sp.x > 50){  
//                  
//            	
//            	//��View��ϣ����View���ش����¼�
//                getParent().requestDisallowInterceptTouchEvent(true);  
//            }  
//          
//        }  
//          
//        return super.onTouchEvent(arg0);  
//    }  
}  
