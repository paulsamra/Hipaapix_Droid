package swipe.android.hipaapix.core;

import android.content.Intent;

public interface ActivityCallbackFromAdapter{
	public void startActivityForResultBridge(Intent i, int request_code);
}