package database;
import using.*;
import model.*;

import java.lang.reflect.GenericDeclaration;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.function.DoubleConsumer;

public class DataBase {

    private static final String folderPath = "./src/database/data/";
    private static final String fileExtension = ".csv";

    public static HashMap<String, User> Users = new HashMap<String, User>();
    public static HashMap<String, MedicalRecord> MedicalRecords = new HashMap<>();

    // For the purpose of easy of writing back to database.
    // When update need to update both Schedules and the schedule within every doctor
    public static HashMap<String, Schedule> Schedules = new HashMap<>();

    public static int numberOfPatient = 0;
    public static int numberofDoctor = 0;
    public static int numberofAdminstrator = 0;
    public static int numberOfPharmacist = 0;

    public DataBase() {
        if (!readPatientCSVFile(FileType.PATIENTFILE))
            System.err.println("Fail to read" + FileType.PATIENTFILE.getFileName());
        if (!readStaffCSVFile(FileType.STAFFFILE))
            System.err.println("Fail to read" + FileType.STAFFFILE.getFileName());

        // for initial case where we only have 3 files, initialize the schedule for each doctor
        initializeDoctorSchedule();
    }

    public static void initializeDoctorSchedule() {
        for (User user : Users.values())
            if (user.getRole() == Role.DOCTOR) {
                Schedule schedule = new Schedule();
                schedule.setDoctor((Doctor) user);
                ((Doctor) user).setSchedule(schedule);

                Schedules.put(user.getID(), schedule);
            }
    }

    public static boolean readStaffCSVFile(FileType fileType) {
        String filePath = folderPath + fileType.getFileName() + fileExtension;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            String[] headers = line.split(",");

            boolean initialState = headers.length == 5;

            while ((line = br.readLine()) != null) {
                String[] inputData = line.split(",");

                String staffID = inputData[0];
                Role role = Role.fromString(inputData[2]);
                User user = null;

                switch (role) {
                    case ADMINISTRATOR:
                        user = new Adminstrator(inputData[1], staffID, initialState? "password" : inputData[5], role, Gender.fromString(inputData[3]), Integer.parseInt(inputData[4]));
                        numberofAdminstrator++;
                        break;

                    case DOCTOR:
                        user = new Doctor(inputData[1], staffID, initialState? "password" : inputData[5], role, Gender.fromString(inputData[3]), Integer.parseInt(inputData[4]));
                        numberofDoctor++;
                        break;

                    case PHARMACIST:
                        user = new Pharmacist(inputData[1], staffID, initialState? "password" : inputData[5], role, Gender.fromString(inputData[3]), Integer.parseInt(inputData[4]));
                        numberOfPharmacist++;
                        break;
                }

                Users.put(staffID, user);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean readPatientCSVFile(FileType fileType) {
       String filePath = folderPath + fileType.getFileName() + fileExtension;

       try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
           String line = br.readLine();
           String[] headers = line.split(",");
           boolean initialState = headers.length == 6;

           while ((line = br.readLine()) != null) {
               String[] inputData = line.split(",");

               String patientID = inputData[0];
               User user = new Patient(inputData[1], patientID, initialState? "password" : inputData[6], Role.PATIENT, Gender.fromString(inputData[3]), BloodType.fromString(inputData[4]), initialState? "" : inputData[7], inputData[5], inputData[2]);
               // might change the casting
               MedicalRecord medicalRecord = new MedicalRecord((Patient) user);
               ((Patient) user).setMedicalRecord(medicalRecord);

               numberOfPatient++;
               Users.put(patientID, user);
               MedicalRecords.put(patientID, medicalRecord);
           }

       }
       catch (IOException e) {
           e.printStackTrace();
           return false;
       }
       return true;
    }

    public static int getNumberOfPatient() { return numberOfPatient; }

    public static int getNumberofDoctor() { return numberofDoctor; }

    public static int getNumberofAdminstrator() { return numberofAdminstrator; }

    public static int getNumberOfPharmacist() { return numberOfPharmacist; }

}