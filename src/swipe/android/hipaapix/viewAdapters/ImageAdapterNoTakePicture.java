package swipe.android.hipaapix.viewAdapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import swipe.android.hipaapix.APIManager;
import swipe.android.hipaapix.GridOfImages;
import swipe.android.hipaapix.HipaapixApplication;
import swipe.android.hipaapix.R;
import swipe.android.hipaapix.SessionManager;
import swipe.android.hipaapix.classes.patients.Patient;
import swipe.android.hipaapix.core.GridOfSearchResultsWithTakePictureFragment;
import swipe.android.hipaapix.json.BlobResponse;
import swipe.android.hipaapix.json.users.GetUserDataResponse;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.edbert.library.network.AsyncTaskCompleteListener;
import com.edbert.library.network.GetDataWebTask;
import com.edbert.library.network.SocketOperator;
import com.edbert.library.utils.MapUtils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageAdapterNoTakePicture extends BaseAdapter {
	private LayoutInflater inflater;
	List<Patient> imageUrls;
	DisplayImageOptions options;
	Context ctx;
	public static final String TAG = ImageAdapterNoTakePicture.class.getName();

	public ImageAdapterNoTakePicture(Context ctx, List<Patient> urls) {
		inflater = LayoutInflater.from(ctx);
		this.imageUrls = urls;
		options = ((HipaapixApplication) ctx.getApplicationContext())
				.getDefaultOptions();
		this.ctx = ctx;
	}

	@Override
	public int getCount() {
		return imageUrls.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item_grid_image, parent, false);
			holder = new ViewHolder();
			assert view != null;
			holder.imageView = new ImageViewAware(
					(ImageView) view.findViewById(R.id.image), false);

			holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
			holder.position = position;
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.position = position;
		holder.progressBar.setProgress(0);
		holder.progressBar.setVisibility(View.VISIBLE);
		String url = imageUrls.get(position).getBlob_url();

		String patient_id = imageUrls.get(position).getPatient_id();
		if (patient_id != null && !patient_id.equals("")) {
			String patient_url = APIManager.getDocumentsURL(this.ctx,
					imageUrls.get(position).getPatient_id());
		
			StringRequest strReq = new StringRequest(Method.GET,
					patient_url, new Response.Listener<String>() {

						@Override
						public void onResponse(String response) {
					String decoded = APIManager.decode64(response);
					Log.d("Inside of adapter", decoded);
					try {
						JSONObject patient = new JSONObject(decoded);
						String firstName = patient.getString("firstName");
						String lastName = patient.getString("lastName");
						String dob = patient.getString("dob");
						imageUrls.get(position).setName(firstName + " " + lastName);
						imageUrls.get(position).setBirthdate(dob);
						String notes = "";
						if(patient.has("notes")){
					notes = patient.getString("notes");
						}
						imageUrls.get(position).setNotes(notes);
						//Log.d("finish loading", "loading");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							
						}
					}){
				@Override
				public HashMap<String, String> getHeaders() {
					HashMap<String, String> params = APIManager
							.defaultSessionHeaders();
					
					return params;
				}
			};
			
			String tagOfQueue = ImageAdapterNoTakePicture.TAG + " " + position;
			HipaapixApplication.getInstance().addToRequestQueue(strReq,
					tagOfQueue);
		}
		/*
		 * ImageLoader loader = ImageLoader.getInstance();
		 * loader.init(((HipaapixApplication) this.ctx.getApplicationContext())
		 * .initImageLoader(this.ctx.getApplicationContext()));
		 */
		ImageLoader.getInstance().displayImage(url, holder.imageView, options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						holder.progressBar.setProgress(0);
						holder.progressBar.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						holder.progressBar.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						holder.progressBar.setVisibility(View.GONE);
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {

					}
				});
		return view;
	}

	static class ViewHolder {
		ImageAware imageView;
		ProgressBar progressBar;
		int position = 0;
	}

}
