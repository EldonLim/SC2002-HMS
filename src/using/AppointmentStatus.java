package using;

public enum AppointmentStatus {
    PENDING("Pending"),
    CONFIRM("Confirmed"),
    CANCEL("Canceled"),
    COMPLETED("Completed");

    private final String label;

    private AppointmentStatus(String label) { this.label = label; }
    public String getLabel() { return label; }

    public static AppointmentStatus fromString(String appointmentStatus) {
        for (AppointmentStatus appointmentStatus_ : values())
            if (appointmentStatus_.getLabel().equals(appointmentStatus))
                return appointmentStatus_;
        return null;
    }
}
