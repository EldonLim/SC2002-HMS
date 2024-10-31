package controller;

import java.util.ArrayList;
import database.DataBase;
import model.Adminstrator;
import model.Doctor;
import model.Patient;
import model.Pharmacist;
import model.Staff;
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
        // add to database
        DataBase.Users.put(id, patient);
    }

    // add staff
    public static void addUser(String name, String id, String password, Role role, Gender gender, int age){
        User user;

        if (role == Role.ADMINISTRATOR){
            user = new Adminstrator(name, id, password, role, gender, age);
        } else if (role == Role.PHARMACIST){
            user = new Pharmacist(name, id, password, role, gender, age);
        } else { // doctor 
            user = new Doctor(name, id, password, role, gender, age);
        }
        // add to database
        DataBase.Users.put(id, user);
    }

    // remove user (staff or patient)
    public static void removeUser(String id){
        DataBase.Users.remove(id);
    }

    // method overloading
    // filter by gender
    public static Staff[] getFilteredStaff(Gender gender){
        Staff[] staffList = extractAllStaffs();
        Staff[] filteredStaff = new Staff[100];
        int index = 0;

        // store staffs list by gender
        for (User user: staffList){
            if (user.getGender() == gender){
                filteredStaff[index] = (Staff) user;
                index++;
            }
        }

        return filteredStaff;
    }

    // filter by role
    public static Staff[] getFilteredStaff(Role role){
        Staff[] staffList = extractAllStaffs();
        Staff[] filteredStaff = new Staff[100];
        int index=0;

        // store staffs list by gender
        for (User user: staffList){
            if (user.getRole() == role){
                filteredStaff[index] = (Staff) user;
                index++;
            }
        }

        return filteredStaff;
    }

    // filter by age
    public static User[] getFilteredStaff(int ageStart, int ageEnd){
        Staff[] staffList = extractAllStaffs();
        Staff[] filteredStaff = new Staff[100];
        int index = 0;

        // Insertion sort
        staffList = insertionSort(staffList);
        
        // extract the age between ageStart and ageEnd
        for (int i=0; i<staffList.length; i++){
            if (staffList[i].getAge() >= ageStart && staffList[i].getAge() <= ageEnd){
                filteredStaff[index] = staffList[i];
                index++;
            }
        }

        return filteredStaff;
    }

    public static void approveReplenishmentRequest(){
        // to-do
        // with inventory manager
    }

    // helper methods
    public static Staff[] extractAllStaffs(){
        Staff[] filteredStaff = new Staff[100];
        int index=0;

        for (User user: DataBase.Users.values()){
            if (user.getRole() != Role.PATIENT){
                filteredStaff[index] = (Staff) user;
                index++;
            }
        }

        return filteredStaff;
    }

    public static Staff[] insertionSort(Staff[] staffList){
        for(int i=0; i<staffList.length; i++){
            for(int j=i; j>0; j--){
                if(staffList[j].getAge() < staffList[j-1].getAge()){
                    swap(staffList[j], staffList[j-1]);
                } else{
                    break;
                }
            }
        }

        return staffList;
    }

    public static void swap(Staff staff1, Staff staff2){
        Staff temp;
        temp = staff1;
        staff1 = staff2;
        staff2 = temp;
    }

}
