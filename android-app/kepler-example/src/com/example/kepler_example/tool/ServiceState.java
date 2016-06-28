package com.example.kepler_example.tool;
//弃用
import java.util.List;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.util.Log;

public class ServiceState {
	public static boolean serviceisrunning(Context arg0){
	    ActivityManager manager = (ActivityManager) arg0.getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningServiceInfo> infolist = manager.getRunningServices(Integer.MAX_VALUE);
        for (RunningServiceInfo service : infolist) {
            if (service.service.getClassName().equalsIgnoreCase("com.example.kepler.service.MainService")) {
                Log.e("service check","running");
                return true;
            }
        }
        Log.e("service check","stopped");
        return false;
	}
}
