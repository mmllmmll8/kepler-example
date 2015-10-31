package com.example.experiment;

import android.app.Activity;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends Activity {

	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ������
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//����ȫ��   
		setContentView(R.layout.activity_start);
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SharedPreferences share = this.getPreferences(MODE_PRIVATE);
        String id = share.getString("id", "");
        if(id==""){
        	//����һ��ע�����
        	Intent intent = new Intent(StartActivity.this,Register.class);
        	startActivity(intent);
        	finish();
        }else{
        	//����main_activity
        	Intent intent = new Intent(StartActivity.this,MainActivity.class);
        	startActivity(intent);
        	finish();
        }
	}
}
