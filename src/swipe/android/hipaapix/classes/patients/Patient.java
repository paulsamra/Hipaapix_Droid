package swipe.android.hipaapix.classes.patients;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Patient implements Parcelable {
	public String getPatient_url() {
		return patient_url;
	}

	public void setPatient_url(String patient_url) {
		this.patient_url = patient_url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	String patient_url, name, birthdate, category;

	String patient_id;
	public String getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Patient(String patient_url, String name, String birthdate,
			String category, String blob_url) {
		super();
		this.patient_url = patient_url;
		this.name = name;
		this.birthdate = birthdate;
		this.category = category;
		this.blob_url = blob_url;
		this.patient_id = "";
	}
	public Patient(String patient_url, String name, String birthdate,
			String category, String blob_url, String patient_id) {
		super();
		this.patient_url = patient_url;
		this.name = name;
		this.birthdate = birthdate;
		this.category = category;
		this.blob_url = blob_url;
		this.patient_id = patient_id;
	}
	public String getBlob_url() {
		return blob_url;
	}

	public void setBlob_url(String blob_url) {
		this.blob_url = blob_url;
	}

	String blob_url;
String notes;
	public String getNotes() {
	return notes;
}

public void setNotes(String notes) {
	this.notes = notes;
}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] { this.patient_url, this.name,
				this.birthdate, this.category, this.blob_url,this.patient_id, this.notes });

	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Patient createFromParcel(Parcel in) {
			return new Patient(in);
		}

		public Patient[] newArray(int size) {
			return new Patient[size];
		}
	};

	public Patient(Parcel in) {

		String[] data = new String[7];

		in.readStringArray(data);

		patient_url = data[0];
		name = data[1];
		birthdate = data[2];
		category = data[3];
		blob_url = data[4];
		patient_id = data[5];
		notes = data[6];
	}
}