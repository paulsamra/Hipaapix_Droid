package swipe.android.hipaapix;

import java.util.ArrayList;
import java.util.List;

import swipe.android.hipaapix.classes.patients.Patient;
import swipe.android.hipaapix.core.BaseFragment;
import swipe.android.hipaapix.core.GridOfSearchResultsWithTakePictureActivity;
import swipe.android.hipaapix.core.HipaaPixTabsActivityContainer;
import swipe.android.hipaapix.json.patientsResponse.PatientsResultsResponse;
import swipe.android.hipaapix.viewAdapters.SearchResultsAdapter;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.edbert.library.network.AsyncTaskCompleteListener;

public class PatientSearchListFragment extends BaseFragment implements
		AsyncTaskCompleteListener<PatientsResultsResponse>, AdapterView.OnItemClickListener {

	private SearchResultsAdapter resultsListAdapter;

	@Override
	public void onResume() {
		super.onResume();
		new DummyWebTask<Object>(this.getActivity(), this, Object.class)
				.execute("", "", "");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_layout, container, false);
		ListView lView = (ListView) view.findViewById(android.R.id.list);
		this.resultsListAdapter = new SearchResultsAdapter(this.getActivity(),
				R.id.name, new ArrayList<Patient>());
		lView.setAdapter(resultsListAdapter);
		lView.setOnItemClickListener(this);
		getActivity().getActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		ActionBar ab = this.getActivity().getActionBar();
		ab.setDisplayShowTitleEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		ab.setTitle("Search Result");
		setHasOptionsMenu(true);
		return view;
	}




	@Override
	public void onTaskComplete(PatientsResultsResponse result) {
		SearchResultsAdapter adapter = (SearchResultsAdapter) this.resultsListAdapter;
		adapter.clear();
		result = new PatientsResultsResponse();
		for (Patient patient : result.getPatients()) {
			adapter.add(patient);
		}
		adapter.notifyDataSetChanged();
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
		String value = resultsListAdapter.getItem(position).getName();
		b.putString(PatientGridOfImagesFragment.BUNDLE_TITLE_ID, value);
		mActivity.pushFragments(HipaaPixTabsActivityContainer.TAB_A, new
				PatientGridOfImagesFragment(),true,true,b);
		
	}
}