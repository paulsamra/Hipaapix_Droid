package swipe.android.hipaapix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import swipe.android.hipaapix.viewAdapters.ImageAdapterWithTakePicture;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class GridOfSearchResultsWithTakePictureFragment extends GridFragment {
	public static final int INDEX = 1;
	List<String> imageUrls = Arrays.asList(Constants.IMAGES);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.grid_layout, container, false);

		listView = (GridView) view.findViewById(R.id.grid);
		((GridView) listView).setAdapter(new ImageAdapterWithTakePicture(this
				.getActivity(), imageUrls));

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// /startImagePagerActivity(position);
			}
		});
		return view;
	}

}