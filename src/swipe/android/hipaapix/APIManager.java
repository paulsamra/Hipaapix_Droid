package swipe.android.hipaapix;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import swipe.android.hipaapix.classes.patients.Patient;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

public class APIManager {
	public static String searchOptions(Context ctx, EditText first_name_field,
			EditText last_name_field, EditText start_date) throws Exception {

		JSONObject fullBody = new JSONObject();
		// buildling filter
		JSONObject filter = new JSONObject();
		JSONObject first_name = new JSONObject();
		JSONObject last_name = new JSONObject();
		JSONObject dob = new JSONObject();

		if (!first_name_field.getText().toString().equals("")) {
			first_name.put("value", first_name_field.getText().toString());
			first_name.put("type", "eq");
			first_name.put("case_sensitive", false);
			filter.put("firstName", first_name);

		}
		if (!last_name_field.getText().toString().equals("")) {
			last_name.put("value", last_name_field.getText().toString());
			last_name.put("type", "eq");
			last_name.put("case_sensitive", false);
			filter.put("lastName", last_name);
		}

		if (!start_date.getText().toString().equals("")) {
			String proper = FieldsParsingUtils.convertDisplayStringToProper(
					start_date.getText().toString(), "");
			Log.d("DATE", proper);
			dob.put("value", proper);
			dob.put("type", "eq");
			filter.put("dob", dob);
		}

		JSONObject sort = new JSONObject();
		sort.put("lastName", "desc");
		fullBody.put("sort", sort);
		// /
		fullBody.put("filter", filter);
		fullBody.put("filter_type", "and");
		fullBody.put("full_document", true);
		fullBody.put("schema_id", SessionManager.getInstance(ctx).getSchemaId());

		String searchOptions = fullBody.toString();

		String encoded = encode64(searchOptions);
		return encoded;
	}

	public static String categorySearchOptions(Context ctx, String s)
			throws Exception {

		JSONObject fullBody = new JSONObject();
		// buildling filter
		JSONObject filter = new JSONObject();
		JSONObject category = new JSONObject();

		category.put("value", s);
		category.put("type", "eq");
		category.put("case_sensitive", false);
		filter.put("category", category);

		// /
		fullBody.put("filter", filter);
		fullBody.put("filter_type", "or");
		fullBody.put("full_document", true);
		fullBody.put("schema_id", SessionManager.getInstance(ctx)
				.getImageSchemaId());

		String searchOptions = fullBody.toString();

		String encoded = encode64(searchOptions);
		return encoded;

	}

	/*
	 * public static String createUserOptions(Context ctx,String firstName,
	 * String lastName, String email){
	 * 
	 * JSONObject firstNameJson = new JSONObject();
	 * firstNameJson.put("firstName", firstName);
	 * 
	 * JSONObject lastNameJson = new JSONObject();
	 * firstNameJson.put("firstName", firstName);
	 * 
	 * JSONObject emailJson = new JSONObject(); firstNameJson.put("firstName",
	 * firstName);
	 * 
	 * JSONObject patient_schemaJson = new JSONObject();
	 * firstNameJson.put("firstName", firstName);
	 * 
	 * //JSONObject patient_schemaJson = new JSONObject();
	 * SessionManager.getInstance(ctx).getVaultID();
	 * SessionManager.getInstance(ctx).getSchemaId();
	 * 
	 * }
	 */

	public static String getDocumentsURL(Context ctx, String documents) {
		return URL_BASE + "/v1/vaults/"
				+ SessionManager.getInstance(ctx).getVaultID() + "/documents/"
				+ documents;
	}

	public static String downloadRawBlobUrl(Context ctx, String blobID) {
		return URL_BASE + "/v1/vaults/"
				+ SessionManager.getInstance(ctx).getVaultID() + "/blobs/"
				+ blobID;
	}

	public static String downloadDocumentURL(Context ctx, String document) {
		return URL_BASE + "/v1/vaults/"
				+ SessionManager.getInstance(ctx).getVaultID() + "/documents/"
				+ document;
	}

	public static String getLoginURL() {
		return URL_BASE + "/v1/auth/login";
	}

	public static String getUserURL(Context ctx) {
		return getUserURL(ctx, SessionManager.getInstance(ctx).getUserID());
	}

	public static String getUserURL(Context ctx, String id) {
		return URL_BASE + "/v1/users/" + id + "?verbose=1";
	}

	public static String searchVaultURL(Context ctx, String options) {
		return URL_BASE + "/v1/vaults/"
				+ SessionManager.getInstance(ctx).getVaultID()
				+ "/?search_option=" + options;
	}

	public static String createUserDocumentURL(Context ctx) {
		return URL_BASE + "/v1/vaults/"
				+ SessionManager.getInstance(ctx).getVaultID() + "/documents";
	}

	public static String updateUserDocumentURL(Context ctx) {
		return URL_BASE + "/v1/vaults/"
				+ SessionManager.getInstance(ctx).getVaultID() + "/documents/"
				+ SessionManager.getInstance(ctx).getPatientId();
	}

	public static HashMap<String, String> defaultSessionHeaders() {
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Basic " + encode64UserPass(apiKey, ""));
		return headers;
	}

	private static String encode64UserPass(String username, String password) {
		return encode64(username + ":" + password);
	}

	public static String encode64(String authString) {
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		return authStringEnc;
	}

	private static final String URL_BASE = "https://api.truevault.com";

	public static final String apiKey = "9b1528d9-801e-4a45-844f-9a8ecc01c32e";
	public static final String accountID = "f7f034f7-be01-4480-bdd6-e144a942c969";

	private APIManager(Context c) {
		this._context = c;

	}

	Context _context;
	private static APIManager instance;

	public static synchronized APIManager getInstance(Context c) {
		if (instance == null)
			instance = new APIManager(c);
		return instance;
	}

	public static String decode64(String str) {
		// Receiving side
		byte[] data = android.util.Base64.decode(str,
				android.util.Base64.DEFAULT);
		String text = "";
		try {
			text = new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;

	}

	public static String createPatientDocument(Context ctx, LocalPatient patient) {
		String body = patient.toJSON();
		return encode64(body);
	}

	public static String createPatientImageDocument(Context ctx,
			LocalPatientImage patient) {
		String body = patient.toJSON();
		return encode64(body);
	}

	public static String uploadImageURL(Context ctx) {
		return URL_BASE + "/v1/vaults/"
				+ SessionManager.getInstance(ctx).getVaultID() + "/blobs";

	}

	public static String updatePatientDocumentString(Context ctx,
			String documentID) {
		JSONObject body = new JSONObject();
		try {
			body.put("firstName", SessionManager.getInstance(ctx)
					.getPatientFirstName());

			body.put("lastName", SessionManager.getInstance(ctx)
					.getPatientLastName());
			body.put("dob", SessionManager.getInstance(ctx).getPatientDOB());

			JSONArray documents = new JSONArray();

			String[] docs = SessionManager.getInstance(ctx).getPatientImages();
			for(int i = 0 ; i < docs.length; i++){
				documents.put(docs[i]);
			}
			documents.put(documentID);
			body.put("images", documents);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encode64(body.toString());
	}

}