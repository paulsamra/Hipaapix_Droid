package swipe.android.hipaapix.json.login;


import swipe.android.hipaapix.json.Error;
import swipe.android.hipaapix.json.TrueVaultResponse;
import swipe.android.hipaapix.json.UserData;

import com.edbert.library.network.sync.JsonResponseInterface;

public class LoginResponse extends TrueVaultResponse implements JsonResponseInterface{
	
	UserData user;
	

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}
}