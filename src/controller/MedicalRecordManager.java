package controller;

import database.DataBase;
import model.MedicalRecord;
import model.Patient;

/**
 * Manages the operations related to medical records, including printing and updating records.
 *
 * @author Chin Linn Sheng
 * @version 4.5
 * @since 2024-10-28
 */
public class MedicalRecordManager {

    /**
     * Constructs a new MedicalRecordManager.
     */
    public MedicalRecordManager() {
    }

    /**
     * Prints the medical record of a specified patient.
     * If the medical record does not exist, a message is displayed.
     *
     * @param patientID the ID of the patient whose medical record is to be printed
     */
    public static void printMedicalRecord(String patientID) {
        MedicalRecord medicalRecord = ((Patient) DataBase.getUsers().get(patientID)).getMedicalRecord();
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
        System.out.println("Phone Number: " + (patient.getPhoneNo().isEmpty() ? "N/A" : patient.getPhoneNo()));
        System.out.println("Email Address: " + (patient.getEmailAddress().isEmpty() ? "N/A" : patient.getEmailAddress()));
        System.out.println("Blood Type: " + patient.getBloodType().getLabel());
        System.out.println("Diagnosis: " + ((medicalRecord.getDiagnosis().isEmpty()) ? "N/A" : String.join(", ", medicalRecord.getDiagnosis())));
        System.out.println("Treatments: " + ((medicalRecord.getTreatments().isEmpty()) ? "N/A" : String.join(", ", medicalRecord.getTreatments())));
    }

    /**
     * Updates the medical record of a patient by adding a new diagnosis and treatment.
     *
     * @param patient   the patient whose medical record is to be updated
     * @param diagnosis the diagnosis to be added to the patient's record
     * @param treatment the treatment to be added to the patient's record
     */
    public static void updateMedicalRecord(Patient patient, String diagnosis, String treatment) {
        patient.getMedicalRecord().addDiagnosis(diagnosis);
        patient.getMedicalRecord().addTreatment(treatment);
    }
}
