package controller;

import database.DataBase;
import helper.Encryption;
import helper.Helper;
import model.*;
import using.BloodType;
import using.Gender;
import using.Role;

import java.util.ArrayList;
import java.util.List;

public class AdminstratorManager {

    public AdminstratorManager() {
    }

    public static List<Staff> getAllStaff() {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() != Role.PATIENT)
                .map(Staff.class::cast)
                .toList();
    }

    public static List<Staff> getAllStaffByGender(Gender gender) {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() != Role.PATIENT && user.getGender() == gender)
                .map(Staff.class::cast)
                .toList();
    }

    public static List<Staff> getAllStaffByRole(Role role) {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() == role)
                .map(Staff.class::cast)
                .toList();
    }

    public static List<Staff> getAllStaffByAgeGroup(int age) {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() != Role.PATIENT)
                .map(Staff.class::cast)
                .filter(staff -> staff.getAge() >= age && staff.getAge() <= age + 9)
                .toList();
    }

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

    public static void removeStaff(String userID) {
        Role staffRole = DataBase.getUsers().get(userID).getRole();

        switch (staffRole) {
            case DOCTOR -> DataBase.decreaseUserCount(Role.DOCTOR);
            case PHARMACIST -> DataBase.decreaseUserCount(Role.PHARMACIST);
            case ADMINISTRATOR -> DataBase.decreaseUserCount(Role.ADMINISTRATOR);
        }
        DataBase.getUsers().remove(userID);
    }

    public static void updateLowStockAlert(String medicineName) {
        System.out.print("Please Enter the New Low Stock Alert: ");
        int quantity = Helper.readInt();

        Medicine medicine = DataBase.getMedicines().get(medicineName);
        medicine.setLowStockThreshold(quantity);

        System.out.println("Low Stock Alert for " + medicineName + " is updated to " + quantity);
    }

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
