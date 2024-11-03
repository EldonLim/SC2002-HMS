package controller;

import database.DataBase;
import helper.Helper;
import model.Medicine;

import java.util.List;

public class InventoryManager {

    private final static int NUMBER_OF_MEDICINE_DISPENDED = 20;
    private final static int NUMBER_OF_STOCK_ADDED = 50;

    public InventoryManager() {}

    public static void listInventory() {
        System.out.printf("%-20s %-5s\n", "Medicines", "Stock");

        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.getStock() != 0)
                System.out.printf("%-20s %-5d\n", medicine.getMedicineName(), medicine.getStock());
    }

    public static boolean checkInventoryLowStock() {
        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.checkStockLevel())
                return true;

        return false;
    }

    public static boolean checkMedicineStockLevel(String medicineName) { return DataBase.getMedicines().get(medicineName).getLowStockAlert(); }

    public static void dispendMedicine(String medicineName) {
        Medicine medicine = DataBase.getMedicines().get(medicineName);
        medicine.setStock(medicine.getStock() - NUMBER_OF_MEDICINE_DISPENDED);
    }

    public static void updateStock(String medicineName, boolean isAddition) {
        System.out.print("Enter Quantity: ");
        int quantity = Helper.readInt();
        Medicine medicine = DataBase.getMedicines().get(medicineName);

        if (isAddition) {
            medicine.setStock(medicine.getStock() + quantity);
            System.out.println(quantity + " Stock Added for " + medicineName);
        }
        else
            if (medicine.getStock() < quantity) System.out.println("Insufficient stock. Cannot remove.");
            else {
                medicine.setStock(medicine.getStock() - quantity);
                System.out.println(quantity + " Stock Removed for " + medicineName);
            }
    }

    public static List<Medicine> getAllMedicineWithLowStockAlert() {
        return DataBase.getMedicines().values().stream()
                .filter(medicine -> medicine.getRequestAddStock() && medicine.getLowStockAlert())
                .toList();
    }

    public static void handleApproveReplenishmentRequest(Medicine medicine) {
        medicine.setStock(medicine.getStock() + NUMBER_OF_STOCK_ADDED);
        System.out.println("Approved Replenishment Request for " + medicine.getMedicineName());
    }

    public static void addNewMedicine(String medicineName, int quantity, int lowStockAlertQuantity) {
        DataBase.getMedicines().put(medicineName, new Medicine(medicineName, quantity, lowStockAlertQuantity));
        System.out.println("Added New Medicine");
    }
}
