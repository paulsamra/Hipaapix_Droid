package swipe.android.hipaapix;

import swipe.android.hipaapix.core.GridOfSearchResultsNoTakePictureFragment;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class PatientGridOfImagesFragment extends
		GridOfSearchResultsNoTakePictureFragment {
	public static final String BUNDLE_TITLE_ID = "title";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);

		getActivity().getActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		ActionBar ab = this.getActivity().getActionBar();
		ab.setDisplayShowTitleEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		Bundle extras = this.getArguments();
		String s = extras.getString(BUNDLE_TITLE_ID);
		ab.setTitle(s);
		setHasOptionsMenu(true);
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.add_picture_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		case R.id.add_picture_item:
			launchCamera();
			return true;
		default:
			this.getActivity().onBackPressed();
			return super.onOptionsItemSelected(item);
		}
	}
	public void launchCamera(){
		Intent i = new Intent(this.getActivity(), HipaaPixCamera.class);
		this.getActivity().startActivity(i);
	}

}