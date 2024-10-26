package model;

public class Medicine {
    private String medicineName;
    private int stock;
    private int lowStockThreshold;
    private boolean lowStockAlert = false;
    private boolean requestAddStock = false;

    /**
     * Constructs a Medicine object.
     *
     * @param medicineName     the name of the medicine
     * @param stock            the initial stock level
     * @param lowStockThreshold the threshold below which stock is considered low
     */
    public Medicine(String medicineName, int stock, int lowStockThreshold) {
        this.medicineName = medicineName;
        this.stock = stock;
        this.lowStockThreshold = lowStockThreshold;
        checkStockLevel(); // Initialize lowStockAlert and requestAddStock
    }

    /**
     * Gets the medicine name.
     *
     * @return the medicine name
     */
    public String getMedicineName() {
        return medicineName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
        checkStockLevel(); // Update lowStockAlert and requestAddStock
    }

    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(int lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
        checkStockLevel(); // Update lowStockAlert and requestAddStock
    }

    private void checkStockLevel() {
        lowStockAlert = stock <= lowStockThreshold;
    }

    public boolean getLowStockAlert() {
        return lowStockAlert;
    }

    public boolean getRequestAddStock() {
        return requestAddStock;
    }
}