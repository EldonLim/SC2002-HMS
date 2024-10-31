package controller;

import database.DataBase;
import model.Doctor;
import model.MedicalRecord;
import model.Patient;
import model.User;

import javax.print.Doc;

public class DoctorManager {

    public DoctorManager() {}

    public static void printAllAvailableSlots() {
        DataBase.getUsers().values().stream()
                .filter(user -> user instanceof Doctor)
                .map(user -> (Doctor) user)
                .forEach(doctor -> ScheduleManager.printDoctorSchedule(doctor.getID()));

    }

    public static void viewMedicalRecord(String doctorID) {
        Doctor doctor = (Doctor) DataBase.getUsers().get(doctorID);

        for (Patient patient : doctor.getPatientList())
            MedicalRecordManager.getMedicalRecord(patient.getID());

    }

    public static void viewPersonalSchedule(String doctorID) {
        ScheduleManager.printPersonalSchedule(doctorID);
    }
}
