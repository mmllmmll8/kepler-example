package com.example.mulewen.kepler_example_as.receiver;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import com.example.mulewen.newkepler.service.MainService;

import com.example.mulewen.kepler_example_as.tool.ServiceState;

public class NetworkConnectReceiver extends BroadcastReceiver{
	Thread thread ;
    static boolean laststate = true;
    SharedPreferences share = null;
    String content = null;
    Context context = null;
    @SuppressLint("NewApi") @Override
    public void onReceive(final Context context, Intent intent) {
    	this.context = context;
        share = context.getSharedPreferences("exam", 0);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        content = share.getString("nrecs", "");
        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
        if (content!=""&&WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                State state = networkInfo.getState();
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


//    final Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//        	Log.e("haha", "ahahahah");
//            //查看service是否还在线
//        	//如果在线在线不在线就重启
//        	
//        }
//    };
//    private void connectServer(Context context){
//        List<NameValuePair> params=new ArrayList();
//        //添加参数
//
//        Log.v("@@@@@@@@@@@@@", "######################");
//        HttpGet httpGet = new HttpGet("https://www.baidu.com/");
//        HttpClient httpClient = new DefaultHttpClient();
//        // 发送请求
//        try
//        {
//        	new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					
//				}
//			}).start();
//            HttpResponse response = httpClient.execute(httpGet);
//            int code = response.getStatusLine().getStatusCode();
//            if(code==200)
//            {
//               Log.e("############LoginActivity","wifi!!!!!!!!!!!!!!!!!");
//	           thread = new Thread(runnable);
//	           thread.start();
//	        }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//    
//    public boolean isWifiConnected(Context context) {  
//        if (context != null) {  
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);  
//            NetworkInfo mWiFiNetworkInfo = mConnectivityManager  
//                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
//            if (mWiFiNetworkInfo != null) {  
//                return mWiFiNetworkInfo.isAvailable();  
//            }  
//        }  
//        return false;  
//    }
//JSONObject jobject = new JSONObject();
//jobject.put("date", "2015-11-15");
//jobject.put("userid", "tonypod");
//jobject.put("lbsinfo", "asdfasdf");
//jobject.put("poiinfo", "caoadsfasdfsdafasdfnima");
//jarray.put(info);
//String haha = URLDecoder.decode("%E5%95%86%E5%8A%A1%E4%BD%8F%E5%AE%85%3B%E4%BD%8F%E5%AE%85%E5%8C%BA%3B%E5%AE%BF%E8%88%8D","utf-8");
//Log.e("haha2",haha);
//strUTF8 = URLEncoder.encode(strUTF8,"utf-8");
//Log.e("haha3",strUTF8);
//URLEncoder.encode(strUTF8, "utf-8");
//Log.e("haha", strUTF8);
//HttpClient httpClient=new DefaultHttpClient();
//HttpPost httpPost=new HttpPost("http://121.42.136.178:10086/RecordHandler.ashx");
//httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//StringEntity stringentity = new StringEntity(strUTF8);
//stringentity.setContentEncoding("utf-8");
//httpPost.setEntity(stringentity);