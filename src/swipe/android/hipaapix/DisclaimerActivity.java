package swipe.android.hipaapix;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisclaimerActivity extends CustomActionBarActivity {

	Button agree;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.disclaimer_layout);

		agree = (Button) findViewById(R.id.disclaimer_agree_button);

		agree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				goToNextActivity();
			}

		});
	}

	public void goToNextActivity() {
		Intent intent = new Intent(this, HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	@Override
	protected View createActionBarView() {
		LayoutInflater mInflater = LayoutInflater.from(this);
		View mCustomView = mInflater
				.inflate(R.layout.actionbar_logo_only, null);
	//	getActionBar().setDisplayUseLogoEnabled(false);
	
		return mCustomView;
	}
}
