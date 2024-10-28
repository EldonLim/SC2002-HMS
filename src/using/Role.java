package using;

public enum Role {
    ADMINISTRATOR("Administrator"),
    PATIENT("Patient"),
    DOCTOR("Doctor"),
    PHARMACIST("Pharmacist");

    private final String label;

    private Role(String label) { this.label = label; }
    public String getLabel() { return label; }

    public static Role fromString(String role) {
        for (Role role_ : values())
            if (role_.label.equals(role))
                return role_;
        return null;
    }
}
