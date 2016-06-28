package com.example.experiment;

import tool.ServiceState;

import com.example.kepler.service.MainService;

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
	StartActivity activity = null;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		activity = this;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//进行全屏 
		setContentView(R.layout.activity_start);
		final SharedPreferences share = this.getSharedPreferences("exam",0);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		        String id = share.getString("id", "");
		        
		        if(id==""){
		        	//进入一个注册界面
		        	Intent intent = new Intent(StartActivity.this,Register.class);
		        	startActivity(intent);
		        	finish();
		        }else{
		        	//进入main_activity
		        	boolean isclose = !ServiceState.serviceisrunning(activity);
		        	if(isclose){
		        		Intent intent = new Intent(StartActivity.this,MainService.class);
			        	startService(intent);
		        	}
		        	Intent intent = new Intent(StartActivity.this,MainActivity.class);
		        	startActivity(intent);
		        	finish();
		        }
			}
		}).start();
	}
}
