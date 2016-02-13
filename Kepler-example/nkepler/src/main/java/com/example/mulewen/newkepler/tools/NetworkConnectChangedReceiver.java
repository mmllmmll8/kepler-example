package com.example.mulewen.newkepler.tools;

import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;


public class NetworkConnectChangedReceiver extends BroadcastReceiver{
    static boolean laststate = true;
    SharedPreferences share = null;
    String content = null;
    static ArrayList<Runnable> runnables = null;

    public static void setrunnables(ArrayList<Runnable> runnable){
        runnables = runnable;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                State state = networkInfo.getState();
                boolean isConnected = state == State.CONNECTED;// 当然，这边可以更精确的确定状态

                if(isConnected&&!laststate){
                    Log.e("receiver", "11111");
                    connectServer();
                }else {
                    Log.e("receiver", "222222");
                }
                laststate = isConnected;
            }
        }
    }

    public static void connectServer(){
        Log.v("@@@@@@@@@@@@@", "######################");
        HttpGet httpGet = new HttpGet("https://www.baidu.com/");
        HttpClient httpClient = new DefaultHttpClient();
        // 发送请求
        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            if(code==200)
            {
                Log.e("########LoginActivity","wifi!!!!!!!!!");
                for (Runnable a:runnables
                     ) {
                    Thread thread = new Thread(a);
                    thread.start();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void startinit(Context context){
        if(isWifiConnected(context)){
            connectServer();
        }
    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {  
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
                    .getSystemService(Context.CONNECTIVITY_SERVICE);  
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager  
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
            if (mWiFiNetworkInfo != null) {  
                return mWiFiNetworkInfo.isAvailable();  
            }  
        }  
        return false;  
    }

}
