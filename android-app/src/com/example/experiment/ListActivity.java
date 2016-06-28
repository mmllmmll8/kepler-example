package com.example.experiment;
 
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends Activity {
	
	private ListView lv;
	private List<Map<String, Object>> data;
	private SharedPreferences sharedpreference;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);//进行全屏   
		
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
	
    //ViewHolder静态类  
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
            //根据context上下文加载布局，这里的是Demo17Activity本身，即this  
            this.mInflater = LayoutInflater.from(context);  
        }  
  
        @Override  
        public int getCount() {  
            //How many items are in the data set represented by this Adapter.  
            //在此适配器中所代表的数据集中的条目数  
            return data.size();  
        }  
  
        @Override  
        public Object getItem(int position) {  
            // Get the data item associated with the specified position in the data set.  
            //获取数据集中与指定索引对应的数据项  
            return position;  
        }  
  
        @Override  
        public long getItemId(int position) {  
            //Get the row id associated with the specified position in the list.  
            //获取在列表中与指定索引对应的行id  
            return position;  
        }  
          
        //Get a View that displays the data at the specified position in the data set.  
        //获取一个在数据集中指定索引的视图来显示数据  
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            ViewHolder holder = null;  
            //如果缓存convertView为空，则需要创建View  
            if(convertView == null)  
            {
                holder = new ViewHolder();
                //根据自定义的Item布局加载布局  
                convertView = mInflater.inflate(R.layout.listitem, null);
                holder.local = (TextView)convertView.findViewById(R.id.Itemtitle);
                holder.time = (TextView)convertView.findViewById(R.id.ItemText);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag  
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
        
        //获取将要绑定的数据设置到data中  
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
//	robject.put("loc", "四川大学");
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
//    robject.put("loc", "四川大学");
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
//    robject.put("loc", "四川大学");
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
