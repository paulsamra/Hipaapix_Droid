package swipe.android.hipaapix;

import swipe.android.hipaapix.R;
import swipe.android.hipaapix.core.FragmentInfo;
import swipe.android.hipaapix.core.HipaaPixTabsActivityContainer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.edbert.library.containers.TabsActivityContainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class HomeActivity extends HipaaPixTabsActivityContainer {

	String id = "0";

	Menu menu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

	}

	@Override
	protected void addDefaultFragments() {
		FragmentInfo homeInfo = new FragmentInfo(new SearchFragment(),
				null);
		mapFragList.put("Home", homeInfo);
		FragmentInfo settingsInfo = new FragmentInfo(new SettingsFragment(),
		null);

		mapFragList.put("Settings", settingsInfo);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected View createActionBarView() {
		LayoutInflater mInflater = LayoutInflater.from(this);
		View mCustomView = mInflater
				.inflate(R.layout.actionbar_logo_only, null);
		getActionBar().setDisplayUseLogoEnabled(false);

		return mCustomView;
	}

}
