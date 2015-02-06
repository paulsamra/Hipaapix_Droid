package swipe.android.hipaapix;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.edbert.library.database.DatabaseCommandManager;

public class SessionManager {
	// Shared Preferences
	static SharedPreferences pref;

	// Editor for Shared preferences
	static Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	static int PRIVATE_MODE = 0;

	// Sharedpref file name
	public static final String PREF_NAME = "HIPPA_PIX_REFERENCE";

	private static SessionManager instance;

	// Default values
	public static final float DEFAULT_SEARCH_RADIUS = 20.0f;
	public static final String DEFAULT_STRING = "";
	public static final int DEFAULT_VALUE = -1;
	private static final String USER_ID = "USER_ID";

	public static final float SEARCH_DEFAULT_NUMERIC = -1;
	private static final String URL_BASE = "https://hipaapix";

	public String loginURL() {
		return URL_BASE + "/login";
	}

	public String needsDetailsFollowersURL(String id) {
		return URL_BASE + "/login";
	}

	public String needsDetailsOffersURL(String id) {
		return URL_BASE + "/login";
	}

	public String needDetailsURL(String id) {
		return URL_BASE + "/login";
	}

	public String exploreGroupsURL() {
		return URL_BASE + "/groups";
	}

	public String needsDetailsBidsURL(String id) {
		return URL_BASE + "/login";
	}

	public String needDetailsQueryURL(String location, String searchFilter) {
		return URL_BASE + "/login";
	}

	public String exploreNeedsURL() {
		return URL_BASE + "/explore";
	}

	public String exploreEventsURL() {
		return URL_BASE + "/events";
	}

	public String createEventURL() {
		return URL_BASE + "/event";
	}

	public String commentsURL(String id) {
		return URL_BASE + "/need/" + id + "/comments";
	}

	private SessionManager(Context c) {
		this._context = c;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public static synchronized SessionManager getInstance(Context c) {
		if (instance == null)
			instance = new SessionManager(c);
		return instance;
	}

	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String CATEGORY = "category";
	public static final String LOGIN = "login";
	private static final String TOKEN = "TOKEN";

	public String getEmail() {
		return pref.getString(EMAIL, this.DEFAULT_STRING);
	}

	public String getPassword() {
		return pref.getString(PASSWORD, this.DEFAULT_STRING);
	}

	public String getCategory() {
		return pref.getString(CATEGORY, this.DEFAULT_STRING);
	}

	public void setEmail(String email) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(EMAIL, email);
		editor.commit();
	}

	public void setPassword(String password) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(PASSWORD, password);
		editor.commit();
	}

	public void setCategory(String category) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(CATEGORY, category);
		editor.commit();
	}

	public void resetTables() {
		DatabaseCommandManager.deleteAllTables(HipaapixContentProvider
				.getDBHelperInstance(_context).getWritableDatabase());

		DatabaseCommandManager.createAllTables(HipaapixContentProvider
				.getDBHelperInstance(_context).getWritableDatabase());
	}
	public void setLogin(boolean login) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(LOGIN, login);
		editor.commit();
	}

	public boolean isLoggedIn(){
		return pref.getBoolean(LOGIN, false);
	}
	public void clearUserPref() {
		// destroy all shared preferences
		SharedPreferences settings = _context.getSharedPreferences(
				SessionManager.PREF_NAME, Context.MODE_PRIVATE);
		settings.edit().clear().commit();
	}
	
	public Map<String, String> defaultSessionHeaders() {
		Map<String, String> headers = new LinkedHashMap<String, String>();

		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("Cache-Control", "none");

		if (getAuthToken() != null && getAuthToken() != "") {
			Log.e("Token", getAuthToken());
			headers.put("token", getAuthToken());
		}
		return headers;
	}
	
	public String getAuthToken() {
		return pref.getString(TOKEN, "");
	}
	public void setAuthToken(String token) {

		editor.putString(TOKEN, token);
		editor.commit();
	}
}

