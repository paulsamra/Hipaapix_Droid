package swipe.android.hipaapix.viewAdapters;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import swipe.android.hipaapix.FieldsParsingUtils;
import swipe.android.hipaapix.R;

import com.edbert.library.network.AsyncTaskCompleteListener;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.google.gson.JsonObject;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.AdapterView.OnItemClickListener;
import de.metagear.android.util.WidgetUtils;
import de.metagear.android.view.ValidatingEditText;
import de.metagear.android.view.ValidatingView;
import de.metagear.android.view.validation.ViewValidator;
import de.metagear.android.view.validation.textview.EmailValidator;
import de.metagear.android.view.validation.textview.MinLengthValidator;
import de.metagear.android.view.validation.textview.NumberValidator;
import de.metagear.android.view.validation.textview.PriceValidator;
import de.metagear.android.view.validation.textview.ZipCodeValidator;

// reviewed
public class PatientSearchAdapter extends BaseFormAdapter {
	
	public PatientSearchAdapter(FragmentActivity ctx, View rootView,
			Bundle savedInstanceState) {
		super(ctx, rootView, savedInstanceState);

		this.ctx = ctx;
		initializeViews(savedInstanceState);

	}


	EditText first_name, last_name;
Button go;
	Switch new_patient;

	private void initializeViews(Bundle savedInstanceState) {
		first_name = (EditText) rootView.findViewById(R.id.first_name_field);

		last_name = (EditText) rootView.findViewById(R.id.last_name_field);

		new_patient = (Switch) rootView
				.findViewById(R.id.new_patient_switch);
		
	}


	@Override
	protected void initializeValidators(View parentView) {
		validatingViews = new ArrayList<ValidatingView>();
		Context context = parentView.getContext();

		synchronized (validatingViews) {

		}
	}

	public JSONObject getJSONObject() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("first_name", first_name.getText());
		jsonObject.put("last_name", last_name.getText());
		jsonObject.put("birthdate", FieldsParsingUtils.getTime(start_date
				.getText().toString()));
		jsonObject.put("is_new_patient", new_patient.isChecked() ? "yes" : "no");
		return jsonObject;
	}

}