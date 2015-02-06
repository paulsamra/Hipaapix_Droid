package swipe.android.hipaapix.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HippaPixSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();

    private static HipaaPixSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new HipaaPixSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}