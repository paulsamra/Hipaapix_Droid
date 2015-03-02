package swipe.android.hipaapix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import swipe.android.hipaapix.classes.patients.Patient;
import swipe.android.hipaapix.core.BaseFragment;
import swipe.android.hipaapix.core.GridOfSearchResultsWithTakePictureActivity;
import swipe.android.hipaapix.core.HipaaPixTabsActivityContainer;
import swipe.android.hipaapix.json.patientsResponse.PatientsResultsResponse;
import swipe.android.hipaapix.json.searchvault.EncodedDocument;
import swipe.android.hipaapix.json.searchvault.SearchVaultResponse;
import swipe.android.hipaapix.json.users.GetUserDataResponse;
import swipe.android.hipaapix.viewAdapters.SearchResultsAdapter;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.edbert.library.network.AsyncTaskCompleteListener;
import com.edbert.library.network.GetDataWebTask;
import com.edbert.library.utils.MapUtils;

public class PatientSearchListFragment extends BaseFragment implements
		AsyncTaskCompleteListener<SearchVaultResponse>,
		AdapterView.OnItemClickListener {

	private SearchResultsAdapter resultsListAdapter;

	@Override
	public void onResume() {
		super.onResume();

	}

	String options = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_layout, container, false);
		ListView lView = (ListView) view.findViewById(android.R.id.list);
		this.resultsListAdapter = new SearchResultsAdapter(this.getActivity(),
				R.id.name, new ArrayList<EncodedDocument>());
		lView.setAdapter(resultsListAdapter);
		lView.setOnItemClickListener(this);
		getActivity().getActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		ActionBar ab = this.getActivity().getActionBar();
		ab.setDisplayShowTitleEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		ab.setTitle("Search Result");

		Bundle extras = this.getArguments();
		options = extras.getString("options");

		// we want to launc this
		setHasOptionsMenu(true);


		String url =APIManager
				.searchVaultURL(
						this.getActivity(),options);

		Map<String, String> headers = APIManager.defaultSessionHeaders();

		
	
		new GetDataWebTask<SearchVaultResponse>(this.getActivity(),
				(AsyncTaskCompleteListener<SearchVaultResponse>) this,
				SearchVaultResponse.class).execute(url,
				MapUtils.mapToString(headers));

		return view;
	}

	@Override
	public void onTaskComplete(SearchVaultResponse result) {
		if (!result.isValid()) {

		} else {
			SearchResultsAdapter adapter = (SearchResultsAdapter) this.resultsListAdapter;
			adapter.clear();
			for (EncodedDocument d : result.getData().getDocuments()) {
				adapter.add(d);
			}
			adapter.notifyDataSetChanged();
		
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		this.getActivity().onBackPressed();
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Bundle b = new Bundle();
		EncodedDocument patient = resultsListAdapter.getItem(position);

		String realPatient = APIManager.decode64(patient.getDocument());
	
		b.putString(PatientGridOfImagesFragment.BUNDLE_TITLE_ID, realPatient);
		mActivity.pushFragments(HipaaPixTabsActivityContainer.TAB_A,
				new PatientGridOfImagesFragment(), true, true, b);

	}
}