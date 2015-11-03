package cn.jarlen.notify.notify.service;

import cn.jarlen.notify.notify.utils.NotifyUtil;
import cn.jarlen.notify.notifyserver.aidl.INotifyService;
import cn.jarlen.notify.notifyserver.bean.NotifyBean;
import cn.jarlen.notify.service.R;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Service;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 通知服务
 * 
 * @author jarlen
 * 
 */
public class NotifyService extends Service {

	/**
	 * 消息dialog队列
	 */
	private NotifyQueue messageQueue = null;

	/**
	 * 当前是否有dialog正在show
	 */
	private boolean isDialogShowing = false;

	private boolean isClickOk = false;
	
	private AlertDialog dialog = null;

	@Override
	public void onCreate() {
		super.onCreate();
		messageQueue = new NotifyQueue();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	private INotifyService.Stub mBinder = new INotifyService.Stub() {

		@Override
		public void pushNotify(NotifyBean notify) throws RemoteException {

			Log.i("===", "get a Message");
			NotifyUtil.showNotify(getApplicationContext(), notify);

			MessageAction action = new MessageAction(notify);

			if (messageQueue == null) {
				messageQueue = new NotifyQueue();
			}
			messageQueue.add(action);
		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			NotifyBean bean = (NotifyBean) msg.obj;
			isClickOk = false;
			
			final View dialogView = LayoutInflater.from(getApplicationContext())
					.inflate(R.layout.layout_dialog, null);
			
			Builder builder = new AlertDialog.Builder(getApplicationContext());
			dialog = builder.create();
			dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			dialog.getWindow().setWindowAnimations(R.style.CustomDialogTheme);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			Window window = dialog.getWindow();
			window.setGravity(Gravity.CENTER);
			window.setContentView(dialogView);
			
			final TextView timeTv = (TextView)window.findViewById(R.id.dialog_time);
			
			final CountDownTimer timer = new CountDownTimer(10000,1000) {
				
				@Override
				public void onTick(long arg0) {
					timeTv.setText("("+arg0/1000+"s)");
				}
				
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			};
			
			timer.start();
			
			window.findViewById(R.id.dialog_left_btn).setOnClickListener(new OnClickListener() {
				
				public void onClick(View view) {
					Log.i("===","  取消 ");
					isClickOk = false;
					dialog.cancel();
				}
			});
			
			
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface arg0) {
					
					Toast.makeText(getApplicationContext(), " isClickOk : "+isClickOk, Toast.LENGTH_SHORT).show();
					if(timer != null){
						timer.cancel();
					}
					if (messageQueue != null) {
						messageQueue.notifyNextAction();
					}
				}
			});
			
			window.findViewById(R.id.dialog_right_btn).setOnClickListener(new OnClickListener() {
				
				public void onClick(View view) {
					Log.i("===", " 确认 ");
					isClickOk = true;
					dialog.cancel();
				}
			});
			
		};
	};

	private class MessageAction extends NotifyQueue.Action<NotifyBean> {

		public MessageAction(NotifyBean badge) {
			super(badge);
		}

		@Override
		public void onAction() {

			Log.i("===", " onAction ");
			Message msg = mHandler.obtainMessage();
			msg.obj = getBadge();
			msg.sendToTarget();
		}
	}
}
