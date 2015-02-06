package swipe.android.hipaapix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.settings_layout, container, false);
		View footerView = inflater.inflate(R.layout.settings_list_footer, null);
		ListView listview = (ListView) view.findViewById(android.R.id.list);
		listview.addFooterView(footerView);

		Button logout = (Button) footerView.findViewById(R.id.logout);
		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
			//	Log.d("Logging out", "loggingout");
				Intent i = new Intent(SettingsFragment.this.getActivity(), LoginActivity.class);
				startActivity(i);
				SettingsFragment.this.getActivity().finish();
			}
		});

		return view;
	}

}