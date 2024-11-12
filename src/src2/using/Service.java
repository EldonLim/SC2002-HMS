package src2.using;

/**
 * Enum representing different types of services offered.
 * Each service has a corresponding label.
 * 
 * @author Chew En Zee
 * @version 2.2
 * @since 2024-10-27
 */
public enum Service {
    /**
     * Service for a consultation.
     */
    CONSULTATION("Consultation"),
    
    /**
     * Service for an X-ray.
     */
    XRAY("X-Ray"),
    
    /**
     * Service for a blood test.
     */
    BLOOD_TEST("Blood Test"),
    
    /**
     * Service for other types of services.
     */
    OTHER("Other");

    /**
     * A descriptive label for the service.
     */
    private final String label;

    /**
     * Constructor for Service enum.
     *
     * @param label The descriptive label of the service.
     */
    Service(String label) {
        this.label = label;
    }

    /**
     * Converts a string label to the corresponding Service enum.
     *
     * @param service The label of the service.
     * @return The corresponding Service, or null if no match is found.
     */
    public static Service fromString(String service) {
        for (Service service_ : values()) {
            if (service_.label.equals(service)) {
                return service_;
            }
        }
        return null;
    }

    /**
     * Retrieves the label of the service.
     *
     * @return The label associated with the service.
     */
    public String getLabel() {
        return label;
    }
}
