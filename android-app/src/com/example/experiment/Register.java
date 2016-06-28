package com.example.experiment;

import com.example.kepler.service.MainService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity {
	EditText phone;
	Button start;
	SharedPreferences sharedPreferences = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ������
        this.getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//����ȫ��   
		setContentView(R.layout.activity_register);
		sharedPreferences = getSharedPreferences("exam",0);
		
        phone = (EditText)findViewById(R.id.phonenumber);
        start = (Button)findViewById(R.id.star);
        start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//����service
				//�����˽���ת��������
				String id = phone.getText().toString();
				if(id!=""){
					Editor editor = sharedPreferences.edit();
					editor.putString("id", id);
					boolean a = editor.commit();
					Intent intent = new Intent(Register.this,com.example.kepler.service.MainService.class);
					ComponentName haha = startService(intent);
					Intent main = new Intent(Register.this,MainActivity.class);
					startActivity(main);
					finish();					
				}
			}
		});
	}
}
