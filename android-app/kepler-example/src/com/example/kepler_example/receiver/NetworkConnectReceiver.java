package com.example.kepler_example.receiver;


import com.example.kepler.service.MainService;
import com.example.kepler_example.activity.StartActivity;
import com.example.kepler_example.tool.ServiceState;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;

public class NetworkConnectReceiver extends BroadcastReceiver{
	Thread thread ;
    static boolean laststate = true;
    SharedPreferences share = null;
    String content = null;
    Context context = null;
    @SuppressLint("NewApi") @Override
    public void onReceive(final Context context, Intent intent) {
    	this.context = context;
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
        if (content!=""&&WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                State state = networkInfo.getState();
                Log.e("wifi check", "check service");
                boolean isConnected = state == State.CONNECTED;// 当然，这边可以更精确的确定状态
                if(isConnected&&!laststate){
                    Log.e("rer", "11111"); 
                    boolean service_close = !ServiceState.serviceisrunning(context);
                	if(service_close){
                		Intent intents=new Intent(context, MainService.class);
        			    intents.setAction("com.example.kepler.service.MainService");
        			    context.startService(intents);

                	}
                }else {
                    Log.e("resr", "222222");
                }
                laststate = isConnected;
            }
        }
    }
}
