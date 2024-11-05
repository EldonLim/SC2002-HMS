package controller;

import database.DataBase;
import helper.Helper;
import model.Appointment;
import model.AppointmentOutcome;
import model.Doctor;
import model.Patient;
import using.*;

import java.util.Iterator;
import java.util.List;

public class AppointmentManager {

    public AppointmentManager() {
    }

    public static boolean scheduleAppointment(Doctor doctor, String date, int timeSlot) {
        Availability doctor_availability = doctor.getSchedule().getWeeklySlots().get(date).get(timeSlot);

        if (doctor_availability == Availability.NOT_AVAILABLE || doctor_availability == Availability.BOOKED)
            return false;

        Patient patient = (Patient) DataBase.getUsers().get(DataBase.getCurrentUserID());
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
            if ((appointment_.getAppointmentID().equals(appointmentID)) && appointment_.getAppointmentStatus() != AppointmentStatus.COMPLETED) {
                appointment = appointment_;
                break;
            }

        appointment.setAppointmentID(patient.getID() + date.replace("/", "") + timeSlot);
        appointment.setDate(date);
        appointment.setTimeSlot(timeSlot);
        appointment.setAppointmentStatus(AppointmentStatus.PENDING);

        appointment.getDoctor().getSchedule().setAvailabilityForParticularDate_Time(date, timeSlot, Availability.AVAILABLE);
    }

    public static void viewAppointmentDetail(Appointment appointment, Role role) {
        System.out.println("Appointment ID: " + appointment.getAppointmentID());
        System.out.println("Date: " + appointment.getDate());
        System.out.printf("Time Slot: %2d:00 - %2d:00\n", appointment.getTimeSlot(), appointment.getTimeSlot() + 1);

        if (role == Role.ADMINISTRATOR) {
            System.out.println("Doctor: " + appointment.getDoctor().getName());
            System.out.println("Patient: " + appointment.getPatient().getName());
            System.out.println("Appointment Status: " + appointment.getAppointmentStatus().getLabel());
            if (appointment.getAppointmentOutcome() != null && appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
                System.out.println("Service: " + appointment.getAppointmentOutcome().getService().getLabel());
                System.out.println("Medicine: " + appointment.getAppointmentOutcome().getMedicine());
                System.out.println("Medication Status: " + appointment.getAppointmentOutcome().getMedicationStatus().getLabel());
                System.out.println("Consultation Note: " + appointment.getAppointmentOutcome().getConsultationNotes());
            }
        } else if (role == Role.DOCTOR) System.out.println("Patient: " + appointment.getPatient().getName());
        else if (role == Role.PATIENT) {
            System.out.println("Doctor: " + appointment.getDoctor().getName());
            System.out.println("Appointment Status: " + appointment.getAppointmentStatus().getLabel());
        }
        Helper.pauseApplication();
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

        Iterator<Appointment> iterator = doctor.getAppointments().iterator();

        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();

            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING) {
                viewAppointmentDetail(appointment, doctor.getRole());
                noPendingAppointment[0] = false;

                if (doctor.getSchedule().getWeeklySlots().get(appointment.getDate()).get(appointment.getTimeSlot()) == Availability.BOOKED) {
                    System.out.println("This timeslot is booked by another patient");
                    appointment.setAppointmentStatus(AppointmentStatus.CANCEL);
                    iterator.remove();
                } else {
                    System.out.print("Accept Appointment (y/n): ");
                    char choice = Helper.readChar();
                    if (choice == 'y') {
                        appointment.setAppointmentStatus(AppointmentStatus.CONFIRM);
                        doctor.getSchedule().getWeeklySlots().get(appointment.getDate()).put(appointment.getTimeSlot(), Availability.BOOKED);
                        DoctorManager.addPatientUnderCare(doctor, appointment.getPatient());
                    } else {
                        appointment.setAppointmentStatus(AppointmentStatus.CANCEL);
                        iterator.remove();
                    }
                }
            }
        }

        if (noPendingAppointment[0])
            System.out.println("\nNo Pending Appointment");
    }

    public static List<Appointment> getDoctorUpComingAppointments(Doctor doctor) {
        return doctor.getAppointments();
    }

    public static void recordAppointmentOutcome(String appointmentID, String service, String consultationNotes, String medicineName) {
        Doctor doctor = (Doctor) DataBase.getUsers().get(DataBase.getCurrentUserID());
        Appointment appointment = doctor.getAppointments().stream().filter(appointment_ -> appointment_.getAppointmentID().equals(appointmentID)).findFirst().orElse(null);

        AppointmentOutcome appointmentOutcome = new AppointmentOutcome(appointment.getDate(), Service.fromString(service), consultationNotes,
                appointmentID, medicineName, MedicationStatus.PENDING);
        appointment.setAppointmentOutcome(appointmentOutcome);

        // delete from doctor
        doctor.removeAppointment(appointment);
        appointment.getPatient().getMedicalRecord().addAppointmentOutcomes(appointmentOutcome, appointmentID);
    }

    public static void printAppointmentOutcome(AppointmentOutcome appointmentOutcome) {
        System.out.println("Date: " + appointmentOutcome.getDate());
        System.out.println("Service: " + appointmentOutcome.getService());
        System.out.println("Medicine: " + appointmentOutcome.getMedicine());
        System.out.println("Consultation Note: " + appointmentOutcome.getConsultationNotes());
    }

}
