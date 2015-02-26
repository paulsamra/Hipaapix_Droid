package swipe.android.hipaapix.json.searchvault;

import java.util.ArrayList;

public class Data{
	public ArrayList<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	ArrayList<Document> documents;
	Info info;
}