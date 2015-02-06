/*package swipe.android.hipaapix.viewAdapters;

import swipe.android.hipaapix.R;
import swipe.android.hipaapix.SessionManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// reviewed
public class SettingsViewAdapter {
	Context ctx;

	public SettingsViewAdapter(FragmentActivity ctx, View rootView,
			Bundle savedInstanceState) {
		this.rootView = rootView;
		this.ctx = ctx;
		logout = (Button) rootView.findViewById(R.id.logout_btn);

		initializeViews(savedInstanceState);
	}

	View rootView;
	Button logout;

	private void initializeViews(Bundle savedInstanceState) {
		TextView email = (TextView) rootView.findViewById(R.id.email_field);
		TextView password = (TextView) rootView
				.findViewById(R.id.password_field);
		TextView category = (TextView) rootView
				.findViewById(R.id.category_field);
		email.setText(SessionManager.getInstance(ctx).getEmail());

		SessionManager.getInstance(ctx).getPassword();
		SessionManager.getInstance(ctx).getCategory();
	}

}*/