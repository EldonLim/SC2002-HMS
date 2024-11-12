package src2.database;

import src2.helper.Helper;
import src2.model.Doctor;
import src2.model.User;
import src2.using.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object (DAO) for managing doctor schedules.
 * Provides methods for reading and writing schedule data from and to files, and updating
 * doctor schedules based on data from the user database.
 *
 * @author Chin Linn Sheng
 * @version 1.2
 * @since 2024-11-06
 */
public class ScheduleDAO {

    /**
     * The UserDAO instance for accessing user data, including doctor information. 
     */
    private UserDAO userDAO;

    /**
     * Constructs a ScheduleDAO object.
     *
     * @param userDAO The UserDAO instance used to access user data, particularly doctor information.
     */
    public ScheduleDAO(UserDAO userDAO) { this.userDAO = userDAO; }

    /**
     * Retrieves the file used to store schedule data.
     *
     * @return The File object representing the schedule file.
     */
    public File getScheduleFile() { return new File(FileType.SCHEDULEFILE.getFilePath()); }

    /**
     * Reads schedule data from a CSV file and updates doctor schedules accordingly.
     */
    public void readScheduleData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FileType.SCHEDULEFILE.getFilePath()))) {
            String line = br.readLine(); // Skip headers
            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);
                updateDoctorSchedule(inputData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the schedule for a specific doctor based on parsed input data.
     *
     * @param inputData A list of strings representing schedule data for a doctor (doctor ID, date, time slot, availability).
     */
    private void updateDoctorSchedule(List<String> inputData) {
        String doctorID = inputData.get(0);
        User user = userDAO.getUsers().get(doctorID);
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            doctor.getSchedule().getWeeklySlots().get(inputData.get(1))
                    .put(Integer.parseInt(inputData.get(2)), Availability.fromString(inputData.get(3)));
        }
    }

    /**
     * Writes schedule data from the user database to a CSV file.
     */
    public void writeScheduleData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileType.SCHEDULEFILE.getFilePath()))) {
            bw.write("Doctor ID,Date,Time Slot,Availability\n");
            for (User user : userDAO.getUsers().values()) {
                if (user instanceof Doctor) {
                    writeScheduleForDoctor((Doctor) user, bw);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the schedule data for a specific doctor to a BufferedWriter.
     *
     * @param doctor The Doctor object for which to write schedule data.
     * @param bw The BufferedWriter used to write the data.
     * @throws IOException If an I/O error occurs while writing to the BufferedWriter.
     */
    private void writeScheduleForDoctor(Doctor doctor, BufferedWriter bw) throws IOException {
        HashMap<String, HashMap<Integer, Availability>> doctorSchedule = doctor.getSchedule().getWeeklySlots();
        for (Map.Entry<String, HashMap<Integer, Availability>> outerHash : doctorSchedule.entrySet()) {
            for (Map.Entry<Integer, Availability> innerHash : outerHash.getValue().entrySet()) {
                if (innerHash.getValue() != Availability.AVAILABLE) {
                    bw.write(String.format("%s,%s,%s,%s\n",
                            doctor.getID(), outerHash.getKey(), innerHash.getKey(), innerHash.getValue().getLabel()));
                }
            }
        }
    }
}
