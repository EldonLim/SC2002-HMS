package controller;

import database.DataBase;
import helper.Helper;
import model.Medicine;

import java.util.List;

/**
 * The InventoryManager class provides methods for managing the inventory of medicines,
 * including checking stock levels, dispensing, updating stock, and handling replenishment requests.
 * 
 * @author Eldon Lim Kai Jie
 * @version 2.5
 * @since 2024-10-31
 */
public class InventoryManager {

    /**
     * The number of medicine units dispensed per prescription.
     */
    private final static int NUMBER_OF_MEDICINE_DISPENDED = 20;

    /**
     * The number of stock units added when approving a replenishment request.
     */
    private final static int NUMBER_OF_STOCK_ADDED = 50;

    /**
     * Constructs an InventoryManager instance.
     */
    public InventoryManager() {
    }

    /**
     * Lists all medicines in the inventory with their current stock levels.
     */
    public static void listInventory() {
        System.out.printf("%-20s %-5s\n", "Medicines", "Stock");

        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.getStock() != 0)
                System.out.printf("%-20s %-5d\n", medicine.getMedicineName(), medicine.getStock());
    }

    /**
     * Checks if any medicines in the inventory have low stock levels.
     *
     * @return true if there is any medicine with a low stock level, false otherwise
     */
    public static boolean checkInventoryLowStock() {
        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.checkStockLevel())
                return true;

        return false;
    }

    /**
     * Checks the stock level of a specific medicine to see if it is low.
     *
     * @param medicineName the name of the medicine to check
     * @return true if the specified medicine has low stock, false otherwise
     */
    public static boolean checkMedicineStockLevel(String medicineName) {
        return DataBase.getMedicines().get(medicineName).getLowStockAlert();
    }

    /**
     * Dispenses a specified quantity of a medicine from the inventory.
     *
     * @param medicineName the name of the medicine to dispense
     */
    public static void dispendMedicine(String medicineName) {
        Medicine medicine = DataBase.getMedicines().get(medicineName);
        medicine.setStock(medicine.getStock() - NUMBER_OF_MEDICINE_DISPENDED);
    }

    /**
     * Updates the stock level of a specific medicine, either adding or removing a specified quantity.
     *
     * @param medicineName the name of the medicine to update
     * @param isAddition   true to add stock, false to remove stock
     */
    public static void updateStock(String medicineName, boolean isAddition) {
        System.out.print("Enter Quantity: ");
        int quantity = Helper.readInt();
        Medicine medicine = DataBase.getMedicines().get(medicineName);

        if (isAddition) {
            medicine.setStock(medicine.getStock() + quantity);
            System.out.println(quantity + " Stock Added for " + medicineName);
        } else if (medicine.getStock() < quantity) System.out.println("Insufficient stock. Cannot remove.");
        else {
            medicine.setStock(medicine.getStock() - quantity);
            System.out.println(quantity + " Stock Removed for " + medicineName);
        }
    }

    /**
     * Retrieves a list of all medicines with low stock alerts that require replenishment.
     *
     * @return a list of medicines that have low stock alerts and need replenishment
     */
    public static List<Medicine> getAllMedicineWithLowStockAlert() {
        return DataBase.getMedicines().values().stream()
                .filter(medicine -> medicine.getRequestAddStock() && medicine.getLowStockAlert())
                .toList();
    }

    /**
     * Approves a replenishment request for a specified medicine, increasing its stock.
     *
     * @param medicine the medicine for which the replenishment request is approved
     */
    public static void handleApproveReplenishmentRequest(Medicine medicine) {
        medicine.setStock(medicine.getStock() + NUMBER_OF_STOCK_ADDED);
        System.out.println("Approved Replenishment Request for " + medicine.getMedicineName());
    }

    /**
     * Adds a new medicine to the inventory with an initial quantity and low stock alert level.
     *
     * @param medicineName         the name of the new medicine
     * @param quantity             the initial stock quantity of the medicine
     * @param lowStockAlertQuantity the stock level at which a low stock alert should be triggered
     */
    public static void addNewMedicine(String medicineName, int quantity, int lowStockAlertQuantity) {
        DataBase.getMedicines().put(medicineName, new Medicine(medicineName, quantity, lowStockAlertQuantity));
        System.out.println("Added New Medicine");
    }
}
