package com.example.mulewen.kepler_example_as.tool;

import java.util.List;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceState {
	public static boolean serviceisrunning(Context arg0){
	    ActivityManager manager = (ActivityManager) arg0.getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningServiceInfo> infolist = manager.getRunningServices(Integer.MAX_VALUE);
        for (RunningServiceInfo service : infolist) {
            if ("com.example.kepler.service.MainService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
	}
}
