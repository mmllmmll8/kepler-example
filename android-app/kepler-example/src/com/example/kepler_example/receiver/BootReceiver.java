package com.example.kepler_example.receiver;


import com.example.kepler.service.MainService;
import com.example.kepler_example.activity.StartActivity;
import com.example.kepler_example.tool.ServiceState;
import com.example.kepler_example.tool.pollingservice;
import com.example.kepler_example.tool.pollingutils;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		final String action = arg1.getAction();
		if(Intent.ACTION_SCREEN_ON.equals(action)) {
			Log.e("kepler", "i am living");
			boolean service_close = !ServiceState.serviceisrunning(arg0);
			if(service_close){
				Intent intent=new Intent(arg0, MainService.class);
				intent.setAction("com.example.kepler.service.MainService");
				arg0.startService(intent);
			}
		}
	}
}