package com.sms_intercept;

import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText reciver;
	EditText content;
	Button send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
	}

	public void init() {
		reciver = (EditText) findViewById(R.id.reciver);
		content = (EditText) findViewById(R.id.content);
		send = (Button) findViewById(R.id.send);

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String moblie = reciver.getText().toString().trim();
				String MsgContent = content.getText().toString();

				SmsManager msmMessage = SmsManager.getDefault();
				PendingIntent pendingIntent = PendingIntent.getBroadcast(
						MainActivity.this, 0, new Intent(), 0);

				if (MsgContent.length() >= 70) {
					List<String> ms = msmMessage.divideMessage(MsgContent);
					for (String message : ms) {
						msmMessage.sendTextMessage(moblie, null, MsgContent,
								pendingIntent, null);
					}
				} else {
					msmMessage.sendTextMessage(moblie, null, MsgContent,
							pendingIntent, null);
				}
				Log.i("发送成功", "!!!!!!!!!!!!!");
			}
		});
	}
}
