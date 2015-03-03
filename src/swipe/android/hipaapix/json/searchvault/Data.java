package swipe.android.hipaapix.json.searchvault;

import java.util.ArrayList;

public class Data {
	public Data(String s) {
		documents = new ArrayList<EncodedDocument>();
		documents.add(new EncodedDocument(s));
	}

	public ArrayList<EncodedDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<EncodedDocument> documents) {
		this.documents = documents;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	ArrayList<EncodedDocument> documents;
	Info info;
}