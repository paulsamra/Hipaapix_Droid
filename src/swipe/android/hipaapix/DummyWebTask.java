package swipe.android.hipaapix;


import android.app.Activity;
import android.support.v4.app.Fragment;

import com.edbert.library.network.AsyncTaskCompleteListener;
import com.edbert.library.network.GetDataWebTask;

public class DummyWebTask<T> extends GetDataWebTask<T> {

	public DummyWebTask(Activity act, Class<T> type) {
		super(act, type);
	}


	public DummyWebTask(Activity act, Fragment f, Class<T> type, boolean showDialog) {
		super(act, f, type, showDialog);
	}

	public DummyWebTask(Activity context,
			AsyncTaskCompleteListener asyncTaskCompleteListener,
			Class<T> class1) {
		super(context, asyncTaskCompleteListener, class1);
	}

	@Override
	protected T doInBackground(String... uri) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}