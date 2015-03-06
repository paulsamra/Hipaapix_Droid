package swipe.android.hipaapix.services;

import com.paypal.android.sdk.payments.LoginActivity;

import swipe.android.hipaapix.HipaapixApplication;
import swipe.android.hipaapix.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class LogoutService extends Service {
	public static CountDownTimer timer;
	public static final String TAG = LogoutService.class.getSimpleName();
private static final int TIME_TO_LOGOUT = 1000 * 60 * 20;
	@Override
	public void onCreate() {
		super.onCreate();
		timer = new CountDownTimer(/* 1 * 60 * 1000 */TIME_TO_LOGOUT, 1000) {
			public void onTick(long millisUntilFinished) {
				// Some code
			}

			public void onFinish() {
				Activity act = ((HipaapixApplication) LogoutService.this
						.getApplication()).getCurrentActivity();
				if (act != null
						&& !act.getClass().getSimpleName()
								.equals(LoginActivity.class.getSimpleName())) {
					stopSelf();

					Log.v(TAG, "Call Logout by Service");
					// Code for Logout

					((HipaapixApplication) LogoutService.this.getApplication())
							.logout(true);
				}
			}
		};
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean isAppSentToBackground(final Context context) {
		try {
			ActivityManager am = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			// The first in the list of RunningTasks is always the foreground
			// task.
			RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
			String foregroundTaskPackageName = foregroundTaskInfo.topActivity
					.getPackageName();// get the top fore ground activity
			PackageManager pm = context.getPackageManager();
			PackageInfo foregroundAppPackageInfo = pm.getPackageInfo(
					foregroundTaskPackageName, 0);
			String foregroundTaskAppName = foregroundAppPackageInfo.applicationInfo
					.loadLabel(pm).toString();
			// Log.v(Constants.TAG,
			// " foregroundTaskAppName "+foregroundTaskPackageName);
			// if (!foregroundTaskAppName.equals("University of Dammam"))
			// {//context.getResources().getString(R.string.app_name)
			if (!foregroundTaskAppName.equals(context.getResources().getString(
					R.string.app_name))) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG, "" + e);
		}
		return false;
	}
}