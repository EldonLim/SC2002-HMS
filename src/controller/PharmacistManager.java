package controller;

import model.AppointmentOutcome;
import model.Medicine;
import database.DataBase;

public class PharmacistManager {
    
    public PharmacistManager(){};

    public void viewAppointmentOutcomeRecords(String appointmentOutcomeID){

        //if AppointmentOutcome hashmap is empty (no appointments completed yet)
        if (DataBase.AppointmentOutcome == null){
            System.out.println("No Appointment Outcome Records found.");
            return;
        }

        // iterate through each key (AppointmentOutcomeID) of Appointment Outcome hashmap
        for (String key : Database.AppointmentOutcome.keySet()){
            
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
            List<AppointmentOutcome> values = Database.AppointmentOutcome.get(key);
            
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

    public void updatePrescriptionStatus(String appointmentOutcomeID){

        if (DataBase.AppointmentOutcome == null){
            System.out.println("No Appointment Outcome Records found.");
            return;
        }

        for (String key : Database.AppointmentOutcome.keySet()){
            if (key == null){
                System.out.println("Appointment Outcome not found.");
                return;
            }


            if (key != appointmentOutcomeID)
                continue;

            // set the variable medicine_prescribed to true
            List<AppointmentOutcome> values = Database.AppointmentOutcome.get(key);
            for (AppointmentOutcome info : values){
                info.setIsMedicinePrescribed(true);     // set medicine_prescribed to true
            }

            // may need add function to update inventory after updating status

        }

    public void viewInventory(){

        // assume Medicine hashmap in Database has String key medicine_name and values int stock and boolean isLowStock
        for (String medicine_name : DataBase.Medicine.keySet()){
            List<Medicine> values = DataBase.Medicine.get(medicine_name);

            // only print medicine_name and stock
            for (Medicine info : values){
                System.out.println("Medicine: " + medicine_name + ", Stock: " + Medicine.getStock());
            }
        }

    }

    public void submitReplenishmentReq(){
        boolean existLowStock = false;

        for (String medicine_name : Database.Medicine.keySet()){
            System.out.println("Medicines that have low stock: ");

            List<Medicine> values = DataBase.Medicine.get(medicine_name);

            for (Medicine info : values){
                if (info.getLowStockAlert() == true){
                    existLowStock = true;
                    System.out.println("Medicine: " + medicine_name + ", Stock: " + Medicine.getStock());
                }
            }
        }

        // not complete
    }

}


