package using;

public enum AppointmentStatus {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    CANCEL("Cancelled"),
    COMPLETED("Completed");

    private final String label;

    private AppointmentStatus(String label) { this.label = label; }
    public String getLabel() { return label; }

}
