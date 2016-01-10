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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mulewen.kepler_example_as.R;

public class ListActivity extends Activity {
	
	private ListView lv;
	private List<Map<String, Object>> data;
	private SharedPreferences sharedpreference;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE );
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	}
	
	private List<Map<String, Object>> getData(String json)
    {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        Map<String, Object> map; 
		
			JSONArray jsonarray = null;
			try {
				jsonarray = new JSONArray(json);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int i = 0;
			while(i<jsonarray.length()){
				JSONObject rec = null;
				try {
					rec = jsonarray.getJSONObject(i);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JSONArray POIS = null;
				try {
					POIS = new JSONArray(rec.getString("poiinfo"));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				map = new HashMap<String, Object>();
				String name = "";
				try {
					name = URLDecoder.decode(POIS.getJSONObject(0).get("name").toString(),"utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            map.put("loc", name);
	            try {
					map.put("time", rec.getString("date"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            map.put("info", rec.toString());
	            list.add(map);
				i++;
			}
		
        return list;
    }
	
    //ViewHolder��̬��  
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
            return data.size();  
        }  
  
        @Override  
        public Object getItem(int position) {
            return position;  
        }  
  
        @Override  
        public long getItemId(int position) {
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
            holder.time.setText((String)data.get(position).get("time"));
            return convertView;
        }
    }  

    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	setContentView(R.layout.activity_list);
        lv = (ListView)findViewById(R.id.lv);  
        
        //��ȡ��Ҫ�󶨵��������õ�data��  
        sharedpreference = getSharedPreferences("exam", MODE_PRIVATE);
        String records = sharedpreference.getString("records", "");
        if(records!=""){
        	data = getData(records); 
        	MyAdapter adapter = new MyAdapter(this);
	        lv.setAdapter(adapter);  
	        lv.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					Map<String,Object> mapinfo = data.get((int) arg3);
					String info = (String) mapinfo.get("info");
					Bundle bundle = new Bundle();
					bundle.putString("info", info);
					bundle.putInt("index", (int) arg3);
					Intent intent = new Intent(ListActivity.this,CorrectActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});       	
        }
    }
}
//try {
//	robject.put("loc", "�Ĵ���ѧ");
//	JSONArray lbss = new JSONArray();
//    lbsobject = new JSONObject();
//    lbsobject.put("lat", "30");
//    lbsobject.put("lng", "100");
//    lbss.put(lbsobject); 
//    lbsobject = new JSONObject();
//    lbsobject.put("lat", "30");
//    lbsobject.put("lng", "100");
//    lbss.put(lbsobject);
//    lbsobject = new JSONObject();
//    lbsobject.put("lat", "30");
//    lbsobject.put("lng", "100");
//    lbss.put(lbsobject);
//    robject.put("lbss",lbss.toString());
//    robject.put("time", "2015-10-31");
//    jrecords.put(robject);
//    
//    robject = new JSONObject();
//    robject.put("loc", "�Ĵ���ѧ");
//    lbss = new JSONArray();
//    lbsobject = new JSONObject();
//    lbsobject.put("lat", "30");
//    lbsobject.put("lng", "100");
//    lbss.put(lbsobject); 
//    lbsobject = new JSONObject();
//    lbsobject.put("lat", "30");
//    lbsobject.put("lng", "100");
//    lbss.put(lbsobject);
//    lbsobject = new JSONObject();
//    lbsobject.put("lat", "30");
//    lbsobject.put("lng", "100");
//    lbss.put(lbsobject); 
//    robject.put("lbss",lbss.toString());
//    robject.put("time", "2015-10-31");
//    jrecords.put(robject);
//    
//    robject = new JSONObject();
//    robject.put("loc", "�Ĵ���ѧ");
//    lbss = new JSONArray();
//    lbsobject = new JSONObject();
//    robject.put("lat", "30");
//    robject.put("lng", "100");
//    lbss.put(lbsobject); 
//    lbsobject = new JSONObject();
//    robject.put("lat", "30");
//    robject.put("lng", "100");
//    lbss.put(lbsobject);
//    lbsobject = new JSONObject();
//    robject.put("lat", "30");
//    robject.put("lng", "100");
//    lbss.put(lbsobject);
//    robject.put("lbss",lbss.toString());
//    robject.put("time", "2015-10-31");
//    jrecords.put(robject);
//    
//} catch (JSONException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
