package com.adui.musicplayer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	public String createTableSQL = "create table if not exists music_tb"+
	"(_id integer primary key autoincrement,name,musicN,zhuanji,time,url,bm,bmm)";
	
	/**
	 * Ω®±Ì”Ôæ‰
	 */
	public static final String CREATE_User = "create table user("
            + "id integer primary key autoincrement, "
            + "name text,"
            + "passwork integer,"
            + "diqu text,"
            + "gender text,"
            + "qianming text)";
	
	
	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(createTableSQL);
		db.execSQL(CREATE_User);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
