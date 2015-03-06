package swipe.android.hipaapix;

import java.io.IOException;
import java.math.BigDecimal;

import org.xmlpull.v1.XmlPullParserException;

import swipe.android.DatabaseHelpers.EventsDatabaseHelper;
import swipe.android.DatabaseHelpers.GroupsDatabaseHelper;
import swipe.android.DatabaseHelpers.MessagesDatabaseHelper;
import swipe.android.DatabaseHelpers.NeedsCommentsDatabaseHelper;
import swipe.android.DatabaseHelpers.NeedsDetailsDatabaseHelper;
import swipe.android.hipaapix.services.LogoutService;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.edbert.library.database.DatabaseCommandManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;

public class HipaapixApplication extends Application implements

Application.ActivityLifecycleCallbacks {

	public static final boolean DEVELOPER_MODE = false;

	// paypal stuff
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

	// note that these credentials will differ between live & sandbox
	// environments.
	private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";

	public static final int REQUEST_CODE_PAYMENT = 1;
	public static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
	public static final int REQUEST_CODE_PROFILE_SHARING = 3;



	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void onCreate() {
		super.onCreate();

		initImageLoader(getApplicationContext());
	
		 startService(new Intent(this, LogoutService.class));
		/*
		 * SendRequestStrategyManager.register(new MessagesRequest(this));
		 * SendRequestStrategyManager.register(new NeedsDetailsRequest(this,
		 * JsonExploreResponse.class)); SendRequestStrategyManager.register(new
		 * EventsDetailsRequest(this)); SendRequestStrategyManager.register(new
		 * GroupsRequest(this));
		 */
		super.registerActivityLifecycleCallbacks(this);

		mInstance = this;

	}


	@Override
	public void onTerminate() {
		super.unregisterActivityLifecycleCallbacks(this);
		super.onTerminate();
	}

	

	public void initImageLoader(Context context) {
		/*
		 * DisplayImageOptions defaultOptions = new
		 * DisplayImageOptions.Builder() .cacheInMemory(true)
		 * .cacheOnDisk(false) .build();
		 */
		DisplayImageOptions defaultOptions = getDefaultOptions();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				.diskCacheExtraOptions(480, 800, null)

				.threadPoolSize(3)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 1)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				// .memoryCacheSize(41943040)
				.memoryCache(new LruMemoryCache(20 * 1024 * 1024))

				.memoryCacheSizePercentage(13)
				// default

				.defaultDisplayImageOptions(defaultOptions)
				.imageDownloader(new SmartImageDownloader(context)) // default
				// default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);

	}

	public DisplayImageOptions getDefaultOptions() {

		Resources res = getResources();
		/*
		 * return new DisplayImageOptions.Builder().cacheInMemory(true)
		 * 
		 * .cacheOnDisk(false).considerExifParams(true)
		 * .showImageOnLoading(R.color.white) //displayer(BitmapDisplayer
		 * displayer) .showImageOnLoading(Drawable.createFromXml(res,
		 * res.getXml(R.drawable.spinner)))
		 * .bitmapConfig(Bitmap.Config.RGB_565);
		 */

		DisplayImageOptions.Builder b = /*
										 * new DisplayImageOptions.Builder().
										 * cacheInMemory(true)
										 * .resetViewBeforeLoading(true)
										 * .cacheOnDisk
										 * (false).considerExifParams(true)
										 * .showImageOnLoading(R.color.white)
										 * .considerExifParams(true)
										 * .imageScaleType
										 * (ImageScaleType.EXACTLY)
										 * //displayer(BitmapDisplayer
										 * displayer)
										 * .bitmapConfig(Bitmap.Config.RGB_565);
										 */
		new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(false)
				.showImageOnLoading(android.R.color.transparent)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565);

		try {
			b.showImageOnLoading(Drawable.createFromXml(res,
					res.getXml(R.drawable.spinner)));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b.build();
	}

	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityStarted(Activity activity) {
		// TODO Auto-generated method stub

	}

	public static void displayNetworkNotAvailableDialog(Context ctx) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		String networkMessage = ctx
				.getString(R.string.not_connected_to_internet_msg);
		String networkTitle = ctx
				.getString(R.string.not_connected_to_internet_title);

		builder.setMessage(networkMessage).setTitle(networkTitle)
				.setCancelable(true)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public void onActivityResumed(Activity activity) {
		act = activity;
		if(autologout){

			///Wait for current login activity
			String loginTitle = "Logout";
			String message = "Due to inactivity, you have been logged out";
			AlertDialog.Builder builder = new AlertDialog.Builder(act);
			builder.setMessage(message)
					.setTitle(loginTitle)
					.setCancelable(false)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.dismiss();
								}
							});

			AlertDialog alert = builder.create();
			alert.show();
			autologout = false;
		}
	}

	Activity act;

	@Override
	public void onActivityPaused(Activity activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityStopped(Activity activity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityDestroyed(Activity activity) {
		// TODO Auto-generated method stub

	}

	public static final String TAG = HipaapixApplication.class.getSimpleName();

	private static HipaapixApplication mInstance;
	private RequestQueue mRequestQueue;

	public static synchronized HipaapixApplication getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
boolean autologout = false;
	public void logout(boolean autologout) {
		SessionManager sm = SessionManager.getInstance(this);
		sm.clearUserPref();
		if (act != null) {
			Intent i = new Intent(act, LoginActivity.class);
			act.startActivity(i);
			act.finish();
this.autologout = autologout;
		}
	}
	public Activity getCurrentActivity(){
		return act;
	}

}
