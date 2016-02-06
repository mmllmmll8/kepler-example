package com.example.mulewen.kepler_example_as.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.example.mulewen.newkepler.service.MainService;
import com.example.mulewen.kepler_example_as.R;
import com.example.mulewen.kepler_example_as.tool.ServiceState;

public class MainActivity extends Activity {
	Button list;
	boolean service_close = false;
	Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,  
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        list = (Button)findViewById(R.id.listbutton);

        context = getApplicationContext();
        final SharedPreferences sharepreference = getSharedPreferences("exam",0);
        service_close = !ServiceState.serviceisrunning(context);
        if(service_close){
        	startService(new Intent(this,MainService.class));
        }

        list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,ListActivity.class);
				startActivity(intent);
			}
		});

    }
}

