package swipe.android.hipaapix.viewAdapters;

import java.util.List;

import swipe.android.hipaapix.HipaapixApplication;
import swipe.android.hipaapix.R;
import swipe.android.hipaapix.core.GridOfSearchResultsWithTakePictureFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageAdapterWithTakePicture extends BaseAdapter {
	private LayoutInflater inflater;
	List<String> imageUrls;
	DisplayImageOptions options;

	public ImageAdapterWithTakePicture(Context ctx, List<String> urls) {
		inflater = LayoutInflater.from(ctx);
		this.imageUrls = urls;
	     options = ((HipaapixApplication)  ctx.getApplicationContext()).getDefaultOptions();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item_grid_image, parent, false);
			holder = new ViewHolder();
			assert view != null;
			holder.imageView = (ImageView) view.findViewById(R.id.image);
			holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		// String url;
		if (position == 0) {
			// take a photo
			holder.imageView.setImageResource(R.drawable.take_photo_static);
			return view;
		}
		// url = "drawable://" + R.drawable.take_photo_static;

		String url = imageUrls.get(position + 1);

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
						holder.progressBar.setProgress(Math.round(100.0f
								* current / total));
					}
				});

		return view;
	}
	static class ViewHolder {
		ImageView imageView;
		ProgressBar progressBar;
	}
}

