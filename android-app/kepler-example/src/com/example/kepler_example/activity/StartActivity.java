package com.example.kepler_example.activity;

import com.example.kepler.service.MainService;
import com.example.kepler_example.R;
import com.example.kepler_example.tool.ServiceState;
import com.example.kepler_example.tool.pollingservice;
import com.example.kepler_example.tool.pollingutils;
import com.example.kepler.framework.*;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager;

public class StartActivity extends Activity {
	StartActivity activity = null;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		activity = this;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_start);
		final SharedPreferences share = this.getSharedPreferences("exam",0);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
		        String id = share.getString("id", "");
		        if(id==""){
		        	Intent intent = new Intent(StartActivity.this,Register.class);
		        	startActivity(intent);
		        	finish();
		        }else{
		        	boolean isclose = !ServiceState.serviceisrunning(activity);
		        	if(isclose){
		        		//pollingutils.startpollingutils(getApplicationContext(), 1000*60*30, MainService.class, "com.example.kepler.service.MainService");
		        		Intent intent = new Intent(StartActivity.this,MainService.class);
		        		Bundle bundle = new Bundle();
		        		bundle.putInt("scantime", share.getInt("scantime", 10000));
		        		intent.putExtras(bundle);
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
//ServiceConnection conn = new ServiceConnection() {
///** 获取服务对象时的操作 */
//
//
//@Override
//public void onServiceConnected(ComponentName arg0,
//		IBinder arg1) {
//	// TODO Auto-generated method stub
//	
//}
//
//@Override
//public void onServiceDisconnected(ComponentName name) {
//	// TODO Auto-generated method stub
//	
//}
//
//};

//bindService(intent,conn,Context.BIND_AUTO_CREATE);