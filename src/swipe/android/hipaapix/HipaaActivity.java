package swipe.android.hipaapix;

import swipe.android.hipaapix.json.TrueVaultResponse;
import swipe.android.hipaapix.services.LogoutService;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public abstract class HipaaActivity extends ActionBarActivity {

	public void displayError(TrueVaultResponse response) {

		
		if(pd != null && pd.isShowing()){
			pd.cancel();
		}
		String loginTitle = "Error";

		String try_again = this.getString(R.string.try_again);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(response.getError().getMessage())
				.setTitle(loginTitle)
				.setCancelable(false)
				.setPositiveButton(try_again,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void displayNetworkError() {
		HipaapixApplication.getInstance()
				.displayNetworkNotAvailableDialog(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// stopService(new Intent(getBaseContext(), LogoutService.class));
		if (LogoutService.timer != null)
			LogoutService.timer.start();
		else
			Log.d("SERVICE NOT AVAIL", "SERVICE NOT AVAIL");

	}

	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		LogoutService.timer.cancel();
		LogoutService.timer.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		pd = new ProgressDialog(this);
	
	}

	protected ProgressDialog pd;

	@Override
	protected void onDestroy() {
		if (pd != null) {
			pd.dismiss();
		}
		super.onDestroy();
	}
}