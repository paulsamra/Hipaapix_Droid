package swipe.android.hipaapix.json;

import java.util.ArrayList;

import swipe.android.hipaapix.json.searchvault.EncodedDocument;

public class UploadImageResponse  extends TrueVaultResponse {
	String blob_id, blob_filename, blob_size;

	public String getBlob_id() {
		return blob_id;
	}

	public void setBlob_id(String blob_id) {
		this.blob_id = blob_id;
	}

	public String getBlob_filename() {
		return blob_filename;
	}

	public void setBlob_filename(String blob_filename) {
		this.blob_filename = blob_filename;
	}

	public String getBlob_size() {
		return blob_size;
	}

	public void setBlob_size(String blob_size) {
		this.blob_size = blob_size;
	}

}