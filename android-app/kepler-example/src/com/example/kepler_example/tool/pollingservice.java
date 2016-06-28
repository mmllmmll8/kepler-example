package com.example.kepler_example.tool;
//弃用
import com.example.kepler.service.MainService;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class pollingservice extends Service {
	int nums = 0;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		nums = 0;
		Intent intent0 = new Intent(this,MainService.class);
		startService(intent0);
		return START_STICKY;

	}
}
