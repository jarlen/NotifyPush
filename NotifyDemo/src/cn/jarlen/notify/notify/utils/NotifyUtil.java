package cn.jarlen.notify.notify.utils;

import java.util.List;

import cn.jarlen.notify.notify.receiver.NotificationReceiver;
import cn.jarlen.notify.notifyserver.bean.NotifyBean;
import cn.jarlen.notify.service.R;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * 通知工具类
 * 
 * @author hjl
 * 
 */
public class NotifyUtil {

	/**
	 * 判断是否锁屏
	 * 
	 * @param ctx
	 *            上下文
	 * @return
	 */
	public static boolean isScreenKeyguard(Context ctx) {
		KeyguardManager mKeyguardManager = (KeyguardManager) ctx
				.getSystemService(ctx.KEYGUARD_SERVICE);
		if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 展示通知
	 * 
	 * @param ctx
	 *            上下文
	 * @param bean
	 *            通知数据
	 */
	public static void showNotify(Context context, NotifyBean bean) {
		NotifyBean notifyBean = bean;

		Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
		broadcastIntent.putExtra("Notify", notifyBean);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context);
		builder.setContentTitle(bean.getNotifyTitle())
				.setContentText(bean.getNotifyContent())
				.setTicker("这是通知的ticker")
				.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_ALL)
				.setContentIntent(pendingIntent)
				.setSmallIcon(R.drawable.ic_launcher);

		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(2, builder.build());
	}

	/**
	 * 判断应用是否已经启动 com.xdja.mdmc.project
	 * 
	 * @param context
	 *            上下文
	 * @param packageName
	 *            包名
	 * @return
	 */
	public static boolean isAppAlive(Context context, String packageName) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager
				.getRunningAppProcesses();
		for (int i = 0; i < processInfos.size(); i++) {
			if (processInfos.get(i).processName.equals(packageName)) {
				Log.i("===", String.format(
						"the %s is running, isAppAlive return true",
						packageName));
				return true;
			}
		}
		Log.i("===", String.format(
				"the %s is not running, isAppAlive return false", packageName));
		return false;
	}
}
