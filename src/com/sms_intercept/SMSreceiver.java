package com.sms_intercept;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSreceiver extends BroadcastReceiver {

	public static final String mACTION = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(mACTION)) {
			StringBuffer str = new StringBuffer();

			Bundle bundle = intent.getExtras();

			if (bundle != null) {
				/* pdus为 android内置短信参数 identifier, 通过bundle.get("")返回一包含pdus的对象 */
				Object[] obt = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[obt.length];
				for (int i = 0; i < obt.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) obt[i]);
				}

				for (SmsMessage currentMessage : messages) {
					str.append("发送人：\n");
					str.append(currentMessage.getDisplayOriginatingAddress());
					str.append("内容：\n");
					str.append(currentMessage.getDisplayMessageBody());
				}

				/* 以Notification(Toase)显示来讯信息 */
				Toast.makeText(context, str.toString(), Toast.LENGTH_LONG)
						.show();
				Log.i("sb===========", str.toString());
				// 终止广播,manifest.xml的priority优先权很高，终止广播传给其他应用
				abortBroadcast();
			}

		}

	}

}
