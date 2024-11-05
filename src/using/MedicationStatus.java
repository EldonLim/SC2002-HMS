package using;

public enum MedicationStatus {
    PENDING("Pending"),
    DISPENSED("Dispensed");

    private final String label;

    MedicationStatus(String label) {
        this.label = label;
    }

    public static MedicationStatus fromString(String medicationStatus) {
        for (MedicationStatus medicationStatus_ : values())
            if (medicationStatus_.getLabel().equals(medicationStatus))
                return medicationStatus_;
        return null;
    }

    public String getLabel() {
        return label;
    }
}
