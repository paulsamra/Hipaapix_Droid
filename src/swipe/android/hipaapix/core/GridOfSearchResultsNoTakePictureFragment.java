package swipe.android.hipaapix.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import swipe.android.hipaapix.Constants;
import swipe.android.hipaapix.GridFragment;
import swipe.android.hipaapix.R;
import swipe.android.hipaapix.R.id;
import swipe.android.hipaapix.R.layout;
import swipe.android.hipaapix.classes.patients.Patient;
import swipe.android.hipaapix.viewAdapters.ImageAdapterNoTakePicture;
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

public class GridOfSearchResultsNoTakePictureFragment extends GridFragment implements OnItemClickListener{
	public static final int INDEX = 1;
	protected ArrayList<Patient> imageUrls = new ArrayList<Patient>();//Arrays.asList(Constants.IMAGES);
	protected ImageAdapterNoTakePicture adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.grid_layout, container, false);

		listView = (GridView) view.findViewById(R.id.grid);
		adapter = new ImageAdapterNoTakePicture(this
				.getActivity(), imageUrls);
		((GridView) listView).setAdapter(adapter);

		listView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		
	}

}