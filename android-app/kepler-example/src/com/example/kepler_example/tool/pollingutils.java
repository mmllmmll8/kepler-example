package com.example.kepler_example.tool;
//弃用
import android.R.anim;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class pollingutils {
	public static void startpollingutils(Context context,int milliseconds,Class<?> cls,String action){
		AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context,cls);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0,  
                intent, PendingIntent.FLAG_UPDATE_CURRENT);  
		long triggerAtTime = SystemClock.elapsedRealtime();  
        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service  
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime,  
        		milliseconds, pendingIntent); 
	}
}
