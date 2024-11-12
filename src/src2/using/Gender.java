package src2.using;

/**
 * Enum representing gender options.
 * Each gender has a corresponding label.
 * 
 * @author Lean Yi Fan
 * @version 2.2
 * @since 2024-10-27
 */
public enum Gender {
    /**
     * Represents male gender.
     */
    MALE("Male"),
    
    /**
     * Represents female gender.
     */
    FEMALE("Female");

    /**
     * A descriptive label for the gender.
     */
    private final String label;

    /**
     * Constructor for Gender enum.
     *
     * @param label The descriptive label of the gender.
     */
    Gender(String label) {
        this.label = label;
    }

    /**
     * Converts a string label to the corresponding Gender enum.
     *
     * @param gender The label of the gender.
     * @return The corresponding Gender, or null if no match is found.
     */
    public static Gender fromString(String gender) {
        for (Gender gender_ : values()) {
            if (gender_.label.equals(gender)) {
                return gender_;
            }
        }
        return null;
    }

    /**
     * Retrieves the label of the gender.
     *
     * @return The label associated with the gender.
     */
    public String getLabel() {
        return label;
    }
}
