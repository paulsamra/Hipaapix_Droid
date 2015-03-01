package swipe.android.hipaapix.json;

public class BlobResponse extends TrueVaultResponse {
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}

	public String getBlob_id() {
		return blob_id;
	}

	public void setBlob_id(String blob_id) {
		this.blob_id = blob_id;
	}

	String category, patient_id, blob_id;
}
