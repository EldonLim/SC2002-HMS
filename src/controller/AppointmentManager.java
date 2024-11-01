package controller;

import database.DataBase;
import helper.Helper;
import model.*;
import using.*;

import java.util.List;
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

        Availability doctor_availability = doctor.getSchedule().getWeeklySlots().get(date).get(timeSlot);
        if (doctor_availability == Availability.NOT_AVAILABLE || doctor_availability == Availability.BOOKED)
            return false;

        Patient patient = (Patient) DataBase.getUsers().get(DataBase.getCurrUserID());;
        Appointment appointment = new Appointment(date, timeSlot, patient, doctor);

        patient.addAppointment(appointment);
        doctor.addAppointment(appointment);
        return true;
    }

    public static boolean viewPatientScheduledAppointments(Patient patient) {
        if (patient.getAppointments().isEmpty()) {
            System.out.println("No Scheduled Appointment");
            return true;
        }

        System.out.println("SCHEDULED APPOINTMENTS");
        for (Appointment appointment : patient.getAppointments())
                viewAppointmentDetail(appointment, Role.PATIENT);
        return false;
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
        if (role == Role.PATIENT)
            System.out.println("Appointment Status: " + appointment.getAppointmentStatus().getLabel());
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
        final boolean[] noPendingAppointment = {true};
        doctor.getAppointments().stream()
                .filter(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.PENDING)
                .forEach(appointment -> {
                    viewAppointmentDetail(appointment, doctor.getRole());
                    noPendingAppointment[0] = false;
                    // Handling multiple patients booked the exact same time slot
                    if (doctor.getSchedule().getWeeklySlots().get(appointment.getDate()).get(appointment.getTimeSlot()) == Availability.BOOKED) {
                        System.out.println("This timeslot is booked by another patient");
                        appointment.setAppointmentStatus(AppointmentStatus.CANCEL);
                        doctor.removeAppointment(appointment);
                    }
                    else {
                        System.out.print("Accept Appointment (y/n): ");
                        char choice = Helper.readChar();
                        if (choice == 'y') {
                            appointment.setAppointmentStatus(AppointmentStatus.CONFIRM);
                            doctor.getSchedule().getWeeklySlots().get(appointment.getDate()).put(appointment.getTimeSlot(),
                                                                      Availability.BOOKED);
                            DoctorManager.addPatientUnderCare(doctor, appointment.getPatient());
                        } else {
                            appointment.setAppointmentStatus(AppointmentStatus.CANCEL);
                            doctor.getAppointments().remove(appointment);
                        }
                    }
                });

        if (noPendingAppointment[0])
            System.out.println("\nNo Pending Appointment");
    }

    public static List<Appointment> getDoctorUpComingAppointments (Doctor doctor) { return doctor.getAppointments(); }

    public static void recordAppointmentOutcome(String appointmentID, String service, String consultationNotes, String medicineName) {
        Doctor doctor = (Doctor) DataBase.getUsers().get(DataBase.getCurrUserID());
        Appointment appointment = doctor.getAppointments().stream().filter(appointment_ -> appointment_.getAppointmentID().equals(appointmentID)).findFirst().orElse(null);

        AppointmentOutcome appointmentOutcome = new AppointmentOutcome(appointment.getDate(), Service.fromString(service), consultationNotes,
                                                                       appointmentID, medicineName, MedicationStatus.PENDING);
        appointment.setAppointmentOutcome(appointmentOutcome);

        // delete from doctor
        doctor.removeAppointment(appointment);
        appointment.getPatient().getMedicalRecord().addAppointmentOutcomes(appointmentOutcome);
    }

    public static void printAppointmentOutcome(AppointmentOutcome appointmentOutcome) {
        System.out.println("Date: " + appointmentOutcome.getDate());
        System.out.println("Service: " + appointmentOutcome.getService());
        System.out.println("Medicine: " + appointmentOutcome.getMedicine());
        System.out.println("Consultation Note: " + appointmentOutcome.getConsultationNotes());
        System.out.println();
    }
}
