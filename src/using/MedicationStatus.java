package using;

public enum MedicationStatus {
    PENDING("Pending"),
    DISPENDED("Dispended");

    private final String label;

    private MedicationStatus(String label) { this.label = label; }
    public String getLabel() { return label; }
}
