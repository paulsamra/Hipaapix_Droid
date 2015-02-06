package swipe.android.hipaapix.viewAdapters;

import java.util.List;

import swipe.android.hipaapix.HipaapixApplication;
import swipe.android.hipaapix.R;
import swipe.android.hipaapix.classes.patients.Patient;
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

public class SearchResultsAdapter extends ArrayAdapter<Patient> {


	public SearchResultsAdapter(Context context, int textViewResourceId,
			List<Patient> objects) {
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
			viewHolder.patient_preivew = (ImageView) updateView
					.findViewById(R.id.patient_preivew);
		
			updateView.setTag(viewHolder);
		} else {
			updateView = view;
			viewHolder = (ViewHolder) updateView.getTag();
		}
		
		Patient patient = (Patient) getItem(position);
		viewHolder.name.setText(patient.getName());
		viewHolder.birthdate.setText(patient.getBirthdate());
		viewHolder.category.setText(patient.getCategory());
		String person_image_url = patient.getPatient_url();
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

		return updateView;
	}

	private static class ViewHolder {
		public TextView name, birthdate, category;
		public ImageView patient_preivew;
	}
}