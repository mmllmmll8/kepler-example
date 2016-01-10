package com.example.mulewen.kepler_example_as.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.mulewen.newkepler.service.MainService;

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
				Toast.makeText(arg0, "ʵ���������", Toast.LENGTH_LONG).show();
			}  
        }   
	}
}
