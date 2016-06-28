package com.example.kepler_example.activity;
//弃用
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.LatLng;
import com.example.kepler_example.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Map_Activity extends Activity {
	private MapView mapView;
	private AMap aMap;
	private WindowManager wm;
	private LatLng latlng;
	private Button ok;
	JSONObject jinfo;
	JSONArray lbsarray;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_map_);
		mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
		init();
		ok = (Button)findViewById(R.id.buttonok);
        wm = (WindowManager) this.getWindowManager();
        latlng = new LatLng(30.557973,104.001632);
		

		if(latlng!=null){
			aMap.animateCamera(CameraUpdateFactory
					.newLatLngZoom(
							latlng, 
							aMap.getMaxZoomLevel()-2),
							1000,
							null);			
		}
		
		AlertDialog.Builder normalDia=new AlertDialog.Builder(Map_Activity.this);
        normalDia.setIcon(R.drawable.ic_launcher);  
        normalDia.setTitle("");
        normalDia.setMessage("");
        
        normalDia.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                
            }  
        });  
        normalDia.setNegativeButton("", new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi") @Override  
            public void onClick(DialogInterface dialog, int which) {  
                // TODO Auto-generated method stub  
            	int screenwidth = wm.getDefaultDisplay().getWidth();
				int screenHeight = wm.getDefaultDisplay().getHeight();
				Projection myProjection =aMap.getProjection();
				Point leftPoint = new Point(screenwidth/2,screenHeight/2);
				LatLng leftlatlng = myProjection.fromScreenLocation(leftPoint);
				
            	SharedPreferences share = getSharedPreferences("exam",0);
				String recs = share.getString("records", "");
				if(recs!=""){
					try {
						JSONArray jrecs = new JSONArray(recs);
						JSONObject jrec = jrecs.getJSONObject(jinfo.getInt("index"));
						
						jrecs.remove(jinfo.getInt("index"));
						JSONArray lbss = new JSONArray(jrec.getString("lbsinfo"));

 						JSONObject real = new JSONObject();
						real.put("lat", leftlatlng.latitude);
						real.put("lng", leftlatlng.longitude);
						

						lbss.put(real);

						jrec.put("lbsinfo", lbss.toString());
						jrec.put("poiinfo", jrec.getString("poiinfo"));
						jrec.put("date",  jrec.getString("date"));

						String nrecs = share.getString("nrecs", "");
						JSONArray njrecs = null;
						if(nrecs==""){
							njrecs = new JSONArray();
						}
						else{
							njrecs = new JSONArray(nrecs);
						}
						njrecs.put(jrec);

						Log.e("nrecs", njrecs.toString());
						Editor editor = share.edit();
						editor.putString("nrecs", njrecs.toString());
						editor.putString("records", jrecs.toString());
						editor.commit();
						finish();
					} catch (JSONException e) {  
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
            }  
        });  
        final AlertDialog dlg = normalDia.create();
        
		

        ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			    Window window = dlg.getWindow();
			    window.setGravity(Gravity.BOTTOM);
		        dlg.show();  
			}
		});
	}
	private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
    }
	
	 /**
     * ����������д
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
 
    /**
     * ����������д
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
     
    /**
     * ����������д
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
 
    /**
     * ����������д
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
