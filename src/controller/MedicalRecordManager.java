package controller;

import database.DataBase;
import model.MedicalRecord;

public class MedicalRecordManager {

    public MedicalRecordManager() {
    }

    public static MedicalRecord getMedicalRecord(String patientID) {
        int found = 0;
        // go through the patient list to search for the patient ID
        while(!null patientList){
            // patient found, print out his/her basic info first
            if(patientID == patient.getID()){
                found = 1;
                System.out.println("Medical Record: ");
                System.out.println("------------------");
                System.out.println("Patient Personal Info: ");
                System.out.println("Name: " + Database.MedicalRecord.getName());
                System.out.println("Patient ID: " +  Database.MedicalRecord.getPatientID());
                System.out.println("Date Of Birth: " +  Database.MedicalRecord.getDateOfBirth());
                System.out.println("Gender: " +  Database.MedicalRecord.getGender());
                System.out.println("Phone Number: " + Database.MedicalRecord.getPhoneNo());
                System.out.println("Email Address: " +  Database.MedicalRecord.getEmailAddress());
                System.out.println("Blood Type: " +  Database.MedicalRecord.getBloodType());
                break;
            }
            System.out.println("Patient ID not found!");
    }

        // print out all past medical records
        if(found ==1){
            while(!null patientList){
                System.out.println("Date of Visit: " + Database.MedicalRecord.getDateOfVisit);
                System.out.println("Diagnosis: " + Database.MedicalRecord.getDiagnosis());
                System.out.println("Treatment Plan: " +  Database.MedicalRecord.getTreatments());
            }
        }
    }

    public static String getTreatments(String patientID) {
        return DataBase.MedicalRecords.getTreatments;
    }

    public static String getDiagnosis(String patientID) {
        return DataBase.MedicalRecords.getDiagnosis;
    }

    public static void addTreatment(String treatment) {
        Database.MedicalRecord.addTreatment(treatment);
    }

    public static void addDiagnosis(String diagnosis) {
        Database.MedicalRecord.addDiagnosis(diagnosis);
    }

    public static updateMedicalRecord(String patientID, Enum data){
        // go through the patient list to search for the patient ID
        while(!null patientList){
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
