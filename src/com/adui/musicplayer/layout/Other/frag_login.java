package com.adui.musicplayer.layout.Other;
import com.adui.mmusic.R;
import com.adui.musicplayer.activity.MmainActivity;
import com.adui.musicplayer.model.Gongju;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class frag_login extends Fragment implements OnClickListener {

	private Context context;
	private View view;
	private MmainActivity activity;
	private EditText user_et;
	private EditText user_pw;
	private LinearLayout ll;
	
	private int[] RIds = new int[] { R.id.button_loginlayout_backbutton, R.id.button_loginlayout_loginButton,
			R.id.button_loginlayout_forgetpasswork, R.id.button_loginlayout_register };

	private  SQLiteDatabase db ;
	
	/**
	 * id: 0 - 返回按钮 1 - 登陆按钮 2 - 忘记密码按钮 3 - 注册按钮
	 */
	private Button[] buttons = new Button[RIds.length];

	public frag_login() {
	}

	public frag_login(Context context) {
		this.context = context;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_loginlayout, container, false);
		
		initView();
		
		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		for (int i = 0; i < RIds.length; i++) {
			buttons[i] = (Button) view.findViewById(RIds[i]);
			buttons[i].setId(i);
			buttons[i].setOnClickListener(this);
		}
		user_et = (EditText) view.findViewById(R.id.edittext_loginlayout_user);	
		user_pw = (EditText) view.findViewById(R.id.edittext_loginlayout_passwork);	
		
		user_et.setFocusable(false);
		user_pw.setFocusable(false);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case 0:
			// 返回
			Intent intent = new Intent("Open_To.Menu2");
			intent.putExtra("GoOrBack", "back");
			context.sendBroadcast(intent);
			break;
		case 1:
			// 登陆
			login();
			break;
		case 2:
			// 忘记
			break;
		case 3:
			// 注册
			break;

		default:
			break;
		}
	}
	
	/**
	 * 登录
	 */
	private void login(){
		String userName = user_et.getText().toString();
		String userPasswork = user_pw.getText().toString();
		
		Log.d("DBB", userName + "   " + userPasswork);
		
		if(userPasswork == null || userPasswork.length()==0){
			if(userName==null || userName.length()==0){
				Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
		}else {
			if(userPasswork== "adui0" && userName=="adui"){
					Log.d("DBB", "登陆成功");
			
			}
		}
		
		
		
		
		
		
		
		
		
		
		//然后在数据库查询，看看是否存在用户号，并且密码相同
				//有的话登陆成功，跳转页面
				//没的话登陆失败，返回失败的原因（密码错误或者没有此用户号）
		
		
		
//		
//		
//		//获取了用户输入的内容
//		Cursor cursor1 = Gongju.getDB().query(
//				"user",new String[]{"name"},
//				"name=?",new String[]{userName},
//				null,null,null
//				);
//		Log.d("DBB", "cursor1 is null?" + (cursor1==null));
//		String DBuserName = cursor1.getString(cursor1.getColumnIndex("name"));
//		Log.d("DBB", "DBuserName is null?" + (DBuserName==null));
//		Cursor cursor = Gongju.getDB().query(
//				"user",new String[]{"passwork"},
//				"passwork=?",new String[]{userPasswork},
//				null,null,null
//				);
//		String DBuserPasswork = cursor.getString(cursor.getColumnIndex("passwork"));
//		
//		Log.d("DBB", "DBuserPasswork is null?" + (DBuserPasswork==null));
//		
//		if(DBuserPasswork==null || userName ==null ){
//			Toast.makeText(context, "不存在账户或者密码错误", Toast.LENGTH_SHORT).show();
//		}else {
//			if(DBuserName==userName){
//				if(DBuserPasswork==userPasswork){
//					//登陆成功
//					
//					Log.d("DBB", "登陆成功");
//				}else {
//					Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
//				}
//			}else {
//				Toast.makeText(context, "账户不符合", Toast.LENGTH_SHORT).show();
//			}
//		}
		
		
		
		
		
	}
	
}
