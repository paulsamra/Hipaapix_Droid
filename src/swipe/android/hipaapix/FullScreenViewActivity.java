package swipe.android.hipaapix;

import java.util.ArrayList;

import swipe.android.hipaapix.classes.patients.Patient;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FullScreenViewActivity extends HipaaActivity {

	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;
LinearLayout rlMain;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);
		this.getActionBar().hide();
		viewPager = (ViewPager) findViewById(R.id.pager);
		ArrayList<Patient> ar1 = getIntent().getExtras()
				.getParcelableArrayList("list");

		Intent i = getIntent();
		int position = i.getIntExtra("position", 0);

		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, ar1);

		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(position);
	}
}
