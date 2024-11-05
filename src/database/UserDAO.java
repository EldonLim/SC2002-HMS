package database;

import model.*;
import helper.*;
import using.BloodType;
import using.FileType;
import using.Gender;
import using.Role;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class UserDAO {

    private static HashMap<String, User> users;
    private static int numberOfPatients;
    private static int numberOfDoctors;
    private static int numberOfAdmins;
    private static int numberOfPharmacists;
    private static String currentUserID;

    public UserDAO() {
        users = new HashMap<>();
        numberOfPatients = 0;
        numberOfDoctors = 0;
        numberOfAdmins = 0;
        numberOfPharmacists = 0;
        currentUserID = null;
    }

    public void readPatientData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FileType.PATIENTFILE.getFilePath()))) {
            String line = br.readLine(); // Skip headers
            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);
                createPatient(inputData);
            }
        }
        catch (IOException e) {
           e.printStackTrace();
        }
    }

    private static void createPatient(List<String> inputData) {
        String patientID = inputData.get(0);
        User patient = new Patient(
                inputData.get(1), patientID, inputData.get(7).isEmpty()? Encryption.encode("password") : inputData.get(7), Role.PATIENT,
                Gender.fromString(inputData.get(3)), BloodType.fromString(inputData.get(4)),
                inputData.get(6), inputData.get(5), inputData.get(2), Helper.parseList(inputData.get(8)),
                Helper.parseList(inputData.get(9))
        );
        users.put(patientID, patient);
        numberOfPatients++;
    }

    public static void readStaffData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FileType.STAFFFILE.getFilePath()))) {
            String line = br.readLine(); // Skip headers
            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);
                createStaff(inputData);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createStaff(List<String> inputData) {
        String staffID = inputData.get(0);
        Role role = Role.fromString(inputData.get(2));
        User staff = null;

        switch (role) {
            case ADMINISTRATOR:
                staff = new Adminstrator(
                        inputData.get(1), staffID, inputData.get(5).isEmpty()? Encryption.encode("password") : inputData.get(5),
                        role, Gender.fromString(inputData.get(3)), Integer.parseInt(inputData.get(4))
                );
                numberOfAdmins++;
                break;
            case DOCTOR:
                staff = new Doctor(
                        inputData.get(1), staffID, inputData.get(5).isEmpty()? Encryption.encode("password") : inputData.get(5),
                        role, Gender.fromString(inputData.get(3)), Integer.parseInt(inputData.get(4))
                );
                numberOfDoctors++;
                break;
            case PHARMACIST:
                staff = new Pharmacist(
                        inputData.get(1), staffID, inputData.get(5).isEmpty()? Encryption.encode("password") : inputData.get(5),
                        role, Gender.fromString(inputData.get(3)), Integer.parseInt(inputData.get(4))
                );
                numberOfPharmacists++;
                break;
        }
        users.put(staffID, staff);
    }

    public static void writePatientData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileType.PATIENTFILE.getFilePath()))) {
            bw.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Contact Information,Phone No,Password,Treatments,Diagnosis\n");
            for (User user : users.values()) {
                if (user instanceof Patient) {
                    Patient patient = (Patient) user;
                    bw.write(formatPatientData(patient));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatPatientData(Patient patient) {
        String treatments = String.join(",", patient.getMedicalRecord().getTreatments());
        String diagnosis = String.join(",", patient.getMedicalRecord().getDiagnosis());
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,\"%s\",\"%s\"\n",
                patient.getID(), patient.getName(), patient.getDateOfBirth(),
                patient.getGender().getLabel(), patient.getBloodType().getLabel(),
                patient.getEmailAddress(), patient.getPhoneNo(), patient.getPassword(),
                treatments, diagnosis);
    }

    public static void writeStaffData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileType.STAFFFILE.getFilePath()))) {
            bw.write("Staff ID,Name,Role,Gender,Age,Password\n");
            for (User user : users.values()) {
                if (!(user instanceof Patient)) {
                    bw.write(formatStaffData((Staff) user));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatStaffData(Staff staff) {
        return String.format("%s,%s,%s,%s,%d,%s\n",
                staff.getID(), staff.getName(), staff.getRole().getLabel(),
                staff.getGender().getLabel(), staff.getAge(), Encryption.encode(staff.getPassword()));
    }

    public static int getNumberOfPatients() { return numberOfPatients; }
    public static int getNumberOfDoctors() { return numberOfDoctors; }
    public static int getNumberOfAdminstrators() { return numberOfAdmins; }
    public static int getNumberOfPharmacists() { return numberOfPharmacists; }
    public static String getCurrentUserID() { return currentUserID; }
    public void setCurrentUserID(String currentUserID) { UserDAO.currentUserID = currentUserID; }
    public static HashMap<String, User> getUsers() { return users; }

    public void increaseUserCount(Role role) {
        switch (role) {
            case PATIENT -> numberOfPatients++;
            case DOCTOR -> numberOfDoctors++;
            case ADMINISTRATOR -> numberOfAdmins++;
            case PHARMACIST -> numberOfPharmacists++;
        }
    }

    public void decreaseUserCount(Role role) {
        switch (role) {
            case PATIENT -> numberOfPatients--;
            case DOCTOR -> numberOfDoctors--;
            case ADMINISTRATOR -> numberOfAdmins--;
            case PHARMACIST -> numberOfPharmacists--;
        }
    }
}