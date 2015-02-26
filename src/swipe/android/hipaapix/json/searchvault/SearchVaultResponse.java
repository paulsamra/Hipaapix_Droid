package swipe.android.hipaapix.json.searchvault;



import swipe.android.hipaapix.json.Error;
import swipe.android.hipaapix.json.TrueVaultResponse;
import swipe.android.hipaapix.json.UserData;

import com.edbert.library.network.sync.JsonResponseInterface;

public class SearchVaultResponse extends TrueVaultResponse implements JsonResponseInterface{
	
	Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	

}