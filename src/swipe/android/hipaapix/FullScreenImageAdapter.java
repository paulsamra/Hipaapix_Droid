package swipe.android.hipaapix;


import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FullScreenImageAdapter extends PagerAdapter {

	private Activity _activity;
	private ArrayList<String> _imagePaths;
	private LayoutInflater inflater;

	// constructor
	public FullScreenImageAdapter(Activity activity,
			ArrayList<String> imagePaths) {
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
 
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);
 
        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
        
        DisplayImageOptions options = ((HipaapixApplication)  _activity.getApplication()).getDefaultOptions();
       ImageLoader.getInstance().displayImage(_imagePaths.get(position), imgDisplay, options,
				new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				/*holder.progressBar.setProgress(0);
				holder.progressBar.setVisibility(View.VISIBLE);*/
			}

			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				//holder.progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				//holder.progressBar.setVisibility(View.GONE);
			}
		}, new ImageLoadingProgressListener() {
			@Override
			public void onProgressUpdate(String imageUri, View view,
					int current, int total) {
			/*	holder.progressBar.setProgress(Math.round(100.0f
						* current / total));*/
			}
		});
        
        
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
