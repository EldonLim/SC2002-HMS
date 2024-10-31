package controller;

import database.DataBase;
import model.Medicine;

public class InventoryManager {

    public InventoryManager() {}

    public static void listInventory() {
        System.out.printf("%-20s %-5s\n", "Medicines", "Stock");

        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.getStock() != 0)
                System.out.printf("%-20s %-5d\n", medicine.getMedicineName(), medicine.getStock());

        System.out.println();
    }

    public static boolean checkInventoryLowStock() {
        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.checkStockLevel())
                return true;

        return false;
    }

    public static boolean checkMedicineStockLevel(String medicineName) { return DataBase.getMedicines().get(medicineName).getLowStockAlert(); }
}
