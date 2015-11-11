package com.example.experiment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.kepler.service.MainService;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	Button controlbutton;
	Button list;
	Boolean service_close; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ������
        this.getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,  
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);//����ȫ��   
        setContentView(R.layout.activity_main);
        controlbutton = (Button)findViewById(R.id.endbutton);
        list = (Button)findViewById(R.id.listbutton);
        final SharedPreferences sharepreference = getSharedPreferences("exam",0);
        service_close = sharepreference.getBoolean("service_close", true);
        
        if(service_close){        	
        	startService(new Intent(this,com.example.kepler.service.MainService.class));
        	controlbutton.setText("�رշ���");
        }
        controlbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				service_close = sharepreference.getBoolean("service_close", true);
				if(service_close){
				   //buttontext��Ϊ�رշ���
				   //�������� 
					Intent intent=new Intent(MainActivity.this,com.example.kepler.service.MainService.class);
					intent.setAction("com.example.kepler.service.MainService");
					startService(intent);
					controlbutton.setText("�رշ���");
				}else{
				   //buttontext��Ϊ��ʼ����
				   //�����㲥�رշ���
					Intent intent=new Intent(MainActivity.this,com.example.kepler.service.MainService.class);
					intent.setAction("com.example.kepler.service.MainService");
					boolean a = stopService(intent);
 					if(!a){
						intent=new Intent(MainActivity.this,com.example.kepler.service.MainService.class);
						intent.setAction("com.example.kepler.service.MainService");
						startService(intent);
						controlbutton.setText("�رշ���");
					}
					else{						
						controlbutton.setText("��������");
					}
				}
			}
		});
        
        list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//����list activity
				Intent intent = new Intent(MainActivity.this,ListActivity.class);
				startActivity(intent);
			}
		});
        
    }
}
