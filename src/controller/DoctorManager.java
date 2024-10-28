package controller;

import java.util.ArrayList;

import model.Patient;

public class DoctorManager {
    public void viewMedicalRecord(String doctorID) {
        int found = 0;

        // an array to store all patients under a specific doctor
        ArrayList<String> specificDocPatient = new ArrayList<String>();

        // loop through the doctorlist to search for matching doctorID as parameter
        for (Doctor doctor : doctorList) {
            // if doctorID matched, input the doctor's patient into the array
            if (doctorID == doctor.getID()) {
                found = 1;
                specificDocPatient.add(doctor.patientID);
            }
        }

        if (found < 1) {
            System.out.println("Doctor ID not found!");
            return;
        }

        // if specifiDocPatient is empty
        if (specificDocPatient == null) {
            System.out.println("There is no patient under this doctor.");
        }
        // loop through specificDocPatient and get all patient's medical record
        else {
            for (String patientID : specificDocPatient) {
                MedicalRecordManager.getMedicalRecord(patientID);
            }
        }
    }

}
