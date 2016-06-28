package com.example.mulewen.newkepler.lbs;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.StrictMode;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.example.mulewen.newkepler.framework.Datacenter;
import com.example.mulewen.newkepler.framework.Datashare;
import com.example.mulewen.newkepler.framework.LBS_info_mid;
import com.example.mulewen.newkepler.object.LBSInfo;

public class gaode implements AMapLocationListener{

	LatLng point;
	double Accuracy;
	int scantime = 1000*60*3;
	float scanwide = 80;
	Context context = null;
	protected LocationManagerProxy mLocationManagerProxy;
	Callback callback;
	static boolean lasttime;
	LBS_info_mid lbs_info_mid;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public gaode(Context activity,Callback callback)
	{
		this.context = activity;
		this.callback = callback;
		this.lasttime = false;
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		init();
	}

	protected void init() {
		Log.e("gaode", "init");

		mLocationManagerProxy = LocationManagerProxy.getInstance(
				this.context.getApplicationContext());
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 
				scantime, 
				scanwide,
				this);
		mLocationManagerProxy.setGpsEnable(true);

		lbs_info_mid = LBS_info_mid.getlbsinfomid(context);
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		//
		// TODO Auto-generated method stub
		Log.e("ok", String.valueOf(amapLocation.getAMapException().getErrorCode()));
		if(amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0){

	        Double geoLat = amapLocation.getLatitude();
	        Double geoLng = amapLocation.getLongitude();
	        String city = amapLocation.getCityCode();
	        JSONObject gaode = new JSONObject();
	        try {
				gaode.put("lat", String.valueOf(geoLat));
				gaode.put("lng", String.valueOf(geoLng));
	        } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Datashare datashare = Datacenter.getDatacenter(context).getshared();
			datashare.Savedata("gaode",gaode.toString(),"exam");
	        Log.e("latlng", String.valueOf(geoLat)+" "+ String.valueOf(geoLng));
	        LatLng nowll = new LatLng(geoLat, geoLng);
			Date now=new Date();
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);

			Log.e("now latlng", String.valueOf(datashare.getfloat("lat","exam"))+" "+ String.valueOf(datashare.getfloat("lng","exam")));
			point = new LatLng(datashare.getfloat("lat","exam"),datashare.getfloat("lng","exam"));
	        Accuracy = amapLocation.getAccuracy();
	        Accuracy=Accuracy<100?100:Accuracy;
			if((AMapUtils.calculateLineDistance(nowll,point)<=120)){
				if(lasttime){
					lasttime = false;
					Log.e("lasttime", "false");
					Log.e("latlng", "record");
					getpoi.start(
							date,
							Accuracy,
							new LatLonPoint(geoLat, geoLng),
							city,
							context);
				}
			}else{
				lasttime = true;
				Log.e("lasttime", "true");
			}
			LBSInfo lbsInfo = new LBSInfo();
			lbsInfo.userid = datashare.getstring("exam","userid");
			lbsInfo.date = date;
			lbsInfo.accuracy = Accuracy;
			lbsInfo.lng = geoLng;
			lbsInfo.lat = geoLat;
			LBS_info_mid lbs_info_mid = LBS_info_mid.getlbsinfomid(context);
			lbs_info_mid.addlbss(lbsInfo);
			lbs_info_mid.update();

			Float lat = Float.valueOf(geoLat.toString());
			Float lng = Float.valueOf(geoLng.toString());
			datashare.Savedata("lat",lat,"exam");
			datashare.Savedata("lng",lng,"exam");
	    }
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.e("latlng","2");
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
