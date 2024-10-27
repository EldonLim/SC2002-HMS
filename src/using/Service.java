package using;

public enum Service {
    CONSULTATION("Consultation"),
    XRAY("X-Ray"),
    BLOOD_TEST("Blood Test"),
    OTHER("Other");

    private final String label;

    private Service(String label) { this.label = label; }
    public String getLabel() { return label; }

    public static Service fromString(String service) {
        for (Service service_: values())
            if (service_.label.equals(service))
                return service_;
        return null;
    }
}
