package swipe.android.hipaapix;

import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.edbert.library.greyButton.GreyedOutButton;
import com.edbert.library.network.AsyncTaskCompleteListener;

public class LoginActivity extends Activity  {

	GreyedOutButton login;
	Button signUp;
	//EditText usernameET, passwordET;
	LinearLayout loginForm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_form);
		if (SessionManager.getInstance(this).isLoggedIn()) {
			goToNextActivity();
		}
		getActionBar().hide();
		//usernameET = (EditText) findViewById(R.id.login_username_input);
		//passwordET = (EditText) findViewById(R.id.login_password_input);
		loginForm = (LinearLayout) findViewById(R.id.login_insert);
		loginForm.setVisibility(View.VISIBLE);
		signUp = (Button) findViewById(R.id.login_sign_up_button);

		SpannableString content = new SpannableString(signUp.getText());
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		signUp.setText(content);
		login = (GreyedOutButton) findViewById(R.id.parse_login_button);
		login.setEnabled(true);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				doLogin();
			}

		});
		

	/*	usernameET.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				enableDisableLoginButton();

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				enableDisableLoginButton();

			}

			@Override
			public void afterTextChanged(Editable s) {
				enableDisableLoginButton();

			}

		});
		passwordET.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				enableDisableLoginButton();

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				enableDisableLoginButton();

			}

			@Override
			public void afterTextChanged(Editable s) {
				enableDisableLoginButton();

			}

		});*/
	}

	public void doSignUp() {

	}

	public void onTaskComplete(Object result) {
	/*	if (result == null) {
			HipaapixApplication.displayNetworkNotAvailableDialog(this);
		} else if (!result.isValid()) {
			String loginTitle = this.getString(R.string.login_error_title);

			String try_again = this.getString(R.string.try_again);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(result.getError())
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
		} else {
			SessionManager.getInstance(this).setIsLoggedIn(true);
			Log.d("USER ID", String.valueOf(result.getUserID()));
			SessionManager.getInstance(this).setUserID(
					String.valueOf(result.getUserID()));
			SessionManager.getInstance(this).setAuthToken(result.getToken());

			goToNextActivity();
		}*/
	}

	public void goToNextActivity() {
		Intent intent = new Intent(this, DisclaimerActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	public void doLogin() {
		if (!((HipaapixApplication) this.getApplication()).isNetworkAvailable()) {
			((HipaapixApplication) this.getApplication())
					.displayNetworkNotAvailableDialog(this);
			return;
		}
		Map<String, String> headers = SessionManager.getInstance(this)
				.defaultSessionHeaders();
		String username = "";
		String password = "";
		if (HipaapixApplication.DEVELOPER_MODE) {
			username = "ramsin";
			password = "ramsin";
		} else {
	//		username = usernameET.getText().toString();
	//		password = passwordET.getText().toString();
		}
		headers.put("username", username);
		headers.put("password", password);
		goToNextActivity();
		/*
		 * new PostDataWebTask<JsonLoginResponse>(this, JsonLoginResponse.class)
		 * .execute(SessionManager.getInstance(this).loginURL(),
		 * MapUtils.mapToString(headers));
		 */
	}
	

	private void enableDisableLoginButton() {

	/*	if (usernameET.getText().toString().length() > 0
				&& passwordET.getText().toString().length() > 0) {
			//
			login.setEnabled(true);
		} else {
			login.setEnabled(false);

		}*/
	}

}
