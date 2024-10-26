package using;

public enum Role {
    ADMINISTRATOR("Administrator"),
    PATIENT("Patient"),
    DOCTOR("Doctor"),
    PHARMACIST("Pharmacist");

    private final String label;

    private Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
