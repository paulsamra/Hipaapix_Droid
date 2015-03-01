package swipe.android.hipaapix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import swipe.android.hipaapix.json.searchvault.EncodedDocument;
import com.edbert.library.network.AsyncTaskCompleteListener;
import com.edbert.library.network.GetDataWebTask;
import com.edbert.library.network.SocketOperator;
import com.edbert.library.utils.MapUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import swipe.android.hipaapix.classes.patients.Patient;
import swipe.android.hipaapix.core.GridOfSearchResultsNoTakePictureFragment;
import swipe.android.hipaapix.json.BlobResponse;
import swipe.android.hipaapix.json.DocumentsResponse;
import swipe.android.hipaapix.json.searchvault.Document;
import swipe.android.hipaapix.json.searchvaultImage.SearchVaultImageResponse;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class PatientGridOfImagesFragment extends
		GridOfSearchResultsNoTakePictureFragment implements
		AsyncTaskCompleteListener<DocumentsResponse> {
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

		try {
			initialize(extras.getString(BUNDLE_TITLE_ID));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setHasOptionsMenu(true);
		return v;
	}
	String name;
	private void initialize(String s) throws Exception {
		JSONObject patient = new JSONObject(s);
		String firstName = patient.getString("firstName");
		String lastName = patient.getString("lastName");
		JSONArray images = patient.getJSONArray("images");
		name = firstName + " " + lastName;
		String documents = "";
		for (int i = 0; i < images.length(); i++) {
			// String t = images.getString(i);
			if (i == images.length() - 1) {
				documents += images.getString(i);
			} else {
				documents += images.getString(i) + ",";
			}
			// imageUrls.add(new Patient(t, title,"",""));
		}

		String url = APIManager.getDocumentsURL(this.getActivity(), documents);
		Map<String, String> headers = APIManager.defaultSessionHeaders();

		Log.d("URL", url);
		
		new GetDataWebTask<DocumentsResponse>(
				(AsyncTaskCompleteListener<DocumentsResponse>) this,this.getActivity(),
				DocumentsResponse.class, true).execute(url,
				MapUtils.mapToString(headers));
		

		ActionBar ab = this.getActivity().getActionBar();
		ab.setTitle(name);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.add_picture_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle item selection
		switch (item.getItemId()) {
		case R.id.add_picture_item:
			launchCamera();
			return true;
		default:
			this.getActivity().onBackPressed();
			return super.onOptionsItemSelected(item);
		}
	}

	public void launchCamera() {
		Intent i = new Intent(this.getActivity(), HipaaPixCamera.class);
		this.getActivity().startActivity(i);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent i = new Intent(this.getActivity(), FullScreenViewActivity.class);
		i.putParcelableArrayListExtra("list",
				super.imageUrls);
		i.putExtra("position", position);
		startActivity(i);
	}


	@Override
	public void onTaskComplete(DocumentsResponse docResponse) {
		if(docResponse.isValid()){
			for(EncodedDocument doc : docResponse.getDocuments()){
				String decode = APIManager.decode64(doc.getDocument());
				
				Gson gson3 = new Gson();
				Document parsedResponse = gson3.fromJson(decode, Document.class);

				String url = APIManager.downloadRawBlobUrl(this.getActivity(), parsedResponse.getBlob_id());
			
				super.imageUrls.add(new Patient("" , name ,"" , parsedResponse.getCategory(), url));
			}
			adapter.notifyDataSetChanged();
			adapter.notifyDataSetInvalidated();
		}
	}
}