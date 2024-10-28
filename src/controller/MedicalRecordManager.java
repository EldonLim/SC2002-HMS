package controller;

import java.util.ArrayList;

import database.DataBase;
import model.MedicalRecord;

public class MedicalRecordManager {

    public MedicalRecordManager() {
    }

    public static ArrayList<MedicalRecord> getMedicalRecord(String patientID) {
        int found = 0;
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();

        // go through the patient list to search for the patient ID
        for (Patient patient : patientList) {
            // patient found, add his/her medical record into the array storing all his/her
            // medical records
            if (patientID == patient.getID()) {
                found = 1;

                medicalRecords.add(patient.getMedicalRecord());
            }
        }

        if (found < 1) {
            System.out.println("Patient ID not found!");
        }
    }

    public static String getTreatments(String patientID) {
        return DataBase.MedicalRecords.getTreatments();
    }

    public static String getDiagnosis(String patientID) {
        return DataBase.MedicalRecords.getDiagnosis();
    }

    public static void addTreatment(String treatment) {
        Database.MedicalRecord.addTreatment(treatment);
    }

    public static void addDiagnosis(String diagnosis) {
        Database.MedicalRecord.addDiagnosis(diagnosis);
    }

    public static updateMedicalRecord(String patientID, Enum data){
        // go through the patient list to search for the patient ID
        for(Patient patient: patientList){
            if(patientID == patient.getID()){
                // if user choose to add diagnosis
                if(data == diagnosis){
                    addDiagnosis(diagnosis);
                    System.out.println("Successfully added diagnosis to patient " + patientID);
                }
                // if user choose to add treatment
                else{
                    addTreatment(treatment);
                    System.out.println("Successfully added treatment plan to patient " + patientID);
                }
            }
        }
        System.out.println("Patient ID not found!");
    }

}
