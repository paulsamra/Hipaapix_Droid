package swipe.android.hipaapix;

import swipe.android.hipaapix.R;
import android.app.ActionBar.LayoutParams;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.throrinstudio.android.library.widgets.dashboard.DashBoardElement.OnClickListener;
import com.throrinstudio.android.library.widgets.dashboard.DashBoardLayout;

public abstract class CustomActionBarActivity extends ActionBarActivity {

	private void setupActionBar() {
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);

		// LayoutInflater mInflater = LayoutInflater.from(this);
		View mCustomView = createActionBarView();

		LayoutParams lp = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);

		getActionBar().setCustomView(mCustomView, lp);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		//getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
		getActionBar().setIcon(
				   new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
		
		
		
	}

	protected abstract View createActionBarView();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setupActionBar();
	}

}