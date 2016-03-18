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

//���ڲ�ѯ���ݿ��е���Ϣ
public class MusicForDB {
	private static List<Music> musicList;
	
	/**
	 * �����ݿ��ж�ȡ������Ϣ�������������������ӵ��б���󷵻��б�
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
					singer="δ֪������";
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
		Log.d("wwwe", "���͹㲥");
		Intent i = new Intent("com.adui.musicplayer.aaa");
		context.sendBroadcast(i);
	}

    /**
     * ģ��
     * ������Ҫģ����ͼƬ
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
    //����viewԭʼ��С��������С,������λͼBitmap
        Bitmap overlay = Bitmap.createBitmap((int) (bkg.getWidth()/scaleFactor),
                (int) (bkg.getHeight()/scaleFactor), Bitmap.Config.ARGB_8888);
        
        
        
    //��ָ����λͼ����һ������������
        Canvas canvas = new Canvas(overlay);

        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        
    //�������ʣ������ƴ���Ĳ���Bitmap
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);
        
    //����ģ��
        overlay = FastBlur.doBlur(overlay, (int)radius, true);
        return overlay;
    }
	
	
	/**
	 * �������������б�
	 */
	public static List<Music> getList(){
		return musicList;
	}
	
	/**
	 * ��ͼ�θ�ΪԲ��
	 * @param bitmap
	 * @return
	 */
	public static Bitmap makeRoundCorner(Bitmap bitmap){
      int width = bitmap.getWidth(); //ͼƬ���
      int height = bitmap.getHeight(); //ͼƬ�߶�
      int left = 0, top = 0, right = width, bottom = height; //��������
      float roundPx = height/2; //x��λ��

      //��ͼƬ��Ϊ�����Σ�ֻ�������β�������Բ�Σ�
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

      //��ȡͼƬ
      Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(output);

      int color = 0xff424242;
      Paint paint = new Paint();

      //����ͼ�ε�λ��
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
