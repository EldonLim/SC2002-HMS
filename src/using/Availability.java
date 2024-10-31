package using;

public enum Availability {
    AVAILABLE("Available"),
    NOT_AVAILABLE("Not Available");
//    PENDING_APPOINTMENT("Pending Appointment"),
//    CONFIRMED_APPOINTMENT("Confirmed Appointment");

    private final String label;

    private Availability(String label) { this.label = label; }
    public String getLabel() { return label; }

    public static Availability fromString(String availability) {
        for (Availability availability_ : values())
            if (availability_.label.equals(availability))
                return availability_;
        return null;
    }
}
