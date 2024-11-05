package using;

public enum AppointmentStatus {
    PENDING("Pending"),
    CONFIRM("Confirmed"),
    CANCEL("Canceled"),
    COMPLETED("Completed");

    private final String label;

    AppointmentStatus(String label) {
        this.label = label;
    }

    public static AppointmentStatus fromString(String appointmentStatus) {
        for (AppointmentStatus appointmentStatus_ : values())
            if (appointmentStatus_.getLabel().equals(appointmentStatus))
                return appointmentStatus_;
        return null;
    }

    public String getLabel() {
        return label;
    }
}
