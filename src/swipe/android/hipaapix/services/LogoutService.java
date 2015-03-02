package swipe.android.hipaapix.services;

import swipe.android.hipaapix.Constants;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class LogoutService extends Service {
	public static CountDownTimer timer;
	private boolean flag = true;

	@Override
	public void onCreate() {
	
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}