package com.example.experiment;

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
	Button endbutton;
	Button list;
	Boolean service_close; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        endbutton = (Button)findViewById(R.id.endbutton);
        list = (Button)findViewById(R.id.listbutton);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        this.getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//进行全屏   
        SharedPreferences sharepreference = getPreferences(MODE_PRIVATE);
        service_close = sharepreference.getBoolean("service_close", false);
        endbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(service_close){
				   //buttontext改为关闭服务
				   //重启服务 
				}else{
				   //buttontext改为开始服务
				   //发布广播关闭服务
				}
			}
		});
        
        list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//启动list activity
				Intent intent = new Intent(MainActivity.this,ListActivity.class);
				startActivity(intent);
				finish();
			}
		});
        
    }
}
