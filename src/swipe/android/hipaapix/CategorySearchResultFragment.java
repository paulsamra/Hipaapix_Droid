package swipe.android.hipaapix;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import swipe.android.hipaapix.classes.patients.Patient;
import swipe.android.hipaapix.core.GridOfSearchResultsNoTakePictureFragment;
import swipe.android.hipaapix.json.searchvault.SearchVaultResponse;
import swipe.android.hipaapix.json.searchvaultImage.SearchVaultImageResponse;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.edbert.library.network.AsyncTaskCompleteListener;
import com.edbert.library.network.GetDataWebTask;
import com.edbert.library.utils.MapUtils;

public class CategorySearchResultFragment extends
		GridOfSearchResultsNoTakePictureFragment implements
		AsyncTaskCompleteListener<SearchVaultImageResponse> {
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

		try {

			String options =
			APIManager.categorySearchOptions(this.getActivity(), s);// searchOptions(s);

			Log.d("Cateogry", APIManager.decode64(options));
			String url = APIManager.searchVaultURL(this.getActivity(), options);

			Map<String, String> headers = APIManager.defaultSessionHeaders();

		
			new GetDataWebTask<SearchVaultImageResponse>(this.getActivity(),
					(AsyncTaskCompleteListener<SearchVaultImageResponse>) this,
					SearchVaultImageResponse.class).execute(url,
					MapUtils.mapToString(headers));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		i.putParcelableArrayListExtra("list", new ArrayList<Patient>(
				super.imageUrls));
		i.putExtra("position", position);
		startActivity(i);
	}

	@Override
	public void onTaskComplete(SearchVaultImageResponse result) {

	}
}