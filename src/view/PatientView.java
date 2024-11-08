package view;

import controller.AppointmentManager;
import controller.DoctorManager;
import controller.PatientManager;
import database.DataBase;
import helper.Helper;
import model.AppointmentOutcome;
import model.Doctor;
import model.Patient;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class PatientView implements View {

    public PatientView() {
    }

    public static void handleUpdatePersonalInfo() {
        System.out.println("UPDATE PERSONAL INFORMATION");
        System.out.println("Please key in the latest PhoneNo & EmailAddress");
        System.out.print("Email Address: ");
        String updateEmailAddress = Helper.readString();
        System.out.print("Phone No: ");
        String updatePhoneNo = Helper.readString();

        PatientManager.updatePersonalInformation(updateEmailAddress, updatePhoneNo, DataBase.getCurrentUserID());
    }

    public static void handleViewAvailableAppointmentSlots() {
        System.out.println("VIEW DOCTORS' AVAILABLE SLOTS");
        DoctorManager.printAllAvailableSlots();
    }

    public static void handleScheduleAnAppointment() {
        System.out.println("SCHEDULE APPOINTMENT");
        boolean bookappointment = false;
        do {
            handleViewAvailableAppointmentSlots();
            System.out.println("Each Time Slot is 1 Hour");
            System.out.print("Please Enter Doctor Name: ");
            String doctorName = Helper.readString();

            Doctor doctor = null;
            for (Map.Entry<String, User> entry : DataBase.getUsers().entrySet())
                if (entry.getValue() instanceof Doctor)
                    if (entry.getValue().getName().equals(doctorName)) {
                        doctor = (Doctor) entry.getValue();
                        break;
                    }

            if (doctor == null) {
                System.out.println("Invalid Doctor Name");
                Helper.pauseApplication();
                continue;
            }

            System.out.print("Please Enter the Date (dd/mm/yy): ");
            String date = Helper.readString();
            System.out.print("Please Enter the Slot in 24Hour Format (13 stands for 1pm): ");
            int timeSlot = Helper.readInt();

            if (!doctor.getSchedule().getWeeklySlots().containsKey(date) || !doctor.getSchedule().getWeeklySlots().get(date).containsKey(timeSlot)) {
                System.out.println("Invalid Date or Time Slot");
                Helper.pauseApplication();
                continue;
            }

            bookappointment = AppointmentManager.scheduleAppointment(doctor, date, timeSlot);
            if (!bookappointment) {
                System.out.println();
                System.out.println("Doctor is not available at that time");
                System.out.println("Please another appointment according to the schedule");
                Helper.pauseApplication();
            }
        } while (!bookappointment);
        System.out.println("Appointment Schedule Successfully");
    }

    public static void handleCancelAppointment() {
        System.out.println("CANCEL APPOINTMENT");
        AppointmentManager.cancel_viewPatientScheduledAppointments((Patient) DataBase.getUsers().get(DataBase.getCurrentUserID()));
        System.out.print("Please Enter the Appointment ID: ");
        String appointmentID = Helper.readString();

        AppointmentManager.cancelAppointment((Patient) DataBase.getUsers().get(DataBase.getCurrentUserID()),
                appointmentID);
        System.out.println("Appointment Cancel Successfully");
    }

    public static void handleRescheduleAppointment() {
        System.out.println("RESCHEDULE APPOINTMENT");
        if (AppointmentManager.viewPatientScheduledAppointments((Patient) DataBase.getUsers().get(DataBase.getCurrentUserID())))
            return;

        String appointmentID;
        while (true) {
            System.out.print("Please Enter the Appointment ID: ");
            appointmentID = Helper.readString();

            // Check if appointment ID exists
            String finalAppointmentID = appointmentID;
            if (((Patient) DataBase.getUsers().get(DataBase.getCurrentUserID())).getAppointments().stream().anyMatch(appointment -> appointment.getAppointmentID().equals(finalAppointmentID)))
                break;

            System.out.println("Invalid appointment ID. Please try again.");
        }

        handleViewAvailableAppointmentSlots();

        String date;
        int timeSlot;
        while (true) {
            System.out.print("Please Enter the Date (dd/mm/yy): ");
            date = Helper.readString();
            System.out.print("Please Enter the Slot in 24Hour Format (13 stands for 1pm): ");
            timeSlot = Helper.readInt();

            if (((Patient) DataBase.getUsers().get(DataBase.getCurrentUserID())).getAppointments().getFirst().getDoctor().getSchedule().getWeeklySlots().containsKey(date) &&
                ((Patient) DataBase.getUsers().get(DataBase.getCurrentUserID())).getAppointments().getFirst().getDoctor().getSchedule().getWeeklySlots().containsValue(timeSlot))
                break;

            System.out.println("Invalid Date or Time Slot");
        }

        AppointmentManager.rescheduleAppointment((Patient) DataBase.getUsers().get(DataBase.getCurrentUserID()),
                appointmentID, date, timeSlot);

        System.out.println("Appointment Reschedule Successfully");
    }

    public static void handleViewPatientScheduledAppointment() {
        System.out.println("VIEW PATIENT'S SCHEDULED APPOINTMENTS");
        AppointmentManager.viewPatientScheduledAppointments((Patient) DataBase.getUsers().get(DataBase.getCurrentUserID()));
    }

    public static void handleViewPastAppointmentRecords() {
        System.out.println("PAST APPOINTMENT OUTCOME RECORDS");
        HashMap<String, AppointmentOutcome> appointmentOutcomeList = ((Patient) DataBase.getUsers().get(DataBase.getCurrentUserID())).getMedicalRecord().getAppointmentOutcomes();

        if (appointmentOutcomeList.isEmpty()) {
            System.out.println("There is no past appointment outcome records");
            return;
        }

        appointmentOutcomeList.values().forEach(AppointmentManager::printAppointmentOutcome);
    }

    public void printViewMenu() {
        System.out.println("""
                1. View Medical Record
                2. Update Personal Information
                3. View Available Appointment Slots
                4. Schedule an Appointment
                5. Reschedule an Appointment
                6. Cancel an Appointment
                7. View Scheduled Appointments
                8. View Past Appointment Outcome Records
                9. Logout""");
    }

    public void handleView() {
        int choice;

        do {
            this.viewTitle();
            this.printViewMenu();
            System.out.print("Please Enter Your Choice: ");
            choice = Helper.readInt();

            while (choice < 1 || choice > 9) {
                System.out.println("\nInvalid choice. Please try again.");
                System.out.print("Please Enter your Choice: ");
                choice = Helper.readInt();
            }

            Helper.pauseApplication();

            switch (choice) {
                case 1 -> PatientManager.getMedicalRecord(DataBase.getCurrentUserID());
                case 2 -> handleUpdatePersonalInfo();
                case 3 -> handleViewAvailableAppointmentSlots();
                case 4 -> handleScheduleAnAppointment();
                case 5 -> handleRescheduleAppointment();
                case 6 -> handleCancelAppointment();
                case 7 -> handleViewPatientScheduledAppointment();
                case 8 -> handleViewPastAppointmentRecords();
                case 9 -> System.out.println("Thanks for Using HMS");
            }
            Helper.pauseApplication();
        } while (choice != 9);
    }

    public void viewTitle() {
        System.out.println("Patient Menu");
    }
}