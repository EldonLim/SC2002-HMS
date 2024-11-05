package using;

public enum Service {
    CONSULTATION("Consultation"),
    XRAY("X-Ray"),
    BLOOD_TEST("Blood Test"),
    OTHER("Other");

    private final String label;

    Service(String label) {
        this.label = label;
    }

    public static Service fromString(String service) {
        for (Service service_ : values())
            if (service_.label.equals(service))
                return service_;
        return null;
    }

    public String getLabel() {
        return label;
    }
}
