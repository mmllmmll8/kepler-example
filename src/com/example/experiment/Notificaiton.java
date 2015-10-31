package com.example.experiment;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


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
				int icon = R.drawable.background; //通知图标
				CharSequence tickerText = "Notice from Kepler"; //状态栏显示的通知文本提示
				long when = System.currentTimeMillis(); //通知产生的时间，会在通知信息里显示
				//用上面的属性初始化 Nofification
				Notification notification = new Notification(icon,tickerText,when);
				Context context = getApplicationContext(); //上下文
				CharSequence contentTitle = "New location!"; //通知栏标题
				CharSequence contentText = "New sites needed to be signed!"; //通知栏内容
				Intent notificationIntent = new Intent(this,List.class); //点击该通知后要跳转的Activity
				PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
				notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
				mNotificationManager.notify(0,notification);// TODO Auto-generated method stub
				
			}
		 

	}
	

