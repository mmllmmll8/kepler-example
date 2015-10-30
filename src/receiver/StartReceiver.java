package receiver;

import com.example.kepler.service.MainService;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class StartReceiver extends BroadcastReceiver	{

	static final String ACTION = "android.intent.action.BOOT_COMPLETED";   
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		if (arg1.getAction().equals(ACTION))    
        {   
			SharedPreferences sharedPreferences= arg0.getSharedPreferences("test",Activity.MODE_PRIVATE); 
			// 使用getString方法获得value，注意第2个参数是value的默认值 
			String name =sharedPreferences.getString("name", ""); 
			if(name!=""){
				arg0.startService(new Intent(arg0,MainService.class));//启动倒计时服务   
				Toast.makeText(arg0, "实验服务启动", Toast.LENGTH_LONG).show();
			}  
            //这边可以添加开机自动启动的应用程序代码   
			//启动service
        }   
	}

}
