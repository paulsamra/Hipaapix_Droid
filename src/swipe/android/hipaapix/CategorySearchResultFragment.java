package swipe.android.hipaapix;

import java.util.ArrayList;

import swipe.android.hipaapix.core.GridOfSearchResultsNoTakePictureFragment;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public class CategorySearchResultFragment extends
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
	public boolean onOptionsItemSelected(MenuItem item) {
	this.getActivity().onBackPressed();
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(this.getActivity(), FullScreenViewActivity.class);
		i.putStringArrayListExtra("list", new ArrayList<String>(super.imageUrls));
        i.putExtra("position", position);
		startActivity(i);
	}
}