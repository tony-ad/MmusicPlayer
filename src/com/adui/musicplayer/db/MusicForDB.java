package com.adui.musicplayer.db;


import java.util.ArrayList;
import java.util.List;
import com.adui.musicplayer.model.MediaUtil;
import com.adui.musicplayer.model.Music;
import com.adui.musicplayer.other.FastBlur;
import com.adui.musicplayer.other.LogUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

//用于查询数据库中的消息
public class MusicForDB {
	private static List<Music> musicList;
	
	/**
	 * 在数据库中读取音乐信息，并遍历逐个把音乐添加到列表，最后返回列表
	 */
	public static void loadMusic(Context context) {
		musicList = new ArrayList<Music>();
		Cursor cursors = context.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.DURATION,
				MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.DISPLAY_NAME,
				MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.ALBUM_ID}, 
				null,
				null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		if(cursors.moveToFirst()){
			do {
				String singer = cursors.getString(cursors.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
				
				if("<unknown>".equals(singer)){
					singer="未知艺术家";
				}
				
				String MusicN = cursors.getString(cursors.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
				int id = cursors.getInt(cursors.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
				String zhuanji = cursors.getString(cursors.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
				int time = cursors.getInt(cursors.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
				Long songid = cursors.getLong(3);
				Long albumid = cursors.getLong(7);
				String url = cursors.getString(cursors.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				Bitmap bm = MediaUtil.getArtwork(context, songid, albumid,true);
				Bitmap bb;
				Bitmap bbmohu = null;
				
				if(bm==null){
					bb=null;
					bbmohu = null;
				}else {
					bb=makeRoundCorner(bm);
					try {
						
						bbmohu = blur(context,bm);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
				Music musicci = new Music();
				musicci.setBmm(bbmohu);
				musicci.setBm(bb);
				musicci.setId(id);
				musicci.setMusicN(MusicN);
				musicci.setName(singer);
				musicci.setTime(time);
				musicci.setZhuanji(zhuanji);
				musicci.setUrl(url);
				musicList.add(musicci);
			} while (cursors.moveToNext());

		}
		Log.d("wwwe", "发送广播");
		Intent i = new Intent("com.adui.musicplayer.aaa");
		context.sendBroadcast(i);
	}

    /**
     * 模糊
     * 参数：要模糊的图片
     * @param bkg
     * @param view
     */
    @SuppressLint("NewApi")
	private static Bitmap blur(Context context,Bitmap bkg) {
        float scaleFactor = 8;
        float radius = 8;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    	DisplayMetrics outMetrics = new DisplayMetrics();
    	wm.getDefaultDisplay().getMetrics(outMetrics);        
    //根据view原始大小，进行缩小,创建新位图Bitmap
        Bitmap overlay = Bitmap.createBitmap((int) (bkg.getWidth()/scaleFactor),
                (int) (bkg.getHeight()/scaleFactor), Bitmap.Config.ARGB_8888);
        
        
        
    //用指定的位图构造一个画布来绘制
        Canvas canvas = new Canvas(overlay);

        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        
    //创建画笔，并绘制传入的参数Bitmap
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);
        
    //快速模糊
        overlay = FastBlur.doBlur(overlay, (int)radius, true);
        return overlay;
    }
	
	
	/**
	 * 返回音乐数据列表
	 */
	public static List<Music> getList(){
		return musicList;
	}
	
	/**
	 * 把图形改为圆形
	 * @param bitmap
	 * @return
	 */
	public static Bitmap makeRoundCorner(Bitmap bitmap){
      int width = bitmap.getWidth(); //图片宽度
      int height = bitmap.getHeight(); //图片高度
      int left = 0, top = 0, right = width, bottom = height; //左下右上
      float roundPx = height/2; //x的位置

      //将图片变为正方形（只有正方形才能作成圆形）
      if (width > height) {
        left = (width - height)/2;
        top = 0;
        right = left + height;
        bottom = height;
      } else if (height > width) {
        left = 0;
        top = 0;
        right = width;
        bottom = top + width;
        roundPx = width/2;
      }

      //截取图片
      Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(output);

      int color = 0xff424242;
      Paint paint = new Paint();

      //绘制图形的位置
      Rect rect = new Rect(left, top, right, bottom);
      RectF rectF = new RectF(rect);

      paint.setAntiAlias(true);
      canvas.drawARGB(0, 0, 0, 0);
      paint.setColor(color);
      canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
      paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      canvas.drawBitmap(bitmap, rect, rect, paint);
      return output;
    }
	
	

}
