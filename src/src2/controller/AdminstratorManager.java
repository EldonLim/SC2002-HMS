package src2.controller;

import src2.database.DataBase;
import src2.helper.Encryption;
import src2.helper.Helper;
import src2.model.*;
import src2.using.BloodType;
import src2.using.Gender;
import src2.using.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * The AdministratorManager class provides functionality for managing staff and patients,
 * including adding, removing, and retrieving staff based on specific criteria.
 * 
 * @author Lean Yi Fan
 * @version 4.3
 * @since 2024-11-02
 */
public class AdminstratorManager {

    /**
     * Constructs an AdminstratorManager instance.
     */
    public AdminstratorManager() {
    }

    /**
     * Retrieves a list of all staff members, excluding patients.
     *
     * @return a list of Staff objects representing all staff members.
     */
    public static List<Staff> getAllStaff() {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() != Role.PATIENT)
                .map(Staff.class::cast)
                .toList();
    }

    /**
     * Retrieves a list of all staff members of a specified gender.
     *
     * @param gender the gender of staff members to retrieve.
     * @return a list of Staff objects with the specified gender.
     */
    public static List<Staff> getAllStaffByGender(Gender gender) {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() != Role.PATIENT && user.getGender() == gender)
                .map(Staff.class::cast)
                .toList();
    }

    /**
     * Retrieves a list of all staff members with a specified role.
     *
     * @param role the role of staff members to retrieve.
     * @return a list of Staff objects with the specified role.
     */
    public static List<Staff> getAllStaffByRole(Role role) {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() == role)
                .map(Staff.class::cast)
                .toList();
    }

    /**
     * Retrieves a list of all staff members within a specified age group.
     *
     * @param age the starting age of the age group.
     * @return a list of Staff objects within the specified age range.
     */
    public static List<Staff> getAllStaffByAgeGroup(int age) {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() != Role.PATIENT)
                .map(Staff.class::cast)
                .filter(staff -> staff.getAge() >= age && staff.getAge() <= age + 9)
                .toList();
    }

    /**
     * Adds a new staff member to the database with the specified parameters.
     *
     * @param name   the name of the new staff member.
     * @param role   the role of the new staff member.
     * @param gender the gender of the new staff member.
     * @param age    the age of the new staff member.
     */
    public static void addNewStaff(String name, Role role, Gender gender, int age) {
        User user = null;
        String userID;
        String defaultPassword = Encryption.encode("password");

        switch (role) {
            case DOCTOR -> {
                userID = String.format("D%03d", DataBase.getNumberOfDoctors() + 1);
                DataBase.increaseUserCount(role);
                user = new Doctor(name, userID, defaultPassword, role, gender, age);
            }
            case PHARMACIST -> {
                userID = String.format("P%03d", DataBase.getNumberOfPharmacists() + 1);
                DataBase.increaseUserCount(role);
                user = new Pharmacist(name, userID, defaultPassword, role, gender, age);
            }
            case ADMINISTRATOR -> {
                userID = String.format("A%03d", DataBase.getNumberOfAdminstrators() + 1);
                DataBase.increaseUserCount(role);
                user = new Adminstrator(name, userID, defaultPassword, role, gender, age);
            }
        }

        DataBase.getUsers().put(user.getID(), user);
        System.out.println("\nStaff ID: " + user.getID());
        System.out.println("Password: " + Encryption.decode(user.getPassword()));
    }

    /**
     * Removes a staff member from the database by their user ID.
     *
     * @param userID the ID of the staff member to be removed.
     */
    public static void removeStaff(String userID) {
        Role staffRole = DataBase.getUsers().get(userID).getRole();

        switch (staffRole) {
            case DOCTOR -> DataBase.decreaseUserCount(Role.DOCTOR);
            case PHARMACIST -> DataBase.decreaseUserCount(Role.PHARMACIST);
            case ADMINISTRATOR -> DataBase.decreaseUserCount(Role.ADMINISTRATOR);
        }
        DataBase.getUsers().remove(userID);
    }

    /**
     * Updates the low stock alert for a specified medicine.
     *
     * @param medicineName the name of the medicine to update the low stock alert for.
     */
    public static void updateLowStockAlert(String medicineName) {
        System.out.print("Please Enter the New Low Stock Alert: ");
        int quantity = Helper.readInt();

        Medicine medicine = DataBase.getMedicines().get(medicineName);
        medicine.setLowStockThreshold(quantity);

        System.out.println("Low Stock Alert for " + medicineName + " is updated to " + quantity);
    }

    /**
     * Registers a new patient with the specified information.
     *
     * @param name         the name of the patient.
     * @param phoneNo      the phone number of the patient.
     * @param emailAddress the email address of the patient.
     * @param bloodType    the blood type of the patient.
     * @param dateOfBirth  the date of birth of the patient.
     * @param gender       the gender of the patient.
     */
    public static void registerNewPatient(String name, String phoneNo, String emailAddress, BloodType bloodType, String dateOfBirth, Gender gender) {
        String patientID = String.format("P1%03d", DataBase.getNumberOfPatients() + 1);
        DataBase.increaseUserCount(Role.PATIENT);

        Patient patient = new Patient(name, patientID, Encryption.encode("password"), Role.PATIENT, gender, bloodType, phoneNo, emailAddress, dateOfBirth, new ArrayList<>(), new ArrayList<>());
        DataBase.getUsers().put(patientID, patient);
        System.out.println("\nRegister Successfully");

        System.out.println("ID: " + patient.getID());
        System.out.println("Password: " + Encryption.decode(patient.getPassword()));
    }
}
