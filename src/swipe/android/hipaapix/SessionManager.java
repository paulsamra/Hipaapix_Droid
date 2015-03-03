package swipe.android.hipaapix;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;

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
	private static final String VAULT_ID = "VAULT_ID";
	private static final String LOGIN = "LOGIN";

	private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
	private static final String SCHEMA_ID = "SCHEMA_ID";

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

	public boolean isLoggedIn() {
		return pref.getBoolean(LOGIN, false);
	}

	public void clearUserPref() {
		// destroy all shared preferences
		SharedPreferences settings = _context.getSharedPreferences(
				SessionManager.PREF_NAME, Context.MODE_PRIVATE);
		settings.edit().clear().commit();
	}

	private static final String ownUserID = "9ab68049-9156-4676-bcd6-14a33621632e";

	private static final String USERNAME = "USERNAME";
	private static final String IS_LOGGED_IN = "IS_LOGGED_IN";

	public void setIsLoggedIn(boolean showAbout) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(IS_LOGGED_IN, showAbout);
		editor.commit();
	}

	public void setUserID(String userID) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(USER_ID, userID);
		editor.commit();
	}

	public void setAccessToken(String access_token) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(ACCESS_TOKEN, access_token);
		editor.commit();
	}

	public void setSchemaID(String schemaID) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putString(SCHEMA_ID, schemaID);
		editor.commit();
	}

	public void setVaultID(String valutID) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putString(VAULT_ID, valutID);
		editor.commit();
	}

	public void setUserName(String username) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putString(USERNAME, username);
		editor.commit();
	}

	public String getUserID() {
		return pref.getString(USER_ID, "");
	}

	public String getAccessToken() {
		return pref.getString(ACCESS_TOKEN, "");
	}

	public String getVaultID() {
		return pref.getString(this.VAULT_ID, "");
	}

	public String getSchemaId() {
		return pref.getString(SCHEMA_ID, "");
	}

	public String getUsername() {
		return pref.getString(USERNAME, "");
	}

	public static final String IMAGE_SCHEMA_ID = "Image_schema_id";

	public void setImageSchemaId(String patient_image_schema) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putString(IMAGE_SCHEMA_ID, patient_image_schema);
		editor.commit();
	}

	public String getImageSchemaId() {
		return pref.getString(IMAGE_SCHEMA_ID, "");
	}

	public static final String PATIENT_ID = "patient_id";

	public String getPatientId() {
		return pref.getString(PATIENT_ID, "");
	}

	public void setPatientID(String patientID) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putString(PATIENT_ID, patientID);
		editor.commit();
	}

	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String LAST_NAME = "LAST_NAME";
	public static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";

	public void setPatientFirstName(String firstName) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putString(FIRST_NAME, firstName);
		editor.commit();
	}

	public void setPatientLastName(String lastName) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putString(LAST_NAME, lastName);
		editor.commit();

	}

	public void setPatientDateOfBirth(String dateofbirth) {

		SharedPreferences.Editor editor = pref.edit();
		editor.putString(DATE_OF_BIRTH, dateofbirth);
		editor.commit();

	}

	public String getPatientFirstName() {
		return pref.getString(FIRST_NAME, "");
	}

	public String getPatientLastName() {
		return pref.getString(LAST_NAME, "");
	}

	public String getPatientDOB() {
		return pref.getString(DATE_OF_BIRTH, "");
	}

	public static final String PATIENT_IMAGES = "PATIENT_IMAGES";

	public void setPatientImages(JSONArray arr) {


		Set<String> mySet = new LinkedHashSet<String>();
		try {

			for (int i = 0; i < arr.length(); i++) {
				mySet.add(arr.getString(i));
				Log.d("Adding", arr.getString(i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		SharedPreferences.Editor editor = pref.edit();
		editor.putStringSet(PATIENT_IMAGES, mySet);
		editor.commit();
	}

	public String[] getPatientImages() {
		Set<String> set = pref.getStringSet(PATIENT_IMAGES,
				new LinkedHashSet<String>());
		return set.toArray(new String[set.size()]);


	}
private static final String IS_OUTDATED = "isOutdated";
	public void setIsDataOutdated(boolean b) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean(IS_OUTDATED, b);
		editor.commit();
	}
	public boolean isOutdated(){
		return pref.getBoolean(IS_OUTDATED, false);
	}
public static final String NEW_DOC = "NEW_DOC";
	public String getNewDocument() {
return pref.getString(NEW_DOC, null);
	}
	public void setNewDoucment(String s) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(NEW_DOC, s);
		editor.commit();
	}
}
