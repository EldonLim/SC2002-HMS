package model;

public class Medicine {
    private String medicineName;
    private int stock;
    private int lowStockThreshold;
    private boolean lowStockAlert;
    private boolean requestAddStock;

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
        this.requestAddStock = false;
        checkStockLevel(); // Initialize lowStockAlert and requestAddStock
    }

    public Medicine(String medicineName, int stock, int lowStockThreshold, boolean lowStockAlert, boolean requestAddStock) {
        this.medicineName = medicineName;
        this.stock = stock;
        this.lowStockThreshold = lowStockThreshold;
        this.lowStockAlert = lowStockAlert;
        this.requestAddStock = requestAddStock;
    }

    /**
     * Gets the medicine name.
     *
     * @return the medicine name
     */
    public String getMedicineName() { return medicineName; }
    public int getStock() { return stock; }

    public void setStock(int stock) {
        this.stock = stock;
        checkStockLevel(); // Update lowStockAlert and requestAddStock
    }

    public int getLowStockThreshold() { return lowStockThreshold; }

    public void setLowStockThreshold(int lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
        checkStockLevel(); // Update lowStockAlert and requestAddStock
    }

    public boolean checkStockLevel() {
        lowStockAlert = stock <= lowStockThreshold;
        return this.getLowStockAlert();
    }

    public boolean getLowStockAlert() { return lowStockAlert; }
    public boolean getRequestAddStock() { return requestAddStock; }

    public void setRequestAddStock() { this.requestAddStock = true; }
}