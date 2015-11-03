package cn.jarlen.notify.notify.receiver;

import cn.jarlen.notify.notify.utils.NotifyUtil;
import cn.jarlen.notify.notifyserver.bean.NotifyBean;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		NotifyBean bean = intent.getParcelableExtra("Notify");
		
		/** 判断应用是否启动 **/
		if (NotifyUtil.isAppAlive(context, "com.jarlen.test")) {
			// 当前应用已启动
			Log.i("===", " live ");
			Intent mainIntent  = new Intent();
			mainIntent .setClassName("com.jarlen.test", "com.jarlen.notify.MainActivity");
			mainIntent .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Intent detailIntent = new Intent();
			detailIntent.setClassName("com.jarlen.test", "com.jarlen.notify.SecondActivity");
			detailIntent.putExtra("Notify",bean.getNotifyContent());
			Intent[] intents = {mainIntent, detailIntent};
			context.startActivities(intents);
			
		} else {
			// 当前应用未启动
			Log.i("===", "not live");

			Intent launchIntent = context.getPackageManager()
					.getLaunchIntentForPackage("com.jarlen.test");
			launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			launchIntent.putExtra("Notify",bean.getNotifyContent());
			context.startActivity(launchIntent);
		}
	}

}
