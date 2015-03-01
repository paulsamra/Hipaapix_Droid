package swipe.android.hipaapix.json;

import java.util.ArrayList;

import swipe.android.hipaapix.json.searchvault.EncodedDocument;

public class DocumentsResponse extends TrueVaultResponse {

	public ArrayList<EncodedDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<EncodedDocument> documents) {
		this.documents = documents;
	}

	ArrayList<EncodedDocument> documents;


}