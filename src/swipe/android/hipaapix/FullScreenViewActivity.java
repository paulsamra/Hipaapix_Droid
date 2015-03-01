package swipe.android.hipaapix;


import java.util.ArrayList;

import swipe.android.hipaapix.classes.patients.Patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class FullScreenViewActivity extends HipaaActivity{

	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);
		this.getActionBar().hide();
		viewPager = (ViewPager) findViewById(R.id.pager);
		ArrayList<Patient> ar1=getIntent().getExtras().getParcelableArrayList("list");  

Intent i = getIntent();
		int position = i.getIntExtra("position", 0);

		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
				ar1);

		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(position);
	}
}
