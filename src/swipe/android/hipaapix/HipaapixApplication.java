package swipe.android.hipaapix;

import java.math.BigDecimal;

import swipe.android.DatabaseHelpers.EventsDatabaseHelper;
import swipe.android.DatabaseHelpers.GroupsDatabaseHelper;
import swipe.android.DatabaseHelpers.MessagesDatabaseHelper;
import swipe.android.DatabaseHelpers.NeedsCommentsDatabaseHelper;
import swipe.android.DatabaseHelpers.NeedsDetailsDatabaseHelper;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.edbert.library.database.DatabaseCommandManager;
import com.edbert.library.sendRequest.SendRequestStrategyManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;

public class HipaapixApplication extends Application implements
		
		Application.ActivityLifecycleCallbacks
		{

	public static final boolean DEVELOPER_MODE = true;

	//paypal stuff
	 private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

	    // note that these credentials will differ between live & sandbox environments.
	    private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";

	    public static final int REQUEST_CODE_PAYMENT = 1;
	    public static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
	    public static final int REQUEST_CODE_PROFILE_SHARING = 3;

	    public static PayPalConfiguration config = new PayPalConfiguration()
	            .environment(CONFIG_ENVIRONMENT)
	            .clientId(CONFIG_CLIENT_ID)
	            // The following are only used in PayPalFuturePaymentActivity.
	            .merchantName("hipaapix")
	            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
	            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));




	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void onCreate() {
		super.onCreate();

		initImageLoader(getApplicationContext());
		registerDatabaseTables();
		DatabaseCommandManager.createAllTables(HipaapixContentProvider
				.getDBHelperInstance(this).getWritableDatabase());

	/*	SendRequestStrategyManager.register(new MessagesRequest(this));
		SendRequestStrategyManager.register(new NeedsDetailsRequest(this,
				JsonExploreResponse.class));
		SendRequestStrategyManager.register(new EventsDetailsRequest(this));
		SendRequestStrategyManager.register(new GroupsRequest(this));*/
		super.registerActivityLifecycleCallbacks(this);
		
		   Intent intent = new Intent(this, PayPalService.class);
	        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
	        startService(intent);
	}
	  public static PayPalPayment generatePayObject(double price, String item, String paymentIntent) {
	        return new PayPalPayment(new BigDecimal(price), "USD", item,
	                paymentIntent);
	    }
	@Override
	public void onTerminate() {
		super.unregisterActivityLifecycleCallbacks(this);
		super.onTerminate();
	}


	private void registerDatabaseTables() {
		DatabaseCommandManager.register(new MessagesDatabaseHelper());
		DatabaseCommandManager.register(new NeedsDetailsDatabaseHelper());
		DatabaseCommandManager.register(new NeedsCommentsDatabaseHelper());
		DatabaseCommandManager.register(new EventsDatabaseHelper());
		DatabaseCommandManager.register(new GroupsDatabaseHelper());
	}

	
	public static void initImageLoader(Context context) {
		/*
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisk(false)
        .build();*/
		DisplayImageOptions defaultOptions = getDefaultOptions();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(1)
				.defaultDisplayImageOptions(defaultOptions)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}

	public static DisplayImageOptions getDefaultOptions() {
		return new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(false).considerExifParams(true)
				.showImageOnLoading(R.color.white)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}


	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityStarted(Activity activity) {
		// TODO Auto-generated method stub

	}


	public static void displayNetworkNotAvailableDialog(Context ctx){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		String networkMessage = ctx.getString(R.string.not_connected_to_internet_msg);
		String networkTitle = ctx.getString(R.string.not_connected_to_internet_title);
		
		builder.setMessage(networkMessage)
				.setTitle(networkTitle)
				.setCancelable(true)
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	@Override
	public void onActivityResumed(Activity activity) {
		// TODO Auto-generated method stub
		
	}
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


}
