package using;

public enum MedicationStatus {
    PENDING("Pending"),
    DISPENSED("Dispensed");

    private final String label;

    private MedicationStatus(String label) { this.label = label; }
    public String getLabel() { return label; }
}
