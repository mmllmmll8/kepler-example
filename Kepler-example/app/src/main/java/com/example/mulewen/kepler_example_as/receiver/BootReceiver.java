package com.example.mulewen.kepler_example_as.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.mulewen.newkepler.service.MainService;
import com.example.mulewen.kepler_example_as.tool.ServiceState;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		final String action = arg1.getAction();
		if(Intent.ACTION_SCREEN_ON.equals(action)) {
			boolean service_close = !ServiceState.serviceisrunning(arg0);
			if(service_close){
				Intent intent=new Intent(arg0, MainService.class);
				intent.setAction("com.example.kepler.service.MainService");
				arg0.startService(intent);
			}
		}
	}
}
