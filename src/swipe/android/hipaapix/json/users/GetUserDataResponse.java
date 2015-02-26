package swipe.android.hipaapix.json.users;



import swipe.android.hipaapix.json.Error;
import swipe.android.hipaapix.json.TrueVaultResponse;
import swipe.android.hipaapix.json.UserData;

import com.edbert.library.network.sync.JsonResponseInterface;

public class GetUserDataResponse extends TrueVaultResponse implements JsonResponseInterface{
	
	UserData user;
	

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}
}