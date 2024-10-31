package controller;

import database.DataBase;
import helper.Helper;
import model.Doctor;
import model.MedicalRecord;
import model.Patient;
import model.User;
import using.Availability;

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

    public static void setAvailability(Doctor doctor) {
        do {
            viewPersonalSchedule(doctor.getID());
            System.out.print("Enter the date: ");
            String date = Helper.readString();
            System.out.print("Enter the time: ");
            int timeSlot = Helper.readInt();

            doctor.getSchedule().setAvailabilityForParticularDate_Time(date, timeSlot, Availability.NOT_AVAILABLE);

            System.out.print("Set more slot (y/n): ");
            char choice = Helper.readChar();

            if (choice == 'n') break;

        } while (true);
    }
}
