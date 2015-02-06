package swipe.android.hipaapix.json.patientsResponse;

import java.util.ArrayList;

import swipe.android.hipaapix.classes.patients.Patient;

import com.edbert.library.network.sync.JsonResponseInterface;

public class PatientsResultsResponse implements JsonResponseInterface{

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
	// temporary
	public ArrayList<Patient> getPatients() {
		ArrayList<Patient> patients = new ArrayList<Patient>();
		for (int i = 0; i < 5; i++) {
			patients.add(new Patient(
					"http://pldh.net/media/dreamworld/196.png", "Bob",
					"05/29/1969", "cateogry"));
		}
		return patients;
	}

}