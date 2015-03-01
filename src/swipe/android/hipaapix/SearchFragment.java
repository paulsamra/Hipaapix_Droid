package swipe.android.hipaapix;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.droidparts.widget.ClearableEditText;
import org.json.JSONObject;

import swipe.android.hipaapix.classes.patients.Patient;
import swipe.android.hipaapix.core.BaseFragment;
import swipe.android.hipaapix.core.FragmentInfo;
import swipe.android.hipaapix.core.GridOfSearchResultsNoTakePictureFragment;
import swipe.android.hipaapix.core.HipaaPixTabsActivityContainer;
import swipe.android.hipaapix.json.UploadPatientResponse;
import swipe.android.hipaapix.json.searchvaultImage.SearchVaultImageResponse;
import swipe.android.hipaapix.viewAdapters.ExpandableListAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Switch;

import com.edbert.library.containers.PositionalLinkedMap;
import com.edbert.library.network.AsyncTaskCompleteListener;
import com.edbert.library.network.GetDataWebTask;
import com.edbert.library.network.PostDataWebTask;
import com.edbert.library.utils.MapUtils;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;

public class SearchFragment extends BaseFragment implements OnDateSetListener,
		AsyncTaskCompleteListener<UploadPatientResponse> {
	Button go;
	ExpandableListView expListView;
	List<String> listDataHeader;
	PositionalLinkedMap<String, List<String>> listDataChild = new PositionalLinkedMap<String, List<String>>();

	ExpandableListAdapter listAdapter;
	ClearableEditText start_date, first_name_field, last_name_field;

	public static final String DATEPICKER_START_TAG = "datepicker_start";
	Calendar calendar;
	DatePickerDialog datePickerDialog;
	Switch newPatient;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.search_layout, container, false);
		go = (Button) view.findViewById(R.id.submit);
		newPatient = (Switch) view.findViewById(R.id.new_patient_switch);
		first_name_field = (ClearableEditText) view
				.findViewById(R.id.first_name_field);
		last_name_field = (ClearableEditText) view
				.findViewById(R.id.last_name_field);
		calendar = Calendar.getInstance();
		go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (!newPatient.isChecked()) {
						doSearch();
					} else {
						doCreate();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		datePickerDialog = DatePickerDialog.newInstance(this,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), false);

		// get the listview
		expListView = (ExpandableListView) view
				.findViewById(R.id.category_list);
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Bundle b = new Bundle();

				List<String> children = listDataChild.getValue(groupPosition);
				String value = children.get(childPosition);
				b.putString(CategorySearchResultFragment.BUNDLE_TITLE_ID, value);
				mActivity.pushFragments(HipaaPixTabsActivityContainer.TAB_A,
						new CategorySearchResultFragment(), true, true, b);
				return false;
			}

		});
		start_date = (ClearableEditText) view.findViewById(R.id.date);

		this.setUpDate(start_date);
		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this.getActivity(),
				listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);
		if (savedInstanceState != null) {
			DatePickerDialog dpd = (DatePickerDialog) SearchFragment.this
					.getActivity().getSupportFragmentManager()
					.findFragmentByTag(DATEPICKER_START_TAG);
			if (dpd != null) {
				dpd.setOnDateSetListener(this);
			}

		}
		return view;
	}

	protected void setUpDate(EditText start_date) {
		if (start_date == null) {
			return;
		}
		start_date.setFocusable(false);
		start_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				datePickerDialog.setVibrate(false);
				datePickerDialog.setYearRange(1985, 2028);
				datePickerDialog.show(SearchFragment.this.getActivity()
						.getSupportFragmentManager(), DATEPICKER_START_TAG);

			}
		});

	}

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new PositionalLinkedMap<String, List<String>>();
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
		String monthName = new DateFormatSymbols().getMonths()[month];
		String total = monthName + " " + day + ", " + year;
		if (datePickerDialog.getTag().equals(DATEPICKER_START_TAG)) {
			start_date.setText(total);
			start_date.setTextColor(Color.BLACK);
			start_date.setClearIconVisible(true);
		}

	}

	public void doCreate() throws Exception {
		if (first_name_field.getText().toString().equals("")
				|| last_name_field.getText().toString().equals("")
				|| start_date.getText().toString().equals("")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					this.getActivity());

			builder.setMessage(
					"Please make sure you enter a first name, last name, and date of birth for new patients")
					.setTitle("Missing Info")
					.setCancelable(true)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
			return;
		}
		String start_date_string = FieldsParsingUtils
				.convertDisplayStringToProper(start_date.getText().toString(),
						"");
		String document = APIManager.createPatientDocument(this.getActivity(),
				new LocalPatient(first_name_field.getText().toString(),
						last_name_field.getText().toString(),
						start_date_string, ""));
		Map<String, String> headers = APIManager.defaultSessionHeaders();
		Map<String, String> form = new HashMap<String, String>();
		form.put("document", document);
		form.put("schema_id", SessionManager.getInstance(this.getActivity())
				.getSchemaId());

		String url = APIManager.createUserDocumentURL(this.getActivity());

		new PostDataWebTask<UploadPatientResponse>(
				(AsyncTaskCompleteListener<UploadPatientResponse>) this,
				this.getActivity(), UploadPatientResponse.class, true, true)
				.execute(url, MapUtils.mapToString(headers),
						MapUtils.mapToString(form));
	}

	public void doSearch() throws Exception {

		String encoded = APIManager.searchOptions(this.getActivity(),
				first_name_field, last_name_field, start_date);

		Bundle b = new Bundle();
		b.putString("options", encoded);
		// Log.d("Full body", searchOptions);
		mActivity.pushFragments(HipaaPixTabsActivityContainer.TAB_A,
				new PatientSearchListFragment(), true, true, b);

	}

	@Override
	public void onTaskComplete(UploadPatientResponse result) {
		if (result != null && result.isValid()) {
			try {
				doSearch();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			String loginTitle = "Error";
			String message = "Failed to add patient";
			String try_again = this.getString(R.string.try_again);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					this.getActivity());
			builder.setMessage(message)
					.setTitle(loginTitle)
					.setCancelable(false)
					.setPositiveButton(try_again,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});

			AlertDialog alert = builder.create();
			alert.show();
		}
	}
}