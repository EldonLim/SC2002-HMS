package using;

public enum Availability {
    AVAILABLE("Available"),
    BOOKED("Booked"),
    NOT_AVAILABLE("Not Available");

    private final String label;

    Availability(String label) {
        this.label = label;
    }

    public static Availability fromString(String availability) {
        for (Availability availability_ : values())
            if (availability_.label.equals(availability))
                return availability_;
        return null;
    }

    public String getLabel() {
        return label;
    }
}
