package com.example.experiment;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
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
		setContentView(R.layout.activity_list);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ������
        this.getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//����ȫ��   
        lv = (ListView)findViewById(R.id.lv);  
        //��ȡ��Ҫ�󶨵��������õ�data��  
        sharedpreference = getPreferences(MODE_PRIVATE);
        String records = sharedpreference.getString("records", "");
        data = getData(records);
        MyAdapter adapter = new MyAdapter(this);
        lv.setAdapter(adapter);  
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private List<Map<String, Object>> getData(String json)
    {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        Map<String, Object> map; 
		try {
			JSONArray jsonarray = new JSONArray(json);
			int i = 0;
			while(i<jsonarray.length()){
				JSONObject rec = jsonarray.getJSONObject(i);
				map = new HashMap<String, Object>();
	            map.put("loc", rec.getString("loc"));
	            map.put("time", rec.getString("time"));
	            list.add(map);
				i++;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return list;
    }
	
    //ViewHolder��̬��  
    static class ViewHolder  
    {    
        public TextView local;  
        public TextView time;
        public String info;
    }  
    
    public class MyAdapter extends BaseAdapter  
    {      
        private LayoutInflater mInflater = null;  
        private MyAdapter(Context context)  
        {  
            //����context�����ļ��ز��֣��������Demo17Activity������this  
            this.mInflater = LayoutInflater.from(context);  
        }  
  
        @Override  
        public int getCount() {  
            //How many items are in the data set represented by this Adapter.  
            //�ڴ�������������������ݼ��е���Ŀ��  
            return data.size();  
        }  
  
        @Override  
        public Object getItem(int position) {  
            // Get the data item associated with the specified position in the data set.  
            //��ȡ���ݼ�����ָ��������Ӧ��������  
            return position;  
        }  
  
        @Override  
        public long getItemId(int position) {  
            //Get the row id associated with the specified position in the list.  
            //��ȡ���б�����ָ��������Ӧ����id  
            return position;  
        }  
          
        //Get a View that displays the data at the specified position in the data set.  
        //��ȡһ�������ݼ���ָ����������ͼ����ʾ����  
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            ViewHolder holder = null;  
            //�������convertViewΪ�գ�����Ҫ����View  
            if(convertView == null)  
            {
                holder = new ViewHolder();
                //�����Զ����Item���ּ��ز���  
                convertView = mInflater.inflate(R.layout.listitem, null);
                holder.local = (TextView)convertView.findViewById(R.id.Itemtitle);
                holder.time = (TextView)convertView.findViewById(R.id.ItemText);
                //�����úõĲ��ֱ��浽�����У�������������Tag��Ա���淽��ȡ��Tag  
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
}
