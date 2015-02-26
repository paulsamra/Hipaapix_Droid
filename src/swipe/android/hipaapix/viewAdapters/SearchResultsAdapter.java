package swipe.android.hipaapix.viewAdapters;

import java.util.List;

import org.json.JSONObject;

import swipe.android.hipaapix.HipaapixApplication;
import swipe.android.hipaapix.R;
import swipe.android.hipaapix.SessionManager;
import swipe.android.hipaapix.classes.patients.Patient;
import swipe.android.hipaapix.json.searchvault.Document;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class SearchResultsAdapter extends ArrayAdapter<Document> {


	public SearchResultsAdapter(Context context, int textViewResourceId,
			List<Document> objects) {
		super(context, textViewResourceId, objects);
	}

	public View getView(int position, View view, ViewGroup viewGroup) {
		View updateView;
		ViewHolder viewHolder;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			updateView = inflater.inflate(R.layout.patient_list_item, null);
			viewHolder = new ViewHolder();
			
			viewHolder.name = (TextView) updateView
					.findViewById(R.id.name);
			viewHolder.birthdate = (TextView) updateView
					.findViewById(R.id.birthdate);
			viewHolder.category = (TextView) updateView
					.findViewById(R.id.category);
		/*	viewHolder.patient_preivew = (ImageView) updateView
					.findViewById(R.id.patient_preivew);*/
		
			updateView.setTag(viewHolder);
		} else {
			updateView = view;
			viewHolder = (ViewHolder) updateView.getTag();
		}
		
		Document patient = getItem(position);
		String realPatient = SessionManager.getInstance(this.getContext()).decode64(patient.getDocument());
		try{
			JSONObject jsonPatient = new JSONObject(realPatient);
		
		viewHolder.name.setText(jsonPatient.getString("lastName") + ", "+jsonPatient.getString("firstName"));
		viewHolder.birthdate.setText(jsonPatient.getString("dob"));
		//viewHolder.category.setText(patient.getCategory());
		viewHolder.category.setVisibility(View.GONE);
		}catch(Exception e){
			e.printStackTrace();
		}
		/*String person_image_url = patient.getPatient_url();
		ImageLoader.getInstance().displayImage(
				person_image_url, viewHolder.patient_preivew,
				HipaapixApplication.getDefaultOptions(),
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
					
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {
					}
				});
*/
		return updateView;
	}

	private static class ViewHolder {
		public TextView name, birthdate, category;
		//public ImageView patient_preivew;
	}
}