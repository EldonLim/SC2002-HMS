package using;

public enum AppointmentStatus {
    PENDING("Pending"),
    DONE("Done");

    private final String label;

    private AppointmentStatus(String label) { this.label = label; }
    public String getLabel() { return label; }

}
