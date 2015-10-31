package receiver;

import com.example.experiment.MainActivity;
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
			//��߿�����ӿ����Զ�������Ӧ�ó������   
			//����service
			SharedPreferences sharedPreferences= arg0.getSharedPreferences("exam",0);
			// ʹ��getString�������value��ע���2��������value��Ĭ��ֵ 
			String name =sharedPreferences.getString("id", ""); 
			if(name!=""){
				arg0.startService(new Intent(arg0,com.example.kepler.service.MainService.class));//��������ʱ����   
				Toast.makeText(arg0, "ʵ���������", Toast.LENGTH_LONG).show();
				 
			}  
        }   
	}
}
