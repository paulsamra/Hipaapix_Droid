package swipe.android.hipaapix;

import java.util.ArrayList;

import swipe.android.hipaapix.classes.patients.Patient;
import swipe.android.hipaapix.json.patientsResponse.PatientsResultsResponse;
import swipe.android.hipaapix.viewAdapters.SearchResultsAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.edbert.library.network.AsyncTaskCompleteListener;

public class SearchResultActivity extends ListActivity implements
		AdapterView.OnItemClickListener,
		AsyncTaskCompleteListener<PatientsResultsResponse> {

	private SearchResultsAdapter resultsListAdapter;

	@Override
	public void onResume() {
		super.onResume();
		new DummyWebTask<Object>(this, Object.class).execute("", "", "");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.list_layout);

		this.resultsListAdapter = new SearchResultsAdapter(this, R.id.name,
				new ArrayList<Patient>());
		// Assign adapter to List
		setListAdapter(resultsListAdapter);
		ListView listView = getListView();
		listView.setOnItemClickListener(this);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent i = new Intent(this, GridOfSearchResultsWithTakePictureActivity.class);
		startActivity(i);

	}
}