package controller;

import database.DataBase;
import helper.Helper;
import model.Appointment;
import model.AppointmentOutcome;
import model.Doctor;
import model.Patient;
import using.Availability;

import java.util.List;

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
        if (doctor.getPatientList().isEmpty()) {
            System.out.println("\nNo Patient Under Your Care");
            System.out.println();
            return;
        }
        for (Patient patient : doctor.getPatientList()) {
            MedicalRecordManager.printMedicalRecord(patient.getID());
            if (!patient.getMedicalRecord().getAppointmentOutcomes().isEmpty())
                patient.getMedicalRecord().getAppointmentOutcomes().values().forEach(AppointmentManager::printAppointmentOutcome);
        }
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

            System.out.println();
        } while (true);
    }

    public static void handleAppointmentRequest(Doctor doctor) { AppointmentManager.handleDoctorAppointmentRequest(doctor); }
    public static void addPatientUnderCare(Doctor doctor, Patient patient) { doctor.addPatient(patient); }
    public static void removePatientUnderCare(Doctor doctor, Patient patient) { doctor.removePatient(patient); }
    public static List<Patient> getAllPatientUnderCare(Doctor doctor) { return doctor.getPatientList(); }
    public static void handleUpdateMedicalRecord(Patient patient, String diagnosis, String treatment) { MedicalRecordManager.updateMedicalRecord(patient, diagnosis, treatment);}
    public static List<Appointment> handleGetDoctorUpComingAppointment(Doctor doctor) { return AppointmentManager.getDoctorUpComingAppointments(doctor); }

}
