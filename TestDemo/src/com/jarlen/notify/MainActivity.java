package com.jarlen.notify;

import com.jarlen.test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_first);
		findViewById(R.id.testBtn).setOnClickListener(this);
		
		Intent intent = getIntent();
		if(intent != null && intent.getStringExtra("Notify") != null){
			Intent ss = new Intent(this,SecondActivity.class);
			ss.putExtra("Notify", intent.getStringExtra("Notify"));
			this.startActivity(ss);
		}
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.testBtn:
			Intent intent = new Intent(this, SecondActivity.class);
			this.startActivity(intent);
			break;

		default:
			break;
		}

	}

}
