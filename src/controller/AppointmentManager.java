package controller;

import java.security.Provider.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.DocScheduleManager.Availability;
import model.Appointment;
import model.AppointmentOutcome;
import model.Doctor;

public class AppointmentManager {
    public static List<LocalDateTime> AppointmentSlots(Doctor doctor) {
        List<LocalDateTime> appointmentSlots = new ArrayList<>();
        for (Map.Entry<LocalDateTime, Availability> entry : doctor.getSchedule().entrySet()) {
            if (entry.getValue() == Availability.PENDING_APPOINTMENT) {
                appointmentSlots.add(entry.getKey());
            }
        }
        return appointmentSlots;
    }

    // patient ID is not used, can remove from parameter
    public static void updateAppointmentOutcome(String patientID, String doctorID, String appointmentID) {
        int found = 0;
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        AppointmentOutcome appointmentOutcome;

        // loop through the list of appointments and find matching appointmentID
        // appointmentList is non-existence
        for (Appointment appointment : appointmentList) {
            if (appointmentID == appointment.getAppointmentID) {
                appointmentOutcome = appointment.getAppointmentOutcome();
                found = 1;
                break;
            }
        }

        if (found < 1) {
            System.out.println("Appointment ID not found!");
        }

        // appoinment ID found
        else {
            String dateInput;
            // ask doctor to enter appointment date following the desired format
            while (true) {
                try {
                    System.out.println("What is the date of appointment? (YYYY-MM-DD)");
                    dateInput = sc.nextLine();
                    LocalDate date = LocalDate.parse(dateInput, dateFormatter);
                } catch (Exception e) {
                    System.out.println("Incorrect input format. Please try again.");
                }
            }

            appointmentOutcome.setDate(dateInput);

            // ask doctor to choose type of services provided
            int choice;
            do {
                System.out.println("Choose the type of services provided to the patient");
                System.out.println("1. Consultation");
                System.out.println("2. X-Ray");
                System.out.println("3. Blood Test");
                System.out.println("4. Other");
                System.out.println("5. Exit");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        appointmentOutcome.setService(using.Service.CONSULTATION);
                        break;

                    case 2:
                        appointmentOutcome.setService(using.Service.XRAY);
                        break;

                    case 3:
                        appointmentOutcome.setService(using.Service.BLOOD_TEST);
                        break;

                    case 4:
                        appointmentOutcome.setService(using.Service.OTHER);
                        break;

                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            } while (choice < 5);
        }

        // aks doctor to write consultation notes
        System.out.println("Enter your consultation notes:");
        String notes = sc.nextLine();
        appointmentOutcome.setConsultationNotes(notes);

        // set appointmentOutcome ID
        appointmentOutcome.setAppointmentOutcomeID(appointmentID);
    }
}
