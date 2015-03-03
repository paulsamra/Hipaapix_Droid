package swipe.android.hipaapix;

import org.json.JSONException;
import org.json.JSONObject;

class LocalPatientImage
{
	public LocalPatientImage( String category, String blobID,
			String thumbnailID, String patientID, String notes) {
		super();
		this.date =  System.currentTimeMillis() / 1000L;;
		this.category = category;
		this.blobID = blobID;
		this.thumbnailID = thumbnailID;
		this.patientID = patientID;
		this.notes = notes;
	}
	long date;
	String category, blobID, thumbnailID, patientID, notes;
	
	public String toJSON(){
		JSONObject body = new JSONObject();
		try {
			body.put("category", category);
		
		body.put("notes", notes);
		body.put("patient_id", patientID);
		body.put("blob_id", blobID);
		body.put("thumbnail", thumbnailID);
		body.put("date", date);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return body.toString();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBlobID() {
		return blobID;
	}

	public void setBlobID(String blobID) {
		this.blobID = blobID;
	}

	public String getThumbnailID() {
		return thumbnailID;
	}

	public void setThumbnailID(String thumbnailID) {
		this.thumbnailID = thumbnailID;
	}

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}