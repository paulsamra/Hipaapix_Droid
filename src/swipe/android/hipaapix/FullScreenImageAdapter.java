package swipe.android.hipaapix;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import swipe.android.hipaapix.classes.patients.Patient;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

public class FullScreenImageAdapter extends PagerAdapter {

	private Activity _activity;
	private ArrayList<Patient> _imagePaths;
	private LayoutInflater inflater;

	// constructor
	public FullScreenImageAdapter(Activity activity,
			ArrayList<Patient> imagePaths) {
		this._activity = activity;
		this._imagePaths = imagePaths;
	}

	@Override
	public int getCount() {
		return this._imagePaths.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		TouchImageView imgDisplay;
		Button btnClose;
		TextView patient, category;
		inflater = (LayoutInflater) _activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image,
				container, false);

		imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
		btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
		patient = (TextView) viewLayout.findViewById(R.id.patient);
		category = (TextView) viewLayout.findViewById(R.id.category_img);
		patient.setText(_imagePaths.get(position).getName());
		category.setText(_imagePaths.get(position).getCategory());
		DisplayImageOptions options = ((HipaapixApplication) _activity
				.getApplication()).getDefaultOptions();

		// MemoryCacheUtils.findCachedBitmapsForImageUri(_imagePaths.get(position).getPatient_url(),
		// ImageLoader.getInstance().getMemoryCache());
		// Begin ImageLoader On Cache
		String URL2 = _imagePaths.get(position).getBlob_url();
		

		List<String> memCache = MemoryCacheUtils.findCacheKeysForImageUri(URL2,
				ImageLoader.getInstance().getMemoryCache());
		boolean cacheFound = !memCache.isEmpty();


		/*
		 * Toast.makeText(this._activity, "memCache:" + cacheFound,
		 * Toast.LENGTH_LONG).show();
		 * 
		 * if (ImageLoader.getInstance().getMemoryCache().get(URL2) != null)
		 * Toast.makeText(this._activity, "something is there",
		 * Toast.LENGTH_LONG).show(); else Toast.makeText(this._activity,
		 * "Nothing is there", Toast.LENGTH_LONG).show();
		 */
		ImageLoader.getInstance().displayImage(URL2,
				(ImageView) viewLayout.findViewById(R.id.imgDisplay), options);
		// END

		// close button click event
		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_activity.finish();
			}
		});

		((ViewPager) container).addView(viewLayout);

		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((RelativeLayout) object);

	}

}
