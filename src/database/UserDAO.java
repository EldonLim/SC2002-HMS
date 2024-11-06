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

/**
 * Data Access Object (DAO) for managing user data, including patients and staff.
 * Provides methods for reading and writing user data from and to files, as well as
 * managing counts of different types of users.
 * 
 * @author Chin Linn Sheng
 * @version 2.1
 * @since 2024-11-06
 */
public class UserDAO {

    /** Map of users, stored by their unique user IDs. */
    private static HashMap<String, User> users;
    /** Count of patients in the system. */
    private static int numberOfPatients;
    /** Count of doctors in the system. */
    private static int numberOfDoctors;
    /** Count of administrators in the system. */
    private static int numberOfAdmins;
    /** Count of pharmacists in the system. */
    private static int numberOfPharmacists;
    /** The ID of the currently logged-in user. */
    private static String currentUserID;

    /**
     * Constructor for UserDAO. Initializes the user database and counts of different user roles.
     */
    public UserDAO() {
        users = new HashMap<>();
        numberOfPatients = 0;
        numberOfDoctors = 0;
        numberOfAdmins = 0;
        numberOfPharmacists = 0;
        currentUserID = null;
    }

    /**
     * Reads patient data from a CSV file, creating Patient objects for each entry and
     * adding them to the user database.
     */
    public void readPatientData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FileType.PATIENTFILE.getFilePath()))) {
            String line = br.readLine(); // Skip headers
            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);
                createPatient(inputData);
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    /**
     * Creates a Patient object based on parsed input data and adds it to the user database.
     *
     * @param inputData List of data fields representing a patient's attributes.
     */
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

    /**
     * Reads staff data from a CSV file, creating Staff objects for each entry and
     * adding them to the user database.
     */
    public static void readStaffData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FileType.STAFFFILE.getFilePath()))) {
            String line = br.readLine(); // Skip headers
            while ((line = br.readLine()) != null) {
                List<String> inputData = Helper.parseCSVLine(line);
                createStaff(inputData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a Staff object based on parsed input data and adds it to the user database.
     *
     * @param inputData List of data fields representing a staff member's attributes.
     */
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

    /**
     * Writes patient data from the user database to a CSV file.
     */
    public static void writePatientData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileType.PATIENTFILE.getFilePath()))) {
            bw.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Contact Information,Phone No,Password,Treatments,Diagnosis\n");
            for (User user : users.values()) {
                if (user instanceof Patient) {
                    Patient patient = (Patient) user;
                    bw.write(formatPatientData(patient));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats the data of a Patient object for CSV output.
     *
     * @param patient The Patient object to format.
     * @return A formatted string representing the patient data for CSV.
     */
    private static String formatPatientData(Patient patient) {
        String treatments = String.join(",", patient.getMedicalRecord().getTreatments());
        String diagnosis = String.join(",", patient.getMedicalRecord().getDiagnosis());
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,\"%s\",\"%s\"\n",
                patient.getID(), patient.getName(), patient.getDateOfBirth(),
                patient.getGender().getLabel(), patient.getBloodType().getLabel(),
                patient.getEmailAddress(), patient.getPhoneNo(), patient.getPassword(),
                treatments, diagnosis);
    }

    /**
     * Writes staff data from the user database to a CSV file.
     */
    public static void writeStaffData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FileType.STAFFFILE.getFilePath()))) {
            bw.write("Staff ID,Name,Role,Gender,Age,Password\n");
            for (User user : users.values()) {
                if (!(user instanceof Patient)) {
                    bw.write(formatStaffData((Staff) user));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats the data of a Staff object for CSV output.
     *
     * @param staff The Staff object to format.
     * @return A formatted string representing the staff data for CSV.
     */
    private static String formatStaffData(Staff staff) {
        return String.format("%s,%s,%s,%s,%d,%s\n",
                staff.getID(), staff.getName(), staff.getRole().getLabel(),
                staff.getGender().getLabel(), staff.getAge(), staff.getPassword());
    }

    /**
     * Gets the number of registered patients.
     * @return the number of patients.
     */
    public static int getNumberOfPatients() { return numberOfPatients; }

    /**
     * Gets the number of registered doctors.
     * @return the number of doctors.
     */
    public static int getNumberOfDoctors() { return numberOfDoctors; }

    /**
     * Gets the number of registered administrators.
     * @return the number of administrators.
     */
    public static int getNumberOfAdminstrators() { return numberOfAdmins; }

    /**
     * Gets the number of registered pharmacists.
     * @return the number of pharmacists.
     */
    public static int getNumberOfPharmacists() { return numberOfPharmacists; }

    /**
     * Gets the current user ID.
     * @return the current user's ID as a String.
     */
    public static String getCurrentUserID() { return currentUserID; }

    /**
     * Sets the current user ID.
     * @param currentUserID the current user's ID.
     */
    public void setCurrentUserID(String currentUserID) { UserDAO.currentUserID = currentUserID; }

    /**
     * Retrieves all users in the system.
     * @return a HashMap of user IDs to User objects.
     */
    public static HashMap<String, User> getUsers() { return users; }

    /**
     * Increases the count for a specified user role.
     *
     * @param role The role of the user to increment count for.
     */
    public void increaseUserCount(Role role) {
        switch (role) {
            case PATIENT -> numberOfPatients++;
            case DOCTOR -> numberOfDoctors++;
            case ADMINISTRATOR -> numberOfAdmins++;
            case PHARMACIST -> numberOfPharmacists++;
        }
    }

    /**
     * Decreases the count for a specified user role.
     *
     * @param role The role of the user to decrement count for.
     */
    public void decreaseUserCount(Role role) {
        switch (role) {
            case PATIENT -> numberOfPatients--;
            case DOCTOR -> numberOfDoctors--;
            case ADMINISTRATOR -> numberOfAdmins--;
            case PHARMACIST -> numberOfPharmacists--;
        }
    }
}
