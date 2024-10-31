package controller;

import model.AppointmentOutcome;
import model.Medicine;
import database.DataBase;
import helper.Helper;

import java.util.ArrayList;

public class PharmacistManager {
    public PharmacistManager(){};

    // public static void viewAppointmentOutcomeRecords(String appointmentOutcomeID){

    //     //if AppointmentOutcome hashmap is empty (no appointments completed yet)
    //     if (DataBase.AppointmentOutcomes == null){
    //         System.out.println("No Appointment Outcome Records found.");
    //         return;
    //     }

    //     // iterate through each key (AppointmentOutcomeID) of Appointment Outcome hashmap
    //     for (String key : DataBase.AppointmentOutcomes.keySet()){
            
    //         // reach end of hashmap
    //         if (key == null){
    //             System.out.println("Appointment Outcome not found.");
    //             return;
    //         }

    //         // current entry is not the correct appointment outcome
    //         if (key != appointmentOutcomeID){
    //             continue;
    //         }

    //         // appointment outcome found
    //         // assume that hashmap for appointment outcome has AppointmentID as key, and rest as List of values
            
    //         // to print the appointment id
    //         System.out.println("Appointment Outcome: ");
    //         System.out.println("Appointment ID: " + key);
    //         List<AppointmentOutcome> values = DataBase.AppointmentOutcomes.get(key);
            
    //         // to print the record itself
    //         for (AppointmentOutcome info : values){
    //             System.out.println("Date: " + info.getDate());
    //             System.out.println("Service: " + info.getService());
    //             // need add medicine in appointment outcome
    //             System.out.println("Medicine: " + info.getMedicine());      
    //             System.out.println("Consultation Notes: " + info.getConsultationNotes());
    //         }

    //     }
    // }

    // public static void updatePrescriptionStatus(String appointmentOutcomeID){

    //     if (DataBase.AppointmentOutcomes == null){
    //         System.out.println("No Appointment Outcome Records found.");
    //         return;
    //     }

    //     for (String key : DataBase.AppointmentOutcomes.keySet()){
    //         if (key == null){
    //             System.out.println("Appointment Outcome not found.");
    //             return;
    //         }


    //         if (key != appointmentOutcomeID)
    //             continue;

    //         // set the variable medicine_prescribed to true
    //         List<AppointmentOutcome> values = DataBase.AppointmentOutcomes.get(key);
    //         for (AppointmentOutcome info : values){
    //             info.setIsMedicinePrescribed(true);     // set medicine_prescribed to true
    //         }

    //         // may need add function to update inventory after updating status
    //     }

    // }




}
