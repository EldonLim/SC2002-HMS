package controller;

import database.DataBase;
import model.Staff;
import model.User;
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
}
