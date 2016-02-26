package com.adui.musicplayer.db;


import java.util.ArrayList;
import java.util.List;
import com.adui.musicplayer.model.MediaUtil;
import com.adui.musicplayer.model.Music;
import com.adui.musicplayer.other.LogUtil;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.provider.MediaStore;
import android.util.Log;

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
				String MusicN = cursors.getString(cursors.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
				int id = cursors.getInt(cursors.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
				String zhuanji = cursors.getString(cursors.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
				int time = cursors.getInt(cursors.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
				Long songid = cursors.getLong(3);
				Long albumid = cursors.getLong(7);
				String url = cursors.getString(cursors.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				Bitmap bm = MediaUtil.getArtwork(context, songid, albumid,true);
				Bitmap bb;
				if(bm==null){
					bb=null;
				}else {
					bb=makeRoundCorner(bm);
				}
				Music musicci = new Music();
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
