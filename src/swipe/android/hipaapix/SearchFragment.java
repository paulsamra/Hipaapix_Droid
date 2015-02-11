package swipe.android.hipaapix;

import java.util.ArrayList;
import java.util.Calendar;
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
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;

public class SearchFragment extends BaseFragment implements OnDateSetListener {
	Button go;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	ExpandableListAdapter listAdapter;
	Button start_date;

	public static final String DATEPICKER_START_TAG = "datepicker_start";
	Calendar calendar;
DatePickerDialog datePickerDialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_layout, container, false);
		go = (Button) view.findViewById(R.id.submit);

		 calendar = Calendar.getInstance();
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
		 datePickerDialog = DatePickerDialog.newInstance(
					this, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH), false);

		// get the listview
		expListView = (ExpandableListView) view
				.findViewById(R.id.category_list);
		TextView from_label = (TextView) view.findViewById(R.id.from_label);
		from_label.setVisibility(View.GONE);
		start_date = (Button) view.findViewById(R.id.date);
		this.setUpDate(start_date);
		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this.getActivity(),
				listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);
		if (savedInstanceState != null) {
			DatePickerDialog dpd = (DatePickerDialog) SearchFragment.this.getActivity().getSupportFragmentManager()
					.findFragmentByTag(DATEPICKER_START_TAG);
			if (dpd != null) {
				dpd.setOnDateSetListener(this);
			}
		
		}
		return view;
	}
	protected void setUpDate(Button start_date){
		if(start_date == null){
			return;
		}
		start_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				datePickerDialog.setVibrate(false);
				datePickerDialog.setYearRange(1985, 2028);
				datePickerDialog.show(SearchFragment.this.getActivity().getSupportFragmentManager(),
						DATEPICKER_START_TAG);
			}

		});

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
	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year,
			int month, int day) {
		// TODO Auto-generated method stub
		
	}
}