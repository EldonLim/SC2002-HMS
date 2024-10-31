package controller;

import database.DataBase;
import model.Medicine;

public class PharmacistManager {

    public PharmacistManager() {}

    public static void viewInventory() {
        if (DataBase.getMedicines().isEmpty()) {
            System.out.println("No medicines record in the inventory");
            return;
        }

        InventoryManager.listInventory();
    }

    public static void submitRequest() {
        System.out.println("Replenishment Request Sent For:");

        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.getLowStockAlert() && !medicine.getRequestAddStock()) {
                System.out.println(medicine.getMedicineName());
                medicine.setRequestAddStock();
            }

        System.out.println();
    }
}
