package controller;

import database.DataBase;
import model.MedicalRecord;
import model.Patient;

public class PatientManager {

    public static void getMedicalRecord(String patientID) {
        MedicalRecordManager.getMedicalRecord(patientID);
    }

    public static void updatePersonalInformation(String emailAddress, String phoneNo, String patientID) {
        Patient patient = (Patient) DataBase.getUsers().get(patientID);

        patient.setEmailAddress(emailAddress);
        patient.setPhoneNo(phoneNo);

        System.out.println("Update Successful!!");
        System.out.println();
    }
}
