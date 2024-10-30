package controller;

import database.DataBase;
import model.Adminstrator;
import model.Doctor;
import model.Patient;
import model.Pharmacist;
import model.User;
import using.BloodType;
import using.Gender;
import using.Role;

public class AdministratorManager {

    public AdministratorManager(){}

    // method overloading
    // add patient
    public static void addUser(String name, String id, String password, Role role, Gender gender, BloodType bloodType, String phoneNo, String emailAddress, String dateOfBirth){
        Patient patient = new Patient(name, id, password, role, gender, bloodType, phoneNo, emailAddress, dateOfBirth);
        // from here need access database function to add new user
        // database.addPatient(patient)
    }

    public static void addUser(String name, String id, String password, Role role, Gender gender, int age){
        if (role == Role.ADMINISTRATOR){
            Adminstrator administrator = new Adminstrator(name, id, password, role, gender, age);
        } else if (role == Role.PHARMACIST){
            Pharmacist pharmacist = new Pharmacist(name, id, password, role, gender, age);
        } else { // doctor
            Doctor doctor = new Doctor(name, id, password, role, gender, age);
        }
        // to be implemented
        // database.addStaff(staff)
    }

    public static void removeUser(String id){
        // need remove from database
        // database.removeUser(user)
    }

    // method overloading
    // filter by gender
    public static User[] getFilteredStaff(Gender gender){
        // this line might need change cos now is just hardcoded initialize to 100
        User[] filteredStaff = new User[100];
        int index=0;

        // store staffs list by gender
        for (User user: DataBase.Users.values()){
            if (user.getRole() != Role.PATIENT && user.getGender() == gender){
                filteredStaff[index] = user;
                index++;
            }
        }

        return filteredStaff;
    }

    // filter by role
    public static User[] getFilteredStaff(Role role){
        // this line might need change cos now is just hardcoded initialize to 100
        User[] filteredStaff = new User[100];
        int index=0;

        // store staffs list by gender
        for (User user: DataBase.Users.values()){
            if (user.getRole() == role){
                filteredStaff[index] = user;
                index++;
            }
        }

        return filteredStaff;
    }

    // filter by age
    public static User[] getFilteredStaff(int age){
        // to-do
    }

    public static void approveReplenishmentRequest(){
        // to-do
        // with inventory manager
    }


}
