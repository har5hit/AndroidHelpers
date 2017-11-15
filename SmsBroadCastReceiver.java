package helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;

public class SmsBroadCastReceiver extends BroadcastReceiver{
    private static final String KEY_MESSAGE_FROM ;
	
	public SmsBroadCastReceiver(String KEY_MESSAGE_FROM){
		this.KEY_MESSAGE_FROM=KEY_MESSAGE_FROM;
	}

    private SMSReceiveListener smsReceiveListener;

    public void setSmsReceiveListener(SMSReceiveListener listener)
    {
        this.smsReceiveListener=listener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Utils.log("OTP received");
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        Utils.log("otp from"+msg_from);
                        if (msg_from.contains(KEY_MESSAGE_FROM)) {
                            String[] msgBody = msgs[i].getMessageBody().split(" ");
                            for (String snippet : msgBody) {
                                Utils.log("snippet is"+snippet);
                                if (snippet.length()==6 && TextUtils.isDigitsOnly(snippet))
                                {
                                    smsReceiveListener.onSMSReceived(snippet);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public interface SMSReceiveListener
    {
        void onSMSReceived(String otp);
    }
}