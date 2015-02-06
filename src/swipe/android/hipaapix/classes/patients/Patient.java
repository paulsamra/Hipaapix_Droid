package swipe.android.hipaapix.classes.patients;

import java.util.ArrayList;

public class Patient {
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public Patient(String patient_url, String name, String birthdate,
			String category) {
		super();
		this.patient_url = patient_url;
		this.name = name;
		this.birthdate = birthdate;
		this.category = category;
	}
}