package swipe.android.hipaapix;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;

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

		try {
			initialize(extras.getString(BUNDLE_TITLE_ID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setHasOptionsMenu(true);
		return v;
	}

	private void initialize(String s) throws Exception {
		JSONObject patient = new JSONObject(s);
		String firstName = patient.getString("firstName");
		String lastName = patient.getString("lastName");
		JSONArray images = patient.getJSONArray("images");
		Log.d("images", images.toString());
		for (int i = 0; i < images.length(); i++){
			imageUrls.add(images.getString(i));
		}

		String title = firstName + " " + lastName;
		ActionBar ab = this.getActivity().getActionBar();
		ab.setTitle(title);
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

	public void launchCamera() {
		Intent i = new Intent(this.getActivity(), HipaaPixCamera.class);
		this.getActivity().startActivity(i);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(this.getActivity(), FullScreenViewActivity.class);
		i.putStringArrayListExtra("list",
				new ArrayList<String>(super.imageUrls));
		i.putExtra("position", position);
		startActivity(i);
	}

}