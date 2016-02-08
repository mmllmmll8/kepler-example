package com.example.mulewen.newkepler.LBS;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;

public class gaode implements AMapLocationListener{

	LatLng point;
	double Accuracy;
	String username = null;
	int time = 1000;
	int scantime = 1000*60;
	int fanwei = 50;
	Context context = null;
	protected LocationManagerProxy mLocationManagerProxy;
	Callback callback;
	static boolean lasttime;
	SharedPreferences share = null;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public gaode(Context activity,Callback callback)
	{
		this.context = activity;
		this.callback = callback;
		this.lasttime = true;
		share = activity.getSharedPreferences("exam", 0);
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		init();
	}

	//list_to_json
	


	protected void init() {
		Log.e("gaode", "init");
		mLocationManagerProxy = LocationManagerProxy.getInstance(
				this.context.getApplicationContext());

		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 
				scantime, 
				80, 
				this);
		mLocationManagerProxy.setGpsEnable(true);
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		Log.e("ok", String.valueOf(amapLocation.getAMapException().getErrorCode()));
		if(amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0){

		
	        Double geoLat = amapLocation.getLatitude();
	        Double geoLng = amapLocation.getLongitude();
	        String city = amapLocation.getCityCode();
	        Editor edit = share.edit();
	        JSONObject gaode = new JSONObject();
	        try {
				gaode.put("lat", String.valueOf(geoLat));
				gaode.put("lng", String.valueOf(geoLng));
	        } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			edit.putString("gaode", gaode.toString());
			edit.commit();
	        Log.e("latlng", String.valueOf(geoLat)+" "+ String.valueOf(geoLng));
	        LatLng nowll = new LatLng(geoLat, geoLng);
	        //Accuracy = amapLocation.getAccuracy();
	        Accuracy = amapLocation.getAccuracy();
	        Accuracy=Accuracy<100?100:Accuracy;
	        if(point!=null){
	        	if((AMapUtils.calculateLineDistance(nowll,point)<=80)){

	        		if(lasttime){
						lasttime = false;
	        			Log.e("latlng", "record");
			        	Date now=new Date();
						String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
						getpoi.start(
								date, 
								Accuracy,
								new LatLonPoint(geoLat, geoLng),
								city,
								context);
	        		}
		        }
	        	else{
	        		lasttime = true;
	        	}
	        }
			point = new LatLng(geoLat, geoLng);
	    }
	}


	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}
}
