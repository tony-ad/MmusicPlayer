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
	 * id: 0 - ���ذ�ť 1 - ��½��ť 2 - �������밴ť 3 - ע�ᰴť
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
			// ����
			Intent intent = new Intent("Open_To.Menu2");
			intent.putExtra("GoOrBack", "back");
			context.sendBroadcast(intent);
			break;
		case 1:
			// ��½
			login();
			break;
		case 2:
			// ����
			break;
		case 3:
			// ע��
			break;

		default:
			break;
		}
	}
	
	/**
	 * ��¼
	 */
	private void login(){
		String userName = user_et.getText().toString();
		String userPasswork = user_pw.getText().toString();
		
		Log.d("DBB", userName + "   " + userPasswork);
		
		if(userPasswork == null || userPasswork.length()==0){
			if(userName==null || userName.length()==0){
				Toast.makeText(context, "�������û���", Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(context, "����������", Toast.LENGTH_SHORT).show();
		}else {
			if(userPasswork== "adui0" && userName=="adui"){
					Log.d("DBB", "��½�ɹ�");
			
			}
		}
		
		
		
		
		
		
		
		
		
		
		//Ȼ�������ݿ��ѯ�������Ƿ�����û��ţ�����������ͬ
				//�еĻ���½�ɹ�����תҳ��
				//û�Ļ���½ʧ�ܣ�����ʧ�ܵ�ԭ������������û�д��û��ţ�
		
		
		
//		
//		
//		//��ȡ���û����������
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
//			Toast.makeText(context, "�������˻������������", Toast.LENGTH_SHORT).show();
//		}else {
//			if(DBuserName==userName){
//				if(DBuserPasswork==userPasswork){
//					//��½�ɹ�
//					
//					Log.d("DBB", "��½�ɹ�");
//				}else {
//					Toast.makeText(context, "�������", Toast.LENGTH_SHORT).show();
//				}
//			}else {
//				Toast.makeText(context, "�˻�������", Toast.LENGTH_SHORT).show();
//			}
//		}
		
		
		
		
		
	}
	
}
