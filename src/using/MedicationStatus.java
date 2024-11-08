package using;

/**
 * Enum representing the status of medication.
 * Each status has a corresponding label.
 * 
 * @author Goh Jun Keat
 * @version 2.3
 * @since 2024-10-26
 */
public enum MedicationStatus {
    /**
     * Status indicating that the medication is pending.
     */
    PENDING("Pending"),
    
    /**
     * Status indicating that the medication has been dispensed.
     */
    DISPENSED("Dispensed");

    /**
     * A descriptive label for the medication status.
     */
    private final String label;

    /**
     * Constructor for MedicationStatus enum.
     *
     * @param label The descriptive label of the medication status.
     */
    MedicationStatus(String label) {
        this.label = label;
    }

    /**
     * Converts a string label to the corresponding MedicationStatus enum.
     *
     * @param medicationStatus The label of the medication status.
     * @return The corresponding MedicationStatus, or null if no match is found.
     */
    public static MedicationStatus fromString(String medicationStatus) {
        for (MedicationStatus medicationStatus_ : values()) {
            if (medicationStatus_.getLabel().equals(medicationStatus)) {
                return medicationStatus_;
            }
        }
        return null;
    }

    /**
     * Retrieves the label of the medication status.
     *
     * @return The label associated with the medication status.
     */
    public String getLabel() {
        return label;
    }
}
