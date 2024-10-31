package view;

import controller.AppointmentManager;
import controller.DoctorManager;
import controller.PatientManager;
import database.DataBase;
import helper.Helper;
import model.Patient;

public class PatientView implements View{

    public PatientView() {}

    public void printViewMenu() {
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout");
    }

    public void handleView() {
        int choice;

        do {
            this.viewTitle();
            this.printViewMenu();
            System.out.print("Please Enter Your Choice: ");
            choice = Helper.readInt();
            System.out.println();

            switch (choice) {
                case 1:
                    PatientManager.getMedicalRecord(DataBase.getCurrUserID());
                    break;

                case 2:
                    this.handleUpdatePersonalInfo();
                    break;

                case 3:
                    this.viewAvailableAppointmentSlots();
                    break;

                case 4:
                    this.handleScheduleAnAppointment();
                    break;

                case 5:
                    this.handleRescheduleAppointment();
                    break;

                case 6:
                    this.handleCancelAppointment();
                    break;

                case 7:
                    this.viewScheduledAppointment();
                    break;

            }

            Helper.pauseApplication();

        } while (choice != 9);

    }

    public void handleUpdatePersonalInfo() {
        System.out.println("Please key in the latest PhoneNo & EmailAddress");
        System.out.print("Email Address: ");
        String updateEmailAddress = Helper.readString();
        System.out.print("Phone No: ");
        String updatePhoneNo = Helper.readString();

        PatientManager.updatePersonalInformation(updateEmailAddress, updatePhoneNo, DataBase.getCurrUserID());
        System.out.println("Updated Successfully");
    }

    public void viewTitle() { System.out.println("Patient Menu"); }

    public void viewAvailableAppointmentSlots() {
        DoctorManager.printAllAvailableSlots();
    }

    public void handleScheduleAnAppointment(){
        this.viewAvailableAppointmentSlots();
        System.out.println("Each Time Slot is 1 Hour");
        System.out.print("Please Enter the Date (dd/mm/yy): ");
        String date = Helper.readString();
        System.out.print("Please Enter Doctor Name: ");
        String doctorName = Helper.readString();
        System.out.print("Please Enter the Slot in 24Hour Format (13 stands for 1pm): ");
        int timeSlot = Helper.readInt();

        AppointmentManager.scheduleAppointment(doctorName, date, timeSlot);

        System.out.println("Appointment Schedule Successfully");
    }

    public void handleCancelAppointment() {
        System.out.println("CANCEL APPOINTMENT");
        AppointmentManager.cancel_viewPatientScheduledAppointments((Patient) DataBase.getUsers().get(DataBase.getCurrUserID()));
        System.out.print("Please Enter the Appointment ID: ");
        String appointmentID = Helper.readString();

        AppointmentManager.cancelAppointment((Patient) DataBase.getUsers().get(DataBase.getCurrUserID()),
                                             appointmentID);

        System.out.println("Appointment Cancel Successfully");
    }

    public void handleRescheduleAppointment() {
        System.out.println("RESCHEDULE APPOINTMENT");
        AppointmentManager.viewPatientScheduledAppointments((Patient) DataBase.getUsers().get(DataBase.getCurrUserID()));
        System.out.print("Please Enter the Appointment ID: ");
        String appointmentID = Helper.readString();

        this.viewAvailableAppointmentSlots();

        System.out.print("Please Enter the Date (dd/mm/yy): ");
        String date = Helper.readString();
        System.out.print("Please Enter the Slot in 24Hour Format (13 stands for 1pm): ");
        int timeSlot = Helper.readInt();

        AppointmentManager.rescheduleAppointment((Patient) DataBase.getUsers().get(DataBase.getCurrUserID()),
                                                 appointmentID, date, timeSlot);

        System.out.println("Appointment Reschedule Successfully");
    }

    public void viewScheduledAppointment() {
        AppointmentManager.viewPatientScheduledAppointments((Patient) DataBase.getUsers().get(DataBase.getCurrUserID())); }
    }
