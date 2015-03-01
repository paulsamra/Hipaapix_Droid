package swipe.android.hipaapix;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import swipe.android.hipaapix.json.login.LoginResponse;
import swipe.android.hipaapix.json.users.GetUserDataResponse;
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
import com.edbert.library.network.GetDataWebTask;
import com.edbert.library.network.PostDataWebTask;
import com.edbert.library.utils.MapUtils;

public class LoginActivity extends HipaaActivity implements
		AsyncTaskCompleteListener {

	GreyedOutButton login;
	Button signUp;
	EditText usernameET, passwordET;
	LinearLayout loginForm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_form);
		if (SessionManager.getInstance(this).isLoggedIn()) {
			goToNextActivity();
		}
		getActionBar().hide();
		usernameET = (EditText) findViewById(R.id.login_username_input);
		passwordET = (EditText) findViewById(R.id.login_password_input);
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

		usernameET.addTextChangedListener(new TextWatcher() {

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

		});

	}

	public void doLoginResponse(LoginResponse loginResponse) {
		if (!loginResponse.isValid()) {
			displayError(loginResponse);
		} else {
			SessionManager.getInstance(this).setIsLoggedIn(true);
			SessionManager.getInstance(this).setAccessToken(
					String.valueOf(loginResponse.getUser().getAccess_token()));
			SessionManager.getInstance(this).setUserName(
					String.valueOf(loginResponse.getUser().getUsername()));
			SessionManager.getInstance(this).setUserID(
					String.valueOf(loginResponse.getUser().getUser_id()));

			String url = APIManager.getUserURL(this);

			Map<String, String> headers = APIManager.defaultSessionHeaders();

			new GetDataWebTask<GetUserDataResponse>(this,
					GetUserDataResponse.class).execute(url,
					MapUtils.mapToString(headers));
		}
	}

	@Override
	public void onTaskComplete(Object response) {
		if (response == null) {
			HipaapixApplication.displayNetworkNotAvailableDialog(this);
		} else if (response instanceof LoginResponse) {
			doLoginResponse((LoginResponse) response);
		} else {
			doFinalLoginFromUserData((GetUserDataResponse) response);
		}
	}

	public void doFinalLoginFromUserData(GetUserDataResponse response) {
		if (!response.isValid()) {
			displayError(response);
		} else {
			String decoded = APIManager.decode64(
					response.getUser().getAttributes());
			JSONObject decodedJSON;
			try {
				decodedJSON = new JSONObject(decoded);

				String patient_schema = decodedJSON.getString("patient_schema");
				String patient_image_schema = decodedJSON
						.getString("patient_image_schema");
				String vault_id = decodedJSON.getString("vault_id");
				SessionManager.getInstance(this).setSchemaID(patient_schema);
				SessionManager.getInstance(this).setVaultID(vault_id);

				this.goToNextActivity();
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
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

		Map<String, String> formHeaders = new LinkedHashMap<String, String>();
		formHeaders.put("account_id", APIManager.accountID);
		if (HipaapixApplication.DEVELOPER_MODE) {
			formHeaders.put("username", "testuser1");
			formHeaders.put("password", "12345678");

		} else {
			formHeaders.put("username", usernameET.getText().toString());
			formHeaders.put("password", passwordET.getText().toString());
		}
		new PostDataWebTask<LoginResponse>(this, this, LoginResponse.class,
				true, true).execute(APIManager
				.getLoginURL(), "", MapUtils.mapToString(formHeaders));

	}

	private void enableDisableLoginButton() {

		if (usernameET.getText().toString().length() > 0
				&& passwordET.getText().toString().length() > 0) { //
			login.setEnabled(true);
		} else {
			login.setEnabled(false);

		}

	}

}
