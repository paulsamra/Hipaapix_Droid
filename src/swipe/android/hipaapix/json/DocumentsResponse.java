package swipe.android.hipaapix.json;

import java.util.ArrayList;

import swipe.android.hipaapix.APIManager;
import swipe.android.hipaapix.json.searchvault.EncodedDocument;

public class DocumentsResponse extends TrueVaultResponse {
	public DocumentsResponse(String s) {
		EncodedDocument d = new EncodedDocument(s);
		documents = new ArrayList<EncodedDocument>();
		documents.add(d);
		super.result = "success";
	}

	public ArrayList<EncodedDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<EncodedDocument> documents) {
		this.documents = documents;
	}

	ArrayList<EncodedDocument> documents;

}