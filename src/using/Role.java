package using;

/**
 * Enum representing different user roles in the application.
 * Each role has a corresponding label.
 *
 * @author Lean Yi Fan
 * @version 2.2
 * @since 2024-10-26
 */
public enum Role {
    /**
     * Role for an administrator.
     */
    ADMINISTRATOR("Administrator"),

    /**
     * Role for a patient.
     */
    PATIENT("Patient"),

    /**
     * Role for a doctor.
     */
    DOCTOR("Doctor"),

    /**
     * Role for a pharmacist.
     */
    PHARMACIST("Pharmacist");

    /**
     * A descriptive label for the user role.
     */
    private final String label;

    /**
     * Constructor for Role enum.
     *
     * @param label The descriptive label of the role.
     */
    Role(String label) {
        this.label = label;
    }

    /**
     * Converts a string label to the corresponding Role enum.
     *
     * @param role The label of the role.
     * @return The corresponding Role, or null if no match is found.
     */
    public static Role fromString(String role) {
        for (Role role_ : values()) {
            if (role_.label.equals(role)) {
                return role_;
            }
        }
        return null;
    }

    /**
     * Retrieves the label of the role.
     *
     * @return The label associated with the role.
     */
    public String getLabel() {
        return label;
    }
}
