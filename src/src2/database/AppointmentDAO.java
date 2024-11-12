package src2.database;

import src2.helper.Helper;
import src2.model.*;
import src2.using.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Data Access Object (DAO) responsible for managing appointment data.
 * Handles reading and writing of appointment-related data from/to a CSV file.
 * 
 * @author Chew En Zee
 * @author Lean Yi Fan
 * @version 3.4
 * @since 2024-11-06
 */
public class AppointmentDAO {

    /**
     * Constructor for the AppointmentDAO class.
     */
    public AppointmentDAO() {}

    /**
     * Reads the appointment data from the CSV file and processes it.
     * Creates appointment objects, associates them with patients and doctors, and updates the relevant data.
     */
    public static void readAppointmentData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FileType.APPOINTMENTFILE.getFilePath()))) {
            String line = br.readLine(); // Skip headers

            // Read and process each line of the file
            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);
                AppointmentStatus appointmentStatus = AppointmentStatus.fromString(inputData.get(1));
                Appointment appointment = createAppointment(inputData, appointmentStatus);

                if (appointmentStatus == AppointmentStatus.COMPLETED) {
                    AppointmentOutcome appointmentOutcome = createAppointmentOutcome(inputData, appointment);
                    appointment.setAppointmentOutcome(appointmentOutcome);
                    updatePatientAndDoctorData(appointment, appointmentOutcome);
                }

                updateAppointmentData(appointment, appointmentStatus);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an Appointment object based on the input data.
     * 
     * @param inputData the data parsed from the CSV line
     * @param appointmentStatus the status of the appointment
     * @return a newly created Appointment object
     */
    private static Appointment createAppointment(List<String> inputData, AppointmentStatus appointmentStatus) {
        return new Appointment(
                inputData.get(0),
                (Doctor) DataBase.getUsers().get(inputData.get(2)),
                appointmentStatus,
                (Patient) DataBase.getUsers().get(inputData.get(0).substring(0, 5)),
                inputData.get(0).substring(5, 7) + "/" + inputData.get(0).substring(7, 9) + "/" + inputData.get(0).substring(9, 11),
                Integer.parseInt(inputData.get(0).substring(11))
        );
    }

    /**
     * Creates an AppointmentOutcome object based on the input data.
     * 
     * @param inputData the data parsed from the CSV line
     * @param appointment the appointment for which the outcome is being created
     * @return a newly created AppointmentOutcome object
     */
    private static AppointmentOutcome createAppointmentOutcome(List<String> inputData, Appointment appointment) {
        return new AppointmentOutcome(
                appointment.getDate(),
                Service.fromString(inputData.get(3)),
                inputData.get(4),
                inputData.get(0),
                inputData.get(5),
                MedicationStatus.fromString(inputData.get(6))
        );
    }

    /**
     * Updates the data of the patient and doctor based on the appointment outcome.
     * 
     * @param appointment the appointment whose data is being updated
     * @param appointmentOutcome the outcome of the appointment
     */
    private static void updatePatientAndDoctorData(Appointment appointment, AppointmentOutcome appointmentOutcome) {
        appointment.getPatient().getMedicalRecord().addAppointmentOutcomes(appointmentOutcome, appointment.getAppointmentID());
        appointment.getDoctor().addPatient(appointment.getPatient());
    }

    /**
     * Updates the appointment data based on the appointment status.
     * 
     * @param appointment the appointment whose data is being updated
     * @param appointmentStatus the status of the appointment (e.g., PENDING, CONFIRM)
     */
    private static void updateAppointmentData(Appointment appointment, AppointmentStatus appointmentStatus) {
        appointment.getPatient().addAppointment(appointment);
        if (appointmentStatus == AppointmentStatus.PENDING || appointmentStatus == AppointmentStatus.CONFIRM) {
            appointment.getDoctor().addAppointment(appointment);
            if (appointmentStatus == AppointmentStatus.CONFIRM)
                appointment.getDoctor().addPatient(appointment.getPatient());
        }
    }

    /**
     * Writes appointment data to the CSV file.
     * It iterates over all patients' appointments and writes their details into the file.
     */
    public static void writeAppointmentData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileType.APPOINTMENTFILE.getFilePath()))) {
            // Write CSV header
            bw.write("Appointment ID,Appointment Status,Doctor ID,Service,Consultation Notes,Medicine,MedicationStatus\n");
            
            // Write appointment data for each patient
            for (User user : DataBase.getUsers().values()) {
                if (!(user instanceof Patient)) continue;
                for (Appointment appointment : ((Patient) user).getAppointments()) {
                    AppointmentOutcome outcome = appointment.getAppointmentOutcome();
                    bw.write(formatAppointmentCSVLine(appointment, outcome));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats the appointment data into a CSV line.
     * 
     * @param appointment the appointment to format
     * @param outcome the appointment outcome to format
     * @return a string representing the appointment in CSV format
     */
    private static String formatAppointmentCSVLine(Appointment appointment, AppointmentOutcome outcome) {
        return String.format("%s,%s,%s,%s,%s,%s,%s\n",
                appointment.getAppointmentID(),
                appointment.getAppointmentStatus().getLabel(),
                appointment.getDoctor().getID(),
                outcome != null ? outcome.getService().getLabel() : "",
                outcome != null ? outcome.getConsultationNotes() : "",
                outcome != null ? outcome.getMedicine() : "",
                outcome != null ? outcome.getMedicationStatus().getLabel() : ""
        );
    }
}
