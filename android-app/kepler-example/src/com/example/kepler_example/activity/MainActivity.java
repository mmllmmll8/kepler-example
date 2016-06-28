package com.example.kepler_example.activity;

import com.example.kepler.framework.Datacenter;
import com.example.kepler.framework.Datashare;
import com.example.kepler.service.MainService;
import com.example.kepler_example.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	Button list;
	boolean service_close = false;
	Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "process id is " + Process.myPid());
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,  
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        list = (Button)findViewById(R.id.listbutton);
        context = getApplicationContext();

        list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,ListActivity.class);
				startActivity(intent);
			}
		});
		final Datashare datashare = Datacenter.getDatacenter(context).getshared();
		final EditText editText = (EditText)findViewById(R.id.scantime);
		Button ScanButton = (Button)findViewById(R.id.zero);
		ScanButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,MainService.class);
				stopService(intent);
        		Bundle bundle = new Bundle();
        		int scan = Integer.valueOf(editText.getText().toString());
        		datashare.Savedata("scantime", scan,"exam");
        		bundle.putInt("scantime", scan);
        		intent.putExtras(bundle);
        		startService(intent);
			}
		});
		
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) @Override
    public void onTrimMemory(int level) {
    	// TODO Auto-generated method stub
    	super.onTrimMemory(level);
    	if(level!=20){
    		
    	}
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
//    		Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());  
//    		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
//    		startActivity(i);
