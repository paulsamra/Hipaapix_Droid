package swipe.android.hipaapix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import swipe.android.hipaapix.core.BaseFragment;
import swipe.android.hipaapix.viewAdapters.ExpandableListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

public class SearchFragment extends BaseFragment {
	Button go;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	ExpandableListAdapter listAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_layout, container, false);
		go = (Button) view.findViewById(R.id.submit);
		go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/* Go to next fragment in navigation stack */
				// mActivity.pushFragments(AppConstants.TAB_A, new
				// AppTabASecondFragment(),true,true);
				Intent i = new Intent(SearchFragment.this.getActivity(),
						SearchResultActivity.class);
				SearchFragment.this.getActivity().startActivity(i);
			}
		});

		// get the listview
		expListView = (ExpandableListView) view
				.findViewById(R.id.category_list);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this.getActivity(),
				listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		return view;
	}

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Search By Category");
		// Adding child data
		List<String> top250 = new ArrayList<String>();
		top250.add("Otology");
		top250.add("Endoscopic Sinus");
		top250.add("Head and Neck");
		top250.add("Laryngology");
		top250.add("Plastics");
		top250.add("Imaging");

		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data

	}
}