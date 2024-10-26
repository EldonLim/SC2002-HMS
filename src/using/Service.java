package using;

public enum Service {
    CONSULTATION("Consultation"),
    XRAY("X-Ray"),
    BLOOD_TEST("Blood Test"),
    OTHER("Other");

    private final String label;

    private Service(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
