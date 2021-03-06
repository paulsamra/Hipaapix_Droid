package swipe.android.hipaapix.core;

import java.util.Arrays;
import java.util.List;

import swipe.android.hipaapix.Constants;
import swipe.android.hipaapix.GridActivity;
import swipe.android.hipaapix.R;
import swipe.android.hipaapix.R.id;
import swipe.android.hipaapix.R.layout;
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

public class GridOfSearchResultsWithTakePictureActivity extends GridActivity implements OnItemClickListener{

	public static final int INDEX = 1;
	List<String> imageUrls = Arrays.asList(Constants.IMAGES);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.grid_layout);
		listView = (GridView) findViewById(R.id.grid);
		((GridView) listView).setAdapter(new ImageAdapterWithTakePicture(this,
				imageUrls));
		listView.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}