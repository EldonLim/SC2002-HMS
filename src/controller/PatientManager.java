package controller;

import model.MedicalRecord;

public class PatientManager {

    public static void getMedicalRecord(String patientID) {
        MedicalRecordManager.getMedicalRecord(patientID);
    }
}
