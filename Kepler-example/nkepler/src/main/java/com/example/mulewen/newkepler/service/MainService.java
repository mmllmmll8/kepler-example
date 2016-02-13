package com.example.mulewen.newkepler.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

import com.example.mulewen.newkepler.LBS.baidu;
import com.example.mulewen.newkepler.LBS.gaode;
import com.example.mulewen.newkepler.LBS.tencent;
import com.example.mulewen.newkepler.runnables.nrecs_runnable;
import com.example.mulewen.newkepler.runnables.recs_runnable;
import com.example.mulewen.newkepler.tools.NetworkConnectChangedReceiver;

import java.util.ArrayList;

public class MainService extends Service{
	baidu baidu_server;
	gaode gaode_server;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.e("caonima", "service binder");
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("TAG", "process id is " + Process.myPid());
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//Bundle bundle =  intent.getBundleExtra("username");
		Log.e("service", "service start");
		mycallback callback = new mycallback();
		init(this.getApplicationContext(),callback);
		flags = START_STICKY;  
		return super.onStartCommand(intent, flags, startId);  
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		baidu_server.stop();
		Log.e("service", "service stop");
	}
	
	private void init(Context context,mycallback callback){
		Log.e("service","service init");
		gaode_server = new gaode(context,callback);
		//填充wifi触发的runnable
		ArrayList<Runnable> runnables = new ArrayList<Runnable>();
		Runnable nrecs_Runnable = new nrecs_runnable(context);
		runnables.add(nrecs_Runnable);
		NetworkConnectChangedReceiver.setrunnables(runnables);
		if(NetworkConnectChangedReceiver.isWifiConnected(context)){
			NetworkConnectChangedReceiver.connectServer();
		}
	}
}
