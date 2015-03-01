package swipe.android.hipaapix;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class LocalPatient {
	String firstName, lastName, dob, id;
	String[] documentIDs;

	public LocalPatient(String firstName, String lastName, String dob, String id) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.id = id;
	}

	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getDocumentIDs() {
		return documentIDs;
	}

	public void setDocumentIDs(String[] documentIDs) {
		this.documentIDs = documentIDs;
	}

	public String toJSON() {
		JSONObject body = new JSONObject();
		try {
			body.put("firstName", firstName);

			body.put("lastName", lastName);

			body.put("dob", dob);
			body.put("images", documentIDs);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return body.toString();
	}
}