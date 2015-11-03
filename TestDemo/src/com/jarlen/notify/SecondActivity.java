package com.jarlen.notify;

import com.jarlen.test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

	private TextView second_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_second);
		second_tv = (TextView) findViewById(R.id.second_tv);

		Intent intent = getIntent();
		String label = intent.getStringExtra("Notify");
		second_tv.setText(label);
	}
}
