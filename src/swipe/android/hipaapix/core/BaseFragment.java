package swipe.android.hipaapix.core;

import swipe.android.hipaapix.HomeActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseFragment extends Fragment {
	public HomeActivity mActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mActivity = (HomeActivity) this.getActivity();
	}

	public boolean onBackPressed() {
		return false;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}


}