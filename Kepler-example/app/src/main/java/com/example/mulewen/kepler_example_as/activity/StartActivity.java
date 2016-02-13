package com.example.mulewen.kepler_example_as.activity;

import com.example.mulewen.kepler_example_as.R;
import com.example.mulewen.kepler_example_as.tool.ServiceState;
import com.example.mulewen.newkepler.framework.Records_info_mid;
import com.example.mulewen.newkepler.service.MainService;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
		Records_info_mid.getpoiinfomid(this);
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
