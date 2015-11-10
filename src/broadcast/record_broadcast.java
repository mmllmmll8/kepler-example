package broadcast;

import java.util.List;

import com.example.experiment.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class record_broadcast {
	//发通知栏广播
	public void Shownotification(Context context) {
		 String ns = Context.NOTIFICATION_SERVICE;
		 NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
		 int icon = R.drawable.background; //通知图标
		 CharSequence tickerText = "Notice from Kepler"; //状态栏显示的通知文本提示
		 long when = System.currentTimeMillis(); //通知产生的时间，会在通知信息里显示
		 //用上面的属性初始化 Nofification
		 Notification notification = new Notification(icon,tickerText,when);
	     CharSequence contentTitle = "New location!"; //通知栏标题
		 CharSequence contentText = "New sites needed to be signed!"; //通知栏内容
		 Intent notificationIntent = new Intent(context,List.class); //点击该通知后要跳转的Activity
		 PendingIntent contentIntent = PendingIntent.getActivity(context,0,notificationIntent,0);
		 notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		 mNotificationManager.notify(0,notification);// TODO Auto-generated method stub
			
	}
}
