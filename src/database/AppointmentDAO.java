package database;

import helper.Helper;
import model.*;
import using.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AppointmentDAO {

    public AppointmentDAO() {}

    public static void readAppointmentData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FileType.APPOINTMENTFILE.getFilePath()))) {
            String line = br.readLine(); // Skip headers

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

    private static void updatePatientAndDoctorData(Appointment appointment, AppointmentOutcome appointmentOutcome) {
        appointment.getPatient().getMedicalRecord().addAppointmentOutcomes(appointmentOutcome, appointment.getAppointmentID());
        appointment.getDoctor().addPatient(appointment.getPatient());
    }

    private static void updateAppointmentData(Appointment appointment, AppointmentStatus appointmentStatus) {
        appointment.getPatient().addAppointment(appointment);
        if (appointmentStatus == AppointmentStatus.PENDING || appointmentStatus == AppointmentStatus.CONFIRM) {
            appointment.getDoctor().addAppointment(appointment);
            if (appointmentStatus == AppointmentStatus.CONFIRM)
                appointment.getDoctor().addPatient(appointment.getPatient());
        }
    }

    public static void writeAppointmentData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileType.APPOINTMENTFILE.getFilePath()))) {
            bw.write("Appointment ID,Appointment Status,Doctor ID,Service,Consultation Notes,Medicine,MedicationStatus\n");
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