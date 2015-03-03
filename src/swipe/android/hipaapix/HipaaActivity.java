package swipe.android.hipaapix;

import swipe.android.hipaapix.json.TrueVaultResponse;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public abstract class HipaaActivity extends Activity{
	
	public void displayError(TrueVaultResponse response){

		String loginTitle = "Error";

		String try_again = this.getString(R.string.try_again);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(response.getError().getMessage())
				.setTitle(loginTitle)
				.setCancelable(false)
				.setPositiveButton(try_again,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								dialog.dismiss();
							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void displayNetworkError(){
		HipaapixApplication.getInstance().displayNetworkNotAvailableDialog(this);
	}
}