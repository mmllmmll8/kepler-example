package com.example.experiment;

import com.example.kepler.service.MainService;

import android.app.Activity;
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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        this.getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//进行全屏   
		setContentView(R.layout.activity_register);
		sharedPreferences = getPreferences(MODE_PRIVATE);
		
        phone = (EditText)findViewById(R.id.phonenumber);
        start = (Button)findViewById(R.id.star);
        start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//启动service
				//结束此界面转到主界面
				String id = phone.getContext().toString();
				if(id!=""){
					Editor editor = sharedPreferences.edit();
					editor.putString("id", id);
					editor.commit();
					Intent intent = new Intent(Register.this,MainService.class);
					startService(intent);
					Intent main = new Intent(Register.this,MainActivity.class);
					startActivity(main);
					finish();					
				}
			}
		});
	}
}
