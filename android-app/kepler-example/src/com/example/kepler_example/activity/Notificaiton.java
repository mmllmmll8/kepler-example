package com.example.kepler_example.activity;
//弃用
import java.util.List;

import com.example.kepler_example.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;



public class Notificaiton extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);    
	    Shownotification();
	}
	
	public void Shownotification() {
		 String ns = Context.NOTIFICATION_SERVICE;
		 NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		 int icon = R.drawable.background;
		 CharSequence tickerText = "Notice from Kepler"; 
		 long when = System.currentTimeMillis(); 
		 Notification notification = new Notification(icon,tickerText,when);
		 Context context = getApplicationContext();
	     CharSequence contentTitle = "New location!";
		 CharSequence contentText = "New sites needed to be signed!";
		 Intent notificationIntent = new Intent(this,List.class);
		 PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
		 notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		 mNotificationManager.notify(0,notification);// TODO Auto-generated method stub
	}
}