package controller;

import database.DataBase;
import model.Medicine;

public class InventoryManager {

    private final static int NUMBER_OF_MEDICINE_DISPENDED = 50;

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

    public static void dispendMedicine(String medicineName) {
        Medicine medicine = DataBase.getMedicines().get(medicineName);
        medicine.setStock(medicine.getStock() - NUMBER_OF_MEDICINE_DISPENDED);
    }
}
