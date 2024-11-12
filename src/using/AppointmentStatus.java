package using;

/**
 * Enum representing the status of an appointment.
 * Each status has a corresponding label that provides a descriptive name.
 * 
 * @author Eldon Lim Kai Jie
 * @version 2.3
 * @since 2024-10-26
 */
public enum AppointmentStatus {
    /**
     * Status indicating that the appointment is pending.
     */
    PENDING("Pending"),
    
    /**
     * Status indicating that the appointment is confirmed.
     */
    CONFIRM("Confirmed"),
    
    /**
     * Status indicating that the appointment is canceled.
     */
    CANCEL("Canceled"),
    
    /**
     * Status indicating that the appointment is completed.
     */
    COMPLETED("Completed");

    /**
     * A descriptive label for the appointment status.
     */
    private final String label;

    /**
     * Constructor for AppointmentStatus enum.
     *
     * @param label The descriptive label of the status.
     */
    AppointmentStatus(String label) {
        this.label = label;
    }

    /**
     * Converts a string label to the corresponding AppointmentStatus enum.
     *
     * @param appointmentStatus The label of the appointment status.
     * @return The corresponding AppointmentStatus, or null if no match is found.
     */
    public static AppointmentStatus fromString(String appointmentStatus) {
        for (AppointmentStatus appointmentStatus_ : values()) {
            if (appointmentStatus_.getLabel().equals(appointmentStatus)) {
                return appointmentStatus_;
            }
        }
        return null;
    }

    /**
     * Retrieves the label of the appointment status.
     *
     * @return The label associated with the appointment status.
     */
    public String getLabel() {
        return label;
    }
}
