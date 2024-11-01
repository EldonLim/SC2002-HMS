package controller;

import database.DataBase;
import helper.Helper;
import model.Appointment;
import model.Doctor;
import model.Patient;
import model.User;
import using.AppointmentStatus;
import using.Availability;
import using.Role;

import java.util.Map;

public class AppointmentManager {

    public AppointmentManager() {}

    public static boolean scheduleAppointment(String doctorName, String date, int timeSlot) {
        Doctor doctor = null;
        for (Map.Entry<String, User> entry : DataBase.getUsers().entrySet())
            if (entry.getValue() instanceof Doctor)
                if (entry.getValue().getName().equals(doctorName)) {
                    doctor = (Doctor) entry.getValue();
                    break;
                }

        if (doctor.getSchedule().getWeeklySlots().get(date).get(timeSlot).equals(Availability.NOT_AVAILABLE))
            return false;

        Patient patient = (Patient) DataBase.getUsers().get(DataBase.getCurrUserID());;
        Appointment appointment = new Appointment(date, timeSlot, patient, doctor);

        patient.addAppointment(appointment);
        doctor.addAppointment(appointment);
        return true;
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

    public static void viewAppointmentDetail(Appointment appointment, Role role) {
        System.out.println("Appointment ID: " + appointment.getAppointmentID());
        System.out.println("Date: " + appointment.getDate());
        System.out.printf("Time Slot: %2d:00 - %2d:00\n", appointment.getTimeSlot(), appointment.getTimeSlot() + 1);
        System.out.println(role == Role.DOCTOR? "Patient: " + appointment.getPatient().getName() : "Doctor: " + appointment.getDoctor().getName());
        System.out.println();
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
        if (doctor.getPatientList().contains(patient))
            DoctorManager.removePatientUnderCare(doctor, patient);

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

    public static void handleDoctorAppointmentRequest(Doctor doctor) {
        boolean existsPendingAppointment = false;
        for (Appointment appointment : doctor.getAppointments())
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING) {
                existsPendingAppointment = true;
                viewAppointmentDetail(appointment, doctor.getRole());
                System.out.print("Accept Appointment (y/n): ");
                char choice = Helper.readChar();

                if (choice == 'y') {
                    appointment.setAppointmentStatus(AppointmentStatus.CONFIRM);
                    doctor.getSchedule().getWeeklySlots().get(appointment.getDate()).put(appointment.getTimeSlot(), Availability.NOT_AVAILABLE);
                    DoctorManager.addPatientUnderCare(doctor, appointment.getPatient());
                }
                else {
                    appointment.setAppointmentStatus(AppointmentStatus.CANCEL);
                    doctor.getAppointments().remove(appointment);
                }
            }

        if (!existsPendingAppointment)
            System.out.println("No Pending Appointment");
    }
}
