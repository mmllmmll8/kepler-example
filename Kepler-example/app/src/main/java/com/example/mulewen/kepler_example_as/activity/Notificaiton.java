package com.example.mulewen.kepler_example_as.activity;

import java.util.List;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mulewen.kepler_example_as.R;


public class Notificaiton extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);    
	            	Shownotification();
//	            	Intent intent = new Intent();
//	                intent.setClass(MainActivity.this, Notification.class);
//	       		    startActivity(intent);

	}
	
	public void Shownotification() {
		 String ns = Context.NOTIFICATION_SERVICE;
		 NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		 int icon = R.drawable.background; //֪ͨͼ��
		 CharSequence tickerText = "Notice from Kepler"; //״̬����ʾ��֪ͨ�ı���ʾ
		 long when = System.currentTimeMillis(); //֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
		 //����������Գ�ʼ�� Nofification
		 Notification notification = new Notification(icon,tickerText,when);
		 Context context = getApplicationContext(); //������
	     CharSequence contentTitle = "New location!"; //֪ͨ������
		 CharSequence contentText = "New sites needed to be signed!"; //֪ͨ������
		 Intent notificationIntent = new Intent(this,List.class); //�����֪ͨ��Ҫ��ת��Activity
		 PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
		 notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		 mNotificationManager.notify(0,notification);// TODO Auto-generated method stub
			
	}
}