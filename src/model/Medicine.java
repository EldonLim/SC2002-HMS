package model;

import using.StockReplenishmentStatus;

public class Medicine {
    private String medicineName;
    private int stock;
    private int lowStockThreshold;
    private boolean lowStockAlert;
    private StockReplenishmentStatus stockReplenishmentStatus;
    private int orderQuantity;

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
        stockReplenishmentStatus = StockReplenishmentStatus.NOACTION;
        orderQuantity = 0;
        checkStockLevel(); // Initialize lowStockAlert and requestAddStock
    }

    /**
     * Gets the medicine name.
     *
     * @return the medicine name
     */
    public String getMedicineName() { return medicineName; }
    public int getStock() { return stock; }

    public void setOrderQuantity(int quantity) {
        this.orderQuantity = quantity;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setRequestAddStock(){
        this.stockReplenishmentStatus = StockReplenishmentStatus.REQUESTED;
    }

    public void setApproveAddStock(){
        this.stockReplenishmentStatus = StockReplenishmentStatus.APPROVED;
    }

    public void setStock(int stock) {
        this.stock = stock;
        checkStockLevel(); // Update lowStockAlert and requestAddStock
    }

    public int getLowStockThreshold() { return lowStockThreshold; }

    public void setLowStockThreshold(int lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
        checkStockLevel(); // Update lowStockAlert and requestAddStock
    }

    public void checkStockLevel() { lowStockAlert = stock <= lowStockThreshold; }
    public boolean getLowStockAlert() { return lowStockAlert; }
    public String getRequestAddStock() { return stockReplenishmentStatus.getLabel(); }
}