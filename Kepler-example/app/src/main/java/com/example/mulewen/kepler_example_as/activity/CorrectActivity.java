package com.example.mulewen.kepler_example_as.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.mulewen.kepler_example_as.R;


public class CorrectActivity extends Activity{
	private ListView lv;
	private List<Map<String, Object>> data;
	private SharedPreferences sharedpreference;
	private int recordindex = 0;
	private int poiindex = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_correct);
        lv = (ListView)findViewById(R.id.correct);
        sharedpreference = getSharedPreferences("exam",MODE_PRIVATE);
        
        AlertDialog.Builder normalDia=new AlertDialog.Builder(CorrectActivity.this);
        
        normalDia.setIcon(R.drawable.ic_launcher);  
        normalDia.setTitle("poi选择");
        normalDia.setMessage("确认选择？");
        
        normalDia.setPositiveButton("不是", new DialogInterface.OnClickListener() {
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                
            }  
        });  
        normalDia.setNegativeButton("是", new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi") @Override  
            public void onClick(DialogInterface dialog, int which) {  
                // TODO Auto-generated method stub  
            	Map<String,Object>poi = data.get(poiindex);
				Editor edit = sharedpreference.edit();
            	try {
            		String record = sharedpreference.getString("records", "");
            		JSONArray jrecords = new JSONArray(record);
            		JSONObject jobject = (JSONObject) jrecords.get(recordindex);
					if(poi.get("info")!=""){
			        	JSONArray pois = jobject.getJSONArray("poiinfo");
			        	JSONObject index = new JSONObject();
			        	index.put("index", poiindex);
			        	pois.put(index);
			        	jobject.put("poiinfo", pois.toString());
						String nrecs = sharedpreference.getString("nrecs", "");
						JSONArray njrecs = null;
						if(nrecs==""){
							njrecs = new JSONArray();
						}
						else{
							njrecs = new JSONArray(nrecs);
						}
						njrecs.put(jobject);
						
						edit.putString("nrecs", njrecs.toString());
					}
		        	jrecords.remove(recordindex);
			        edit.putString("records", jrecords.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				edit.commit();
				finish();
            }  
        }); 
        final AlertDialog dlg = normalDia.create();
        
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        data = getData(bundle.getString("info"));
        recordindex = bundle.getInt("index");
        MyAdapter adapter = new MyAdapter(this);
        lv.setAdapter(adapter);  
        lv.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi") 
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				poiindex = (int)arg3;
				Window window = dlg.getWindow();
				window.setGravity(Gravity.CENTER);
			    dlg.show();
			}
		});
	}
	
	private List<Map<String, Object>> getData(String json) 
    {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        Map<String, Object> map; 
		try {
			JSONObject jsonobject = new JSONObject(json);
		    JSONArray POIS =new JSONArray(jsonobject.getString("poiinfo"));
		    int i = 0;
			while(i<POIS.length()){
				map = new HashMap<String, Object>();
				String name = "";
				try {
					name = URLDecoder.decode(POIS.getJSONObject(i).get("name").toString(),"utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String type = "";
				try {
					type = URLDecoder.decode(POIS.getJSONObject(i).get("type").toString(),"utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            map.put("loc", name);
	            map.put("info", type);
	            list.add(map);
				i++;
			}
			map = new HashMap<String, Object>();
			map.put("loc", "都不是");
            map.put("info", "");
            list.add(map);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return list;
    }

    static class ViewHolder  
    {    
        public TextView local;  
        public TextView time;
    }  
    
    public class MyAdapter extends BaseAdapter  
    {      
        private LayoutInflater mInflater = null;  
        private MyAdapter(Context context)  
        {
            this.mInflater = LayoutInflater.from(context);  
        }  
  
        @Override  
        public int getCount() {  
            //How many items are in the data set represented by this Adapter.
            return data.size();  
        }  
  
        @Override  
        public Object getItem(int position) {  
            // Get the data item associated with the specified position in the data set.
            return position;  
        }  
  
        @Override  
        public long getItemId(int position) {  
            //Get the row id associated with the specified position in the list.
            return position;  
        }  
          
        //Get a View that displays the data at the specified position in the data set.
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            ViewHolder holder = null;
            if(convertView == null)  
            {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.listitem, null);
                holder.local = (TextView)convertView.findViewById(R.id.Itemtitle);
                holder.time = (TextView)convertView.findViewById(R.id.ItemText);
                convertView.setTag(holder);
            }else
            {
                holder = (ViewHolder)convertView.getTag();  
            }
            holder.local.setText((String)data.get(position).get("loc"));
            holder.time.setText((String)data.get(position).get("info"));
            return convertView;
        }
    }
}
