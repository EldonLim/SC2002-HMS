package controller;

import java.util.ArrayList;

import model.Doctor;
import model.MedicalRecord;
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
            int index = 0;
            for (String patientID : specificDocPatient) {
                ArrayList<MedicalRecord> medRecord = MedicalRecordManager.getMedicalRecord(patientID);

                // print out basic info of the patient
                System.out.println("Medical Record: ");
                System.out.println("------------------");
                System.out.println("Patient Personal Info: ");
                System.out.println("Name: " + medRecord.get(index).getName());
                System.out.println("Patient ID: " + medRecord.get(index).getPatientID());
                System.out.println("Date Of Birth: " + medRecord.get(index).getDateOfBirth());
                System.out.println("Gender: " + medRecord.get(index).getGender());
                System.out.println("Phone Number: " + medRecord.get(index).getPhoneNo());
                System.out.println("Email Address: " + medRecord.get(index).getEmailAddress());
                System.out.println("Blood Type: " + medRecord.get(index).getBloodType());
                index++;

                // print out all past medical records
                for (MedicalRecord medicalRecord: medRecord) {
                    System.out.println("Date of Visit: " + medicalRecord.getDateOfVisit());
                    System.out.println("Diagnosis: " + medicalRecord.getDiagnosis());
                    System.out.println("Treatment Plan: " + medicalRecord.getTreatments());
                }
            }
        }
    }

    public void
}
