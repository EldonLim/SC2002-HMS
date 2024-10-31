package controller;

import database.DataBase;
import model.Appointment;
import model.Doctor;
import model.Patient;
import model.User;

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

    }
}
