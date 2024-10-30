package controller;

import model.AppointmentOutcome;
import model.Medicine;
import database.DataBase;
import helper.Helper;

import java.util.ArrayList;

public class PharmacistManager {
    
    public PharmacistManager(){};

    public static void viewAppointmentOutcomeRecords(String appointmentOutcomeID){

        //if AppointmentOutcome hashmap is empty (no appointments completed yet)
        if (DataBase.AppointmentOutcomes == null){
            System.out.println("No Appointment Outcome Records found.");
            return;
        }

        // iterate through each key (AppointmentOutcomeID) of Appointment Outcome hashmap
        for (String key : DataBase.AppointmentOutcomes.keySet()){
            
            // reach end of hashmap
            if (key == null){
                System.out.println("Appointment Outcome not found.");
                return;
            }

            // current entry is not the correct appointment outcome
            if (key != appointmentOutcomeID){
                continue;
            }

            // appointment outcome found
            // assume that hashmap for appointment outcome has AppointmentID as key, and rest as List of values
            
            // to print the appointment id
            System.out.println("Appointment Outcome: ");
            System.out.println("Appointment ID: " + key);
            List<AppointmentOutcome> values = DataBase.AppointmentOutcomes.get(key);
            
            // to print the record itself
            for (AppointmentOutcome info : values){
                System.out.println("Date: " + info.getDate());
                System.out.println("Service: " + info.getService());
                // need add medicine in appointment outcome
                System.out.println("Medicine: " + info.getMedicine());      
                System.out.println("Consultation Notes: " + info.getConsultationNotes());
            }

        }

    }

    public static void updatePrescriptionStatus(String appointmentOutcomeID){

        if (DataBase.AppointmentOutcomes == null){
            System.out.println("No Appointment Outcome Records found.");
            return;
        }

        for (String key : DataBase.AppointmentOutcomes.keySet()){
            if (key == null){
                System.out.println("Appointment Outcome not found.");
                return;
            }


            if (key != appointmentOutcomeID)
                continue;

            // set the variable medicine_prescribed to true
            List<AppointmentOutcome> values = DataBase.AppointmentOutcomes.get(key);
            for (AppointmentOutcome info : values){
                info.setIsMedicinePrescribed(true);     // set medicine_prescribed to true
            }

            // may need add function to update inventory after updating status

        }

    // view all medicines in inventory
    public static void viewInventory(){
        if (DataBase.Medicines.isEmpty()){
            System.out.println("No medicines recorded in inventory.");
            return;
        }

        System.out.printf("%-20s %-5s\n", "Medicines", "Stock");
        // print in a table

        for (Medicine medicine : DataBase.Medicines.values()){
            System.out.printf("%-20s %-5d\n", medicine.getMedicineName(), medicine.getStock());
        }

        System.out.println();

    }

    // submit replenishment request
    public static void submitReplenishmentReq(){
        char choice;

        // return if no low stock meds
        if (!displayLowStockMeds())
            return;

        // return if all low stock meds have already been requested earlier
        if (isAlreadyReq()){
            System.out.println("Replenishment requests already submitted.\n");
            return;
        }

        do {
            System.out.println("Submit replenishment request? (y/n)");
            choice = Helper.readChar();

            if (choice != 'y' && choice != 'n' && choice != 'Y' && choice != 'N')
                System.out.println("Invalid choice!");

        } while (choice != 'y' && choice != 'n' && choice != 'Y' && choice != 'N');

        if (choice == 'y' || choice == 'Y')
            submitReq();        // submit requests for all low stock meds

        else
            System.out.println("No requests made.\n");      // if decide not to request

    }

    // print all low stock meds if any
    public static boolean displayLowStockMeds(){
        boolean existLowStock = false;

        System.out.println("Medicines low on stock: ");

        // print all meds with low stock and whether a replenishment request has been submitted for this med
        for (Medicine medicine : DataBase.Medicines.values()){
            if (medicine.getLowStockAlert()){
                existLowStock = true;
                System.out.printf("%s\n", medicine.getMedicineName());
                System.out.printf("Current Stock: %d\tMinimum Required Stock: %d\tRequested Replenishment: %b\n\n", medicine.getStock(), medicine.getLowStockThreshold(), medicine.getRequestAddStock());
            }

        }

        if (!existLowStock)
            System.out.println("None\n");       // if no low stock meds

        return existLowStock;

    }

    // check if all meds already requested for replenishment
    public static boolean isAlreadyReq(){
        for (Medicine medicine : DataBase.Medicines.values()){
            if (medicine.getLowStockAlert() && !medicine.getRequestAddStock())
                return false;       // if there exists meds that are low stock and request not sent for it yet
        }

        return true;
    }

    // submit request for replenishement
    public static void submitReq(){
        ArrayList<String> cur_requests = new ArrayList<>();        

        for (Medicine medicine : DataBase.Medicines.values()){
            if (medicine.getLowStockAlert() && !medicine.getRequestAddStock()){
                cur_requests.add(medicine.getMedicineName());      // add all current requests to ArrayList cur_requests
                medicine.setRequestAddStock(true);      // indicate that replenishment request for this medicine already made
            }
        }

        System.out.println("Replenishment request sent for: ");
        
        for (String meds : cur_requests){
            System.out.println(meds);       // print all meds requested on this request
        }

        System.out.println();
    }


    // long version of code below

    // public static void submitReplenishmentReq(){
    //     boolean existLowStock = false, alreadyReq = true;
    //     char choice;

    //     System.out.println("Medicines low on stock: ");

    //     for (Medicine medicine : DataBase.Medicines.values()){
    //         if (medicine.getLowStockAlert()){
    //             existLowStock = true;
    //             System.out.printf("%s\n", medicine.getMedicineName());
    //             System.out.printf("Current Stock: %d\tMinimum Required Stock: %d\tRequested Replenishment: %b\n\n", medicine.getStock(), medicine.getLowStockThreshold(), medicine.getRequestAddStock());
            
    //             if (!medicine.getRequestAddStock())
    //                 alreadyReq = false;

    //         }

    //     }

    //     if (!existLowStock){
    //         System.out.println("None");
    //         System.out.println();
    //         return;
    //     }

    //     if (alreadyReq){
    //         System.out.println("Replenishment requests already submitted.");
    //         System.out.println();
    //         return;
    //     }

    //     do {
    //         System.out.println("Submit replenishment request? (y/n)");
    //         choice = Helper.readChar();

    //         if (choice != 'y' && choice != 'n' && choice != 'Y' && choice != 'N')
    //             System.out.println("Invalid choice!");

    //     } while (choice != 'y' && choice != 'n' && choice != 'Y' && choice != 'N');

    //     if (choice == 'y' || choice == 'Y'){
    //         ArrayList<String> cur_requests = new ArrayList<>();

    //         for (Medicine medicine : DataBase.Medicines.values()){
    //             if (medicine.getLowStockAlert() && !medicine.getRequestAddStock()){
    //                 cur_requests.add(medicine.getMedicineName());
    //                 medicine.setRequestAddStock(true);
    //             }
    //         }

    //         System.out.println("Replenishment request sent for: ");
            
    //         for (String meds : cur_requests){
    //             System.out.println(meds);
    //         }

    //         System.out.println();

    //     }

    //     else {
    //         System.out.println("No requests made.");
    //         System.out.println();
    //         return;
    //     }

    // }

}


