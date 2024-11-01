package controller;

import database.DataBase;
import model.MedicalRecord;
import model.Patient;

public class MedicalRecordManager {

    public MedicalRecordManager() {}

    public static void printMedicalRecord(String patientID) {
        MedicalRecord medicalRecord = DataBase.getMedicalRecords().get(patientID);

        if (medicalRecord == null) {
            System.out.println("No record of this patient");
            return;
        }

        Patient patient = medicalRecord.getPatient();
        System.out.println("Medical Record");
        System.out.println("-----------------------");
        System.out.println("Name: " + patient.getName());
        System.out.println("Patient ID: " + patient.getID());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Gender: " + patient.getGender().getLabel());
        System.out.println("Phone Number: " + patient.getPhoneNo());
        System.out.println("Email Address: " + patient.getEmailAddress());
        System.out.println("Blood Type: " + patient.getBloodType().getLabel());
        if (!medicalRecord.getDiagnosis().isEmpty()) {
            String concat_diagnosis = String.join(", ", medicalRecord.getDiagnosis());
            System.out.println(concat_diagnosis);
        }
        if (!medicalRecord.getTreatments().isEmpty()) {
            String concat_treatments = String.join(", ", medicalRecord.getTreatments());
            System.out.println(concat_treatments);
        }

        System.out.println();
    }

    public static void updateMedicalRecord(Patient patient, String diagnosis_, String treatment_) {
        patient.getMedicalRecord().addDiagnosis(diagnosis_);
        patient.getMedicalRecord().addTreatment(treatment_);
    }

}
