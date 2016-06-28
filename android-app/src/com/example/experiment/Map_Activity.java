package com.example.experiment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//进行全屏   
		setContentView(R.layout.activity_map_);
		mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 必须要写
		init();
		ok = (Button)findViewById(R.id.buttonok);
        wm = (WindowManager) this.getWindowManager();
//        Intent intent = this.getIntent();
//		Bundle bundle = intent.getExtras();
//		String info = bundle.getString("info");
//        try {
//			jinfo = new JSONObject(info);//从intent传入的数据
//			lbsarray = new JSONArray(jinfo.get("lbsinfo").toString());
//			//lbsarray = jinfo.getJSONArray("lbss");
//			
//			latlng = new LatLng(
//					lbsarray.getJSONObject(0).getDouble("lat"),
//					lbsarray.getJSONObject(0).getDouble("lng"));
//			
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        latlng = new LatLng(30.557973,104.001632);
		
        //初始化地图，把镜头移到固定的位置。
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
        normalDia.setTitle("poi定位");  
        normalDia.setMessage("poi标记的是正确的位置么？");  
        
        normalDia.setPositiveButton("不是", new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                
            }  
        });  
        normalDia.setNegativeButton("是", new DialogInterface.OnClickListener() {  
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
						JSONArray jrecs = new JSONArray(recs);//rec数组
						JSONObject jrec = jrecs.getJSONObject(jinfo.getInt("index"));//取出点击项对应的记录
						
						jrecs.remove(jinfo.getInt("index"));
						JSONArray lbss = new JSONArray(jrec.getString("lbsinfo"));//取出lbs数组
						//构建新的lbs点
 						JSONObject real = new JSONObject();
						real.put("lat", leftlatlng.latitude);
						real.put("lng", leftlatlng.longitude);
						
						//更新lbs数组
						lbss.put(real);
						//更新rec内容
						jrec.put("lbsinfo", lbss.toString());
						jrec.put("poiinfo", jrec.getString("poiinfo"));
						jrec.put("date",  jrec.getString("date"));
						//新建rec数组
						String nrecs = share.getString("nrecs", "");
						JSONArray njrecs = null;
						if(nrecs==""){
							njrecs = new JSONArray();
						}
						else{
							njrecs = new JSONArray(nrecs);
						}
						njrecs.put(jrec);
						//提交rec
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
        
		
        //点击确定后修改share中的数据，将新的组合存到新的jsonarray等待发送。
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
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
 
    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
     
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
 
    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
