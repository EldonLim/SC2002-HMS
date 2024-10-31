package controller;

import database.DataBase;
import model.Appointment;
import model.Doctor;
import model.Patient;
import model.User;
import using.AppointmentStatus;
import using.Availability;

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
        if (patient.getAppointments().isEmpty()) {
            System.out.println("No Scheduled Appointment");
            return;
        }

        System.out.println("SCHEDULED APPOINTMENTS");
        for (Appointment appointment : patient.getAppointments()) {
                System.out.println("Appointment ID: " + appointment.getAppointmentID());
                System.out.println("Date: " + appointment.getDate());
                System.out.printf("Time Slot: %2d:00 - %2d:00\n", appointment.getTimeSlot(), appointment.getTimeSlot() + 1);
                System.out.println("Doctor: " + appointment.getDoctor().getName());
                System.out.println("Appointment Status: " + appointment.getAppointmentStatus().getLabel());
                System.out.println();
            }
    }

    public static void rescheduleAppointment(Patient patient, String appointmentID, String date, int timeSlot) {
       Appointment appointment = null;

       for (Appointment appointment_ : patient.getAppointments())
           if ((appointment_.getAppointmentID().equals(appointmentID)) && appointment_.getAppointmentStatus() != AppointmentStatus.COMPLETED){
               appointment = appointment_;
               break;
           }

       appointment.setAppointmentID(patient.getID() + date.replace("/", "") + Integer.toString(timeSlot));
       appointment.setDate(date);
       appointment.setTimeSlot(timeSlot);
       appointment.setAppointmentStatus(AppointmentStatus.PENDING);

       appointment.getDoctor().getSchedule().setAvailabilityForParticularDate_Time(date, timeSlot, Availability.AVAILABLE);
    }

    public static void cancelAppointment(Patient patient, String appointmentID) {
        Appointment appointment = null;

        for (Appointment appointment_ : patient.getAppointments())
            if (appointment_.getAppointmentID().equals(appointmentID) && appointment_.getAppointmentStatus() != AppointmentStatus.COMPLETED) {
                appointment = appointment_;
                break;
            }

        Doctor doctor = appointment.getDoctor();
        doctor.getSchedule().setAvailabilityForParticularDate_Time(appointment.getDate(), appointment.getTimeSlot(), Availability.AVAILABLE);

        doctor.getAppointments().remove(appointment);
        patient.getAppointments().remove(appointment);
    }

    public static void cancel_viewPatientScheduledAppointments(Patient patient) {
        for (Appointment appointment : patient.getAppointments())
            if (appointment.getAppointmentStatus() != AppointmentStatus.CANCEL && appointment.getAppointmentStatus() != AppointmentStatus.COMPLETED) {
                System.out.println("Appointment ID: " + appointment.getAppointmentID());
                System.out.println("Date: " + appointment.getDate());
                System.out.printf("Time Slot: %2d:00 - %2d:00\n", appointment.getTimeSlot(), appointment.getTimeSlot() + 1);
                System.out.println("Doctor: " + appointment.getDoctor().getName());
                System.out.println("Appointment Status: " + appointment.getAppointmentStatus().getLabel());
                System.out.println();
            }
    }
}
