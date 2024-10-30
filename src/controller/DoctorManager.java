package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.DocScheduleManager.Availability;
import model.Doctor;
import model.MedicalRecord;
import model.Patient;
import using.Gender;
import using.Role;

public class DoctorManager {
    public static void viewMedicalRecord(String doctorID) {
        int found = 0;

        // an array to store all patients under a specific doctor
        ArrayList<String> specificDocPatient = new ArrayList<String>();

        // loop through the doctorlist to search for matching doctorID as parameter
        // doctorList is non-existence
        for (Doctor doctor : doctorList) {
            // if doctorID matched, input the doctor's patient into the array
            if (doctorID == doctor.getID()) {
                found = 1;
                specificDocPatient.add(doctor.patientID);
            }
        }

        if (found < 1) {
            System.out.println("Doctor ID not found!");
            return;
        }

        // if specifiDocPatient is empty
        if (specificDocPatient == null) {
            System.out.println("There is no patient under this doctor.");
        }
        // loop through specificDocPatient and get all patient's medical record
        else {
            int index = 0;
            for (String patientID : specificDocPatient) {
                ArrayList<MedicalRecord> medRecord = MedicalRecordManager.getMedicalRecord(patientID);

                // print out basic info of the patient
                System.out.println("Medical Record: ");
                System.out.println("------------------");
                System.out.println("Patient Personal Info: ");
                System.out.println("Name: " + medRecord.get(index).getName());
                System.out.println("Patient ID: " + medRecord.get(index).getPatientID());
                System.out.println("Date Of Birth: " + medRecord.get(index).getDateOfBirth());
                System.out.println("Gender: " + medRecord.get(index).getGender());
                System.out.println("Phone Number: " + medRecord.get(index).getPhoneNo());
                System.out.println("Email Address: " + medRecord.get(index).getEmailAddress());
                System.out.println("Blood Type: " + medRecord.get(index).getBloodType());
                index++;

                // print out all past medical records
                for (MedicalRecord medicalRecord : medRecord) {
                    System.out.println("Date of Visit: " + medicalRecord.getDateOfVisit());
                    System.out.println("Diagnosis: " + medicalRecord.getDiagnosis());
                    System.out.println("Treatment Plan: " + medicalRecord.getTreatments());
                }
            }
        }
    }

    public static void updateMedicalRecord(String patientID, Enum data) {
        int found = 0;

        for (Patient patient : patientList) {
            if (patientID == patient.getID()) {
                found = 1;
                // should we add another parameter "date" to know which medical record to
                // update?
                MedicalRecordManager.updateMedicalRecord(patientID, data);
            }
        }

        // successfully updated medical record, print the latest version
        if (found > 0) {
            System.out.println("Sucessfully updated medical record for patient " + patientID + "!");
            System.out.println("Updated medical record:");
            viewMedicalRecord(patientID);
        } else {
            System.out.println("Patient not found!");
        }
    }

    public static void viewPersonalSchedule(String doctorID) {
        for (Doctor doctor : doctorList) {
            if (doctorID == doctor.getID()) {
                System.out.println("Your schedule for the upcoming 7 days:");
                System.out.println(doctor.getSchedule());
                break;
            }
        }
    }

    public static void setAvailability(Doctor doctor) {
        DocScheduleManager.setDoctorAvailability(doctor);
    }

    // Stucked, incomplete method
    // public static void manageAppointmentRequest(Doctor doctor) {
    // List<LocalDateTime> pendingAppointment =
    // AppointmentManager.AppointmentSlots(doctor);

    // System.out.println("These are the current appointment requests:");
    // System.out.println(pendingAppointment);

    // Scanner sc = new Scanner(System.in);

    // while (!isempty(pendingAppointment)) {
    // try {
    // // let doctor choose available date
    // System.out.print(
    // "Enter the date you wish to set/update availability for appointment (Please
    // follow the format of 'YYYY-MM-DD'): ");
    // String dateInput = sc.nextLine();
    // LocalDate date = LocalDate.parse(dateInput, dateFormatter);

    // // let doctor choose available time
    // System.out.print(
    // "Enter the time you wish to set/update availability for appointment (Please
    // follow the format of 'HH:mm'): ");
    // String timeInput = sc.nextLine();
    // LocalTime time = LocalTime.parse(timeInput, timeFormatter);

    // dateTime = LocalDateTime.of(date, time);

    // // check if the date & time is in the correct range
    // if (!isValidDateTime(dateTime)) {
    // throw new Exception("Invalid date!");
    // }
    // break;
    // } catch (Exception e) {
    // if (e.getMessage().contains("Invalid")) {
    // // if date/time not in correct range
    // System.out.println("Invalid date or time. Please try again!");
    // } else {
    // // if date/time not following desired format
    // System.out.println("Incorrect input format. Please try again.");
    // }
    // continue;
    // }
    // }

    // while (true) {
    // // let doctor set availability for the chosen timeslot
    // System.out.print(dateTime + " (A?N)?");
    // char choice = sc.next().charAt(0);
    // try {
    // if (choice == 'A' || choice == 'a' || choice == 'N' || choice == 'n') {
    // if (choice == 'A' || choice == 'a') {
    // doctor.getSchedule().put(dateTime, Availability.AVAILABLE);
    // } else {
    // doctor.getSchedule().put(dateTime, Availability.NOT_AVAILABLE);
    // }
    // }
    // break;
    // } catch (Exception e) {
    // System.out.println("Incorrect input. Please enter 'A' or 'N'.");
    // }
    // }
    // sc.close();
    // }

    // Stucked, incomplete method, need something to store apointment details in
    // database
    // public static void viewUpcomingAppointment(Doctor doctor) {
    // List<LocalDateTime> upcomingAppointment = new ArrayList<>();
    // for (Map.Entry<LocalDateTime, Availability> entry :
    // doctor.getSchedule().entrySet()) {
    // if (entry.getValue() == Availability.CONFIRMED_APPOINTMENT) {
    // upcomingAppointment.add(entry.getKey());
    // }
    // }

    // System.out.println("Upcoming Appointments: ");

    // }

    // patient ID is not used, can remove from parameter
    public static void recordAppointmentOutcome(String patientID, String doctorID, String appointmentID) {
        AppointmentManager.updateAppointmentOutcome(patientID, doctorID, appointmentID);
    }
}
