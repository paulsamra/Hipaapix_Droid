package swipe.android.hipaapix;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import swipe.android.hipaapix.classes.patients.Patient;
import swipe.android.hipaapix.core.GridOfSearchResultsNoTakePictureFragment;
import swipe.android.hipaapix.json.searchvault.Document;
import swipe.android.hipaapix.json.searchvault.EncodedDocument;
import swipe.android.hipaapix.json.searchvault.SearchVaultResponse;
import swipe.android.hipaapix.json.searchvaultImage.SearchVaultImageResponse;
import swipe.android.hipaapix.viewAdapters.SearchResultsAdapter;
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
import com.google.gson.Gson;

public class CategorySearchResultFragment extends
		GridOfSearchResultsNoTakePictureFragment implements
		AsyncTaskCompleteListener<SearchVaultResponse> {
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

			String options = APIManager.categorySearchOptions(
					this.getActivity(), s);// searchOptions(s);

			// Log.d("Cateogry", APIManager.decode64(options));
			String url = APIManager.searchVaultURL(this.getActivity(), options);

			Map<String, String> headers = APIManager.defaultSessionHeaders();

			new GetDataWebTask<SearchVaultResponse>(this.getActivity(),
					(AsyncTaskCompleteListener<SearchVaultResponse>) this,
					SearchVaultResponse.class).execute(url,
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

	public void setUpDocmentsDecodeQuery(SearchVaultResponse result) {

		for (EncodedDocument doc : result.getData().getDocuments()) {
			String decode = APIManager.decode64(doc.getDocument());
			Gson gson3 = new Gson();
			Document parsedResponse = gson3.fromJson(decode, Document.class);

			String url = APIManager.downloadRawBlobUrl(this.getActivity(),
					parsedResponse.getBlob_id());

			super.imageUrls.add(new Patient("", "", "", parsedResponse
					.getCategory(), url, parsedResponse.getPatient_id()));
		}
		adapter.notifyDataSetChanged();
		adapter.notifyDataSetInvalidated();
	}

	public void loadImagesIntoAdapter() {

	}

	@Override
	public void onTaskComplete(SearchVaultResponse result) {
		if (!result.isValid()) {

		} else {
			setUpDocmentsDecodeQuery(result);
		}
	}
}