package using;

/**
 * Enum representing the availability status.
 * Each status has a corresponding label to describe its state.
 * 
 * @author Chew En Zee
 * @version 2.3
 * @since 2024-10-30
 */
public enum Availability {
    /**
     * Status indicating that the item is available.
     */
    AVAILABLE("Available"),
    
    /**
     * Status indicating that the item is booked.
     */
    BOOKED("Booked"),
    
    /**
     * Status indicating that the item is not available.
     */
    NOT_AVAILABLE("Not Available");

    /**
     * A descriptive label for the availability status.
     */
    private final String label;

    /**
     * Constructor for Availability enum.
     *
     * @param label The descriptive label of the availability status.
     */
    Availability(String label) {
        this.label = label;
    }

    /**
     * Converts a string label to the corresponding Availability enum.
     *
     * @param availability The label of the availability status.
     * @return The corresponding Availability status, or null if no match is found.
     */
    public static Availability fromString(String availability) {
        for (Availability availability_ : values()) {
            if (availability_.label.equals(availability)) {
                return availability_;
            }
        }
        return null;
    }

    /**
     * Retrieves the label of the availability status.
     *
     * @return The label associated with the availability status.
     */
    public String getLabel() {
        return label;
    }
}
