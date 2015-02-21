package swipe.android.hipaapix.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FragmentInfo {
	Fragment fragment;
	Bundle b;

	public Bundle getB() {
		return b;
	}

	public void setB(Bundle b) {
		this.b = b;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}

	public FragmentInfo(Fragment f, Bundle b) {

		this.fragment = f;
		this.b = b;
	}

}