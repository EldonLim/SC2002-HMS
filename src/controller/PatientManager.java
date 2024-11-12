package controller;

import database.DataBase;
import model.Patient;

/**
 * Manages patient-related operations, including retrieving medical records and updating personal information.
 *
 * @author Chin Linn Sheng
 * @version 4.5
 * @since 2024-10-28
 */
public class PatientManager {

    /**
     * Constructs a PatientManager instance.
     */
    public PatientManager() {
    }

    /**
     * Retrieves and prints the medical record of a specified patient.
     * Also prints any appointment outcomes associated with the patient's medical record, if available.
     *
     * @param patientID the ID of the patient whose medical record is to be retrieved
     */
    public static void getMedicalRecord(String patientID) {
        MedicalRecordManager.printMedicalRecord(patientID);
        if (!((Patient) DataBase.getUsers().get(DataBase.getCurrentUserID())).getMedicalRecord().getAppointmentOutcomes().isEmpty())
            ((Patient) DataBase.getUsers().get(DataBase.getCurrentUserID())).getMedicalRecord().getAppointmentOutcomes().values().forEach(AppointmentManager::printAppointmentOutcome);
    }

    /**
     * Updates the personal information (email address and phone number) of a specified patient.
     *
     * @param emailAddress the new email address of the patient
     * @param phoneNo      the new phone number of the patient
     * @param patientID    the ID of the patient whose information is to be updated
     */
    public static void updatePersonalInformation(String emailAddress, String phoneNo, String patientID) {
        Patient patient = (Patient) DataBase.getUsers().get(patientID);
        patient.setEmailAddress(emailAddress);
        patient.setPhoneNo(phoneNo);

        System.out.println("Update Successful!!");
    }
}
