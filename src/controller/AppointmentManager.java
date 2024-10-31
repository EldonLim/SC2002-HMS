package controller;

import database.DataBase;
import model.Appointment;
import model.Doctor;
import model.Patient;
import model.User;
import using.AppointmentStatus;

import java.util.Map;

public class AppointmentManager {

    public AppointmentManager() {}

    public static void scheduleAppointment(String doctorName, String date, int timeSlot) {
        Doctor doctor = null;
        for (Map.Entry<String, User> entry : DataBase.getUsers().entrySet())
            if (entry.getValue() instanceof Doctor)
                if (entry.getValue().getName().equals(doctorName)) {
                    doctor = (Doctor) entry.getValue();
                    break;
                }
        Patient patient = (Patient) DataBase.getUsers().get(DataBase.getCurrUserID());;
        Appointment appointment = new Appointment(date, timeSlot, patient, doctor);

        patient.addAppointment(appointment);
        doctor.addAppointment(appointment);
    }

    public static void viewPatientScheduledAppointments(Patient patient) {
        for (Appointment appointment : patient.getAppointments())
            if (appointment.getAppointmentStatus() != AppointmentStatus.COMPLETED) {
                System.out.println("Appointment ID: " + appointment.getAppointmentID());
                System.out.println("Date: " + appointment.getDate());
                System.out.printf("Time Slot: 2%d:00 - 2%d:00\n", appointment.getTimeSlot(), appointment.getTimeSlot() + 1);
                System.out.println("Doctor: " + appointment.getDoctor().getName());
                System.out.println("Appointment Status: " + appointment.getAppointmentStatus().getLabel());
                System.out.println();
            }
    }
}
