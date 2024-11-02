package controller;

import database.DataBase;
import model.*;
import using.Gender;
import using.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminstratorManager {

    public AdminstratorManager() {}

    public static List<Staff> getAllStaff() {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() != Role.PATIENT)
                .map(Staff.class::cast)
                .collect(Collectors.toList());
    }

    public static List<Staff> getAllStaffByGender(Gender gender) {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() != Role.PATIENT && ((Staff) user).getGender() == gender)
                .map(Staff.class::cast)
                .collect(Collectors.toList());
    }

    public static List<Staff> getAllStaffByRole(Role role) {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() == role)
                .map(Staff.class::cast)
                .collect(Collectors.toList());
    }

    public static List<Staff> getAllStaffByAgeGroup(int age) {
        return DataBase.getUsers().values().stream()
                .filter(user -> user.getRole() != Role.PATIENT)
                .map(Staff.class::cast)
                .filter(staff -> staff.getAge() >= age && staff.getAge() <= age + 9)
                .collect(Collectors.toList());
    }

    public static void addNewStaff(String name, Role role, Gender gender, int age) {
        User user = null;
        String userID;

        switch (role) {
            case Role.DOCTOR:
                userID = String.format("D%03d", DataBase.getNumberofDoctor() + 1);
                DataBase.increaseDoctorCount();
                user = new Doctor(name, userID, "password", role, gender, age);
                break;

            case Role.PHARMACIST:
                userID = String.format("P%03d", DataBase.getNumberOfPharmacist() + 1);
                DataBase.increasePharmacistCount();
                user = new Pharmacist(name, userID, "password", role, gender, age);
                break;

            case Role.ADMINISTRATOR:
                userID = String.format("A%03d", DataBase.getNumberofAdminstrator() + 1);
                DataBase.increaseAdminstratorCount();
                user = new Adminstrator(name, userID, "password", role, gender, age);
        }

        DataBase.getUsers().put(user.getID(), user);
        System.out.println("\nStaff ID: " + user.getID());
        System.out.println("Password: " + user.getPassword());
    }

    public static void removeStaff(String userID) {
        Role staffRole = DataBase.getUsers().get(userID).getRole();

        switch (staffRole) {
            case Role.DOCTOR: DataBase.decreaseDoctorCount(); break;
            case Role.PHARMACIST: DataBase.decreasePharmacistCount(); break;
            case Role.ADMINISTRATOR: DataBase.decreaseAdminstratorCount();
        }

        DataBase.getUsers().remove(userID);
    }
}
