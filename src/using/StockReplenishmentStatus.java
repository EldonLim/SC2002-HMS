package using;

public enum StockReplenishmentStatus {
    APPROVED("Approved"),
    REJECTED("Rejected"),
    REQUESTED("Requested"),
    NOACTION("No Action Required");

    private final String label;
    private StockReplenishmentStatus(String label) {this.label = label;}
    public String getLabel() { return label; }
}

