package com.example.notifyclientdemo;

import cn.jarlen.notify.notifyserver.aidl.INotifyService;
import cn.jarlen.notify.notifyserver.bean.NotifyBean;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	private INotifyService notifyService = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.bind).setOnClickListener(this);
		findViewById(R.id.push).setOnClickListener(this);
		findViewById(R.id.unbind).setOnClickListener(this);
	}

	private Handler handler = new Handler();

	private Runnable task = new Runnable() {
		public void run() {
			// TODO Auto-generated method stub
			handler.postDelayed(this, 5 * 1000);// 设置延迟时间，此处是5秒
			// 需要执行的代码
			if (notifyService != null) {

				NotifyBean bean = new NotifyBean();
				bean.setNotifyContent("测试content");
				bean.setNotifyTitle("测试title");
				bean.setNotifyType(0);
				try {
					notifyService.pushNotify(bean);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	};

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.bind:
			Intent intent = new Intent();
			intent.setAction("com.xdja.atws.notify.service.NotifyService");
			intent.setPackage("com.xdja.atws.notify.service");
			bindService(intent, conn, Context.BIND_AUTO_CREATE);
			break;
		case R.id.push:
			handler.postDelayed(task, 5000);// 延迟调用
			break;
		case R.id.unbind:
			unbindService(conn);
			break;

		default:
			break;
		}
	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			notifyService = INotifyService.Stub.asInterface(arg1);
		}
	};

}
