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
import com.example.mulewen.newkepler.framework.Records_info_mid;
import com.example.mulewen.newkepler.object.POI_Info;
import com.example.mulewen.newkepler.object.REC_Info;


public class CorrectActivity extends Activity{
	private ListView lv;
	private List<Map<String, Object>> data;
	private int recordindex = 0;
	private int poiindex = 0;
	private Records_info_mid records_mid = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		records_mid = Records_info_mid.getpoiinfomid(this);
		setContentView(R.layout.activity_correct);
        lv = (ListView)findViewById(R.id.correct);
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
                REC_Info choose = records_mid.getRecinfos().remove(recordindex);
				if(poi.get("info")!=""){
                    choose.pois.add(0,choose.pois.get(poiindex));
                    records_mid.addnrecs(choose);
                    records_mid.update();
				}
				finish();
            }  
        }); 
        final AlertDialog dlg = normalDia.create();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        recordindex = bundle.getInt("index");
        data = getData(records_mid.getRecinfos().get(recordindex).pois);
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

	private List<Map<String, Object>> getData(ArrayList<POI_Info> pois)
    {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
		for (POI_Info poi:pois
			 ) {
			map = new HashMap<String, Object>();
            String name = "";
            String type = "";
            try {
                name = URLDecoder.decode(poi.name,"utf-8");
                type = URLDecoder.decode(poi.type,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            map.put("loc", name);
			map.put("info", type);
			list.add(map);
		}
		map = new HashMap<String, Object>();
		map.put("loc", "都不是");
		map.put("info", "");
		list.add(map);
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
