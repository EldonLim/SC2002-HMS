package src2.model;

/**
 * Represents a medicine with stock management properties such as stock level, 
 * low stock threshold, and alerts for low stock.
 * 
 * @author Goh Jun Keat
 * @version 3.4
 * @since 2024-10-26
 */
public class Medicine {
    
    /**
     * The name of the medicine.
     */
    private final String medicineName;
    
    /**
     * The current stock level of the medicine.
     */
    private int stock;
    
    /**
     * The threshold level below which the stock is considered low.
     */
    private int lowStockThreshold;
    
    /**
     * A flag indicating if the stock is below the low stock threshold.
     */
    private boolean lowStockAlert;
    
    /**
     * A flag indicating if a request to add stock has been made.
     */
    private boolean requestAddStock;

    /**
     * Constructs a Medicine object with specified name, initial stock level, and low stock threshold.
     * Initializes the low stock alert and add stock request based on the current stock level.
     *
     * @param medicineName      the name of the medicine
     * @param stock             the initial stock level
     * @param lowStockThreshold the threshold below which stock is considered low
     */
    public Medicine(String medicineName, int stock, int lowStockThreshold) {
        this.medicineName = medicineName;
        this.stock = stock;
        this.lowStockThreshold = lowStockThreshold;
        this.requestAddStock = false;
        checkStockLevel(); // Initialize lowStockAlert and requestAddStock
    }

    /**
     * Constructs a Medicine object with specified properties including the low stock alert 
     * and add stock request flags.
     *
     * @param medicineName      the name of the medicine
     * @param stock             the initial stock level
     * @param lowStockThreshold the threshold below which stock is considered low
     * @param lowStockAlert     initial value for the low stock alert
     * @param requestAddStock   initial value for the add stock request flag
     */
    public Medicine(String medicineName, int stock, int lowStockThreshold, boolean lowStockAlert, boolean requestAddStock) {
        this.medicineName = medicineName;
        this.stock = stock;
        this.lowStockThreshold = lowStockThreshold;
        this.lowStockAlert = lowStockAlert;
        this.requestAddStock = requestAddStock;
    }

    /**
     * Gets the name of the medicine.
     *
     * @return the name of the medicine
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * Gets the current stock level of the medicine.
     *
     * @return the current stock level
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock level of the medicine and checks if it triggers a low stock alert.
     *
     * @param stock the new stock level
     */
    public void setStock(int stock) {
        this.stock = stock;
        checkStockLevel(); // Update lowStockAlert and requestAddStock
    }

    /**
     * Gets the threshold below which the stock is considered low.
     *
     * @return the low stock threshold
     */
    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    /**
     * Sets the low stock threshold and checks if it triggers a low stock alert.
     *
     * @param lowStockThreshold the new low stock threshold
     */
    public void setLowStockThreshold(int lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
        checkStockLevel(); // Update lowStockAlert and requestAddStock
    }

    /**
     * Checks if the stock level is at or below the low stock threshold.
     * Updates the low stock alert flag accordingly.
     *
     * @return true if the stock is below or equal to the low stock threshold; false otherwise
     */
    public boolean checkStockLevel() {
        lowStockAlert = stock <= lowStockThreshold;
        return this.getLowStockAlert();
    }

    /**
     * Gets the current status of the low stock alert flag.
     *
     * @return true if the stock level is low; false otherwise
     */
    public boolean getLowStockAlert() {
        return lowStockAlert;
    }

    /**
     * Gets the status of the request to add stock.
     *
     * @return true if an add stock request has been made; false otherwise
     */
    public boolean getRequestAddStock() {
        return requestAddStock;
    }

    /**
     * Sets the add stock request flag to true, indicating a request has been made to replenish stock.
     */
    public void setRequestAddStock() {
        this.requestAddStock = true;
    }
}
