package database;

import helper.Helper;
import model.Doctor;
import model.User;
import using.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleDAO {

    private UserDAO userDAO;

    public ScheduleDAO(UserDAO userDAO) { this.userDAO = userDAO; }

    public File getScheduleFile() { return new File(FileType.SCHEDULEFILE.getFilePath()); }

    public void readScheduleData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FileType.SCHEDULEFILE.getFilePath()))) {
            String line = br.readLine(); // Skip headers
            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);
                updateDoctorSchedule(inputData);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateDoctorSchedule(List<String> inputData) {
        String doctorID = inputData.get(0);
        User user = userDAO.getUsers().get(doctorID);
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            doctor.getSchedule().getWeeklySlots().get(inputData.get(1))
                    .put(Integer.parseInt(inputData.get(2)), Availability.fromString(inputData.get(3)));
        }
    }

    public void writeScheduleData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileType.SCHEDULEFILE.getFilePath()))) {
            bw.write("Doctor ID,Date,Time Slot,Availability\n");
            for (User user : userDAO.getUsers().values()) {
                if (user instanceof Doctor) {
                    writeScheduleForDoctor((Doctor) user, bw);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

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