package com.example.mulewen.kepler_example_as.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

import com.example.mulewen.newkepler.service.MainService;

import com.example.mulewen.kepler_example_as.tool.ServiceState;

public class MobileNetworkState extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		ConnectivityManager cm = (ConnectivityManager) arg0.getSystemService(Context.CONNECTIVITY_SERVICE); 
		State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();  
        if(mobileState==State.CONNECTED)
        {

        	boolean service_close = !ServiceState.serviceisrunning(arg0);
        	if(service_close){
        		Intent intent=new Intent(arg0, MainService.class);
			    intent.setAction("com.example.kepler.service.MainService");
			    arg0.startService(intent);
        	}
        }
	}
}
