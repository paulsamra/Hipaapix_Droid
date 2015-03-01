package swipe.android.hipaapix.json;

public class UploadPatientResponse extends TrueVaultResponse{
	String document_id;

	public String getDocument_id() {
		return document_id;
	}

	public void setDocument_id(String document_id) {
		this.document_id = document_id;
	}
}