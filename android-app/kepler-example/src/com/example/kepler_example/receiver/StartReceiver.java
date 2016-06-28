package com.example.kepler_example.receiver;

import com.example.kepler.service.MainService;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;
public class StartReceiver extends BroadcastReceiver	{

	static final String ACTION = "android.intent.action.BOOT_COMPLETED";   
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		if (arg1.getAction().equals(ACTION))    
        {   

			SharedPreferences sharedPreferences= arg0.getSharedPreferences("exam",0);
			String name =sharedPreferences.getString("id", ""); 
			if(name!=""){
				arg0.startService(new Intent(arg0,MainService.class));
				Toast.makeText(arg0, "haha", Toast.LENGTH_LONG).show();
			}  
        }   
	}
}
