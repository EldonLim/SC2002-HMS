package using;

/**
 * Enum representing the different blood types.
 * Each blood type is associated with a specific label.
 * 
 * @author Lean Yi Fan
 * @version 2.3
 * @since 2024-10-26
 */
public enum BloodType {
    /**
     * Blood type A negative.
     */
    A_NEGATIVE("A-"),
    
    /**
     * Blood type A positive.
     */
    A_POSITIVE("A+"),
    
    /**
     * Blood type B negative.
     */
    B_NEGATIVE("B-"),
    
    /**
     * Blood type B positive.
     */
    B_POSITIVE("B+"),
    
    /**
     * Blood type O negative.
     */
    O_NEGATIVE("O-"),
    
    /**
     * Blood type O positive.
     */
    O_POSITIVE("O+"),
    
    /**
     * Blood type AB negative.
     */
    AB_NEGATIVE("AB-"),
    
    /**
     * Blood type AB positive.
     */
    AB_POSITIVE("AB+");

    /**
     * A descriptive label representing the blood type.
     */
    private final String label;

    /**
     * Constructor for BloodType enum.
     *
     * @param label The label of the blood type.
     */
    BloodType(String label) {
        this.label = label;
    }

    /**
     * Converts a string label to the corresponding BloodType enum.
     *
     * @param bloodType The label of the blood type.
     * @return The corresponding BloodType, or null if no match is found.
     */
    public static BloodType fromString(String bloodType) {
        for (BloodType bloodType_ : values()) {
            if (bloodType_.label.equals(bloodType)) {
                return bloodType_;
            }
        }
        return null;
    }

    /**
     * Retrieves the label of the blood type.
     *
     * @return The label associated with the blood type.
     */
    public String getLabel() {
        return label;
    }
}
