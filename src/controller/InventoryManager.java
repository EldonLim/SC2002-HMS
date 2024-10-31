package controller;

import java.util.ArrayList;
import database.DataBase;
import model.Medicine;

public class InventoryManager {

    InventoryManager() {
    }

    public static void orderMedicine(String medicineName, int quantity){
        Medicine med = DataBase.MedicineList.get(medicineName);
        setAlert(medicineName);
        med.setOrderQuantity(quantity);
    }

    public static int getMedicineStock(String medicineName){
        int medStock = DataBase.MedicineList.get(medicineName).getStock();
        return medStock;
    }

    public static void updateStock(String medicineName, int quantity){
        Medicine med = DataBase.MedicineList.get(medicineName);
        int currentStock = med.getStock();
        med.setStock(quantity + currentStock);
    }

    public static void setAlert(String medicineName){
        DataBase.MedicineList.get(medicineName).setRequestAddStock();
    }

    public static int getLowLevelThreshold(String medicineName){
        return DataBase.MedicineList.get(medicineName).getLowStockThreshold();
    }

    public static ArrayList<Medicine> getMedicationList() {
        ArrayList<Medicine> medicationList = new ArrayList<>();

        for (Medicine med: DataBase.MedicineList.values()) {
            medicationList.add(med);
        }

        return medicationList;
    }

    public static Medicine[] getRequestedAddStockList() {
        Medicine[] requestReplenishmentList = new Medicine[100];
        int index = 0;

        for (Medicine med: DataBase.MedicineList.values()) {
            if (med.getRequestAddStock() == "Requested") {
                requestReplenishmentList[index] = med;
                index++;
            }
        }

        return requestReplenishmentList;
    }

    public static void approvedReplenishment(String medicineName) {
        DataBase.MedicineList.get(medicineName).setApproveAddStock();
    }

    // TEST CASE 18 View Medication Inventory
    // PHARMACIST will call getMedicationList() to see a list of medications and it's stock levels.

    // TEST CASE 19 Submit Replenishment Request
    // PHARMACIST will call getMedicineStock() and getLowLevelThreshold() to check if stock is less than low level. ( this will be displayed on view and pharmacist can decide whether to submit replenishment request)
    // if he/she decided to submit request, it will call orderMedicine().

    // TEST CASE 22 View and Manage Medication Inventory
    // ADMINISTRATOR can update the stock level by calling updateStock().
    // he/she also can verify if the stock level is updated by calling getMedicineStock()

    // TEST CASE 23 Approve Replenishment Requests
    // ADMINISTRATOR will see a list of medicine that needed to be replenish by calling getRequestedAddStockList()
    // Then he/she can approves it and the request status will change to approved and it will call updateStock() and approvedReplenishment()
}
