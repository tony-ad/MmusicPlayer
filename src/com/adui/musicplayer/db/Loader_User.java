package com.adui.musicplayer.db;

import java.util.List;

import com.adui.musicplayer.model.User;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * ģ�����е�����
 * @author user
 *
 */
public class Loader_User {
		private static String userName = "adui";
		private static String userGender1 = "boy";
		private static String userGender2 = "girl";
		private static String userRegion = "GuangDong";
		private static String userSignature = "Hello,I am adui";
		private static String userPasswork = "passwork";
		
		/**
		 * ��������ӵ����ݿ⣬ģ����ӵ�е��û�����
		 * @param db
		 */
		public static void addDB(SQLiteDatabase db){
			ContentValues values = new ContentValues();
			for(int i=0;i<100;i++){
	        values.put("name", userName);
	        if(i % 2 ==0){
	        	values.put("gender", userGender1);
	        }else {
	        	values.put("gender", userGender2);
			}
	        values.put("passwork", userPasswork);
	        values.put("diqu", userRegion);
	        values.put("qianming", userSignature);
	        db.insert("user", null, values);
	        values.clear();
			}
		}

	
}
