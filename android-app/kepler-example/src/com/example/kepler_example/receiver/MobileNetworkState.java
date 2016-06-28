package com.example.kepler_example.receiver;

import com.example.kepler.service.MainService;
import com.example.kepler_example.activity.StartActivity;
import com.example.kepler_example.tool.ServiceState;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.IBinder;
import android.util.Log;

public class MobileNetworkState extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		ConnectivityManager cm = (ConnectivityManager) arg0.getSystemService(Context.CONNECTIVITY_SERVICE); 
		State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();  
        if(mobileState==State.CONNECTED)
        {
        	Log.e("moblie network", "service check");
        	boolean service_close = !ServiceState.serviceisrunning(arg0);
        	if(service_close){
        		Intent intent=new Intent(arg0, MainService.class);
			    intent.setAction("com.example.kepler.service.MainService");
			    arg0.startService(intent);
        	}
        }
	}
}