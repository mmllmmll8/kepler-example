package com.example.kepler_example.broadcast;

//弃用
import com.example.kepler_example.R;
import com.example.kepler_example.activity.ListActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class record_broadcast {
	public void Shownotification(Context context) {
		 String ns = Context.NOTIFICATION_SERVICE;
		 NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
		 int icon = R.drawable.background;
		 CharSequence tickerText = "Notice from Kepler";
		 long when = System.currentTimeMillis(); 
		 Notification notification = new Notification(icon,tickerText,when);
	     CharSequence contentTitle = "New location!";
		 CharSequence contentText = "New sites needed to be signed!";
		 Intent notificationIntent = new Intent(context,ListActivity.class);
		 PendingIntent contentIntent = PendingIntent.getActivity(context,0,notificationIntent,0);
		 notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		 mNotificationManager.notify(0,notification);// TODO Auto-generated method stub
	}
}
